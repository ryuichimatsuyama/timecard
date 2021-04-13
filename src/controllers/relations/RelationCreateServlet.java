package controllers.relations;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Employee;
import models.Relation;
import utils.DBUtil;

/**
 * Servlet implementation class RelationCreateServlet
 */
@WebServlet("/relations/create")
public class RelationCreateServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public RelationCreateServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // relationインスタンスを生成。
        Relation r = new Relation();
        // DAOインスタンスの生成
        EntityManager em = DBUtil.createEntityManager();
        // 自分の従業員情報
        Employee e = ((Employee) request.getSession().getAttribute("login_employee"));
        r.setEmployee(e);
        // 選択した上司をセット
        Employee boss = em.find(Employee.class, Integer.parseInt(request.getParameter("boss")));
        r.setBoss(boss);
        // バリデーター の呼び出し
        List<String> errors = new ArrayList<String>();
        // もし自分を除く従業員以外が登録されたならば
        if (!em.createNamedQuery("getBossCandidates", Employee.class).setParameter("id", e.getId()).getResultList()
                .contains(boss)) {
            errors.add("上司が間違ってます");
        }
        // errorsリストに1つでも追加されていたら
        if (errors.size() > 0) {
            // 自分を除く従業員以外
            List<Employee> employees = em.createNamedQuery("getBossCandidates", Employee.class)
                    .setParameter("id", e.getId()).getResultList();
            // DAOの破棄
            em.close();
            // リクエストスコープに各データをセット
            request.setAttribute("employees", employees);

            request.setAttribute("_token", request.getSession().getId());
            request.setAttribute("card", r);
            request.setAttribute("errors", errors);
            // 前の画面に戻る
            RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/relations/new.jsp");
            rd.forward(request, response);
        } else {
            // データベースに保存
            em.getTransaction().begin();
            em.persist(r);
            em.getTransaction().commit();
            // DAOの破棄
            em.close();
            // セッションスコープにフラッシュメッセージをセットする
            request.getSession().setAttribute("flush", "登録が完了しました。");
            // トップページにリダイレクト
            response.sendRedirect(request.getContextPath() + "/index.html");
        }
    }
}