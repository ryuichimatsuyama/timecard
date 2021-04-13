package controllers.card;

import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Card;
import models.Employee;
import models.Relation;
import utils.DBUtil;

/**
 * Servlet implementation class CardCreateServlet
 */
@WebServlet("/cards/create")
public class CardCreateServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public CardCreateServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // String型の _tokenにパラメーターの_tokenを代入する
        String _token = (String) request.getParameter("_token");
        // _tokenがnullではなく、且つセッションIDと等しいならば
        if (_token != null && _token.equals(request.getSession().getId())) {
            // DAOインスタンスの生成
            EntityManager em = DBUtil.createEntityManager();
            // カードのインスタンスを生成
            Card r = new Card();
            // ログイン情報をセット
            r.setEmployee((Employee) request.getSession().getAttribute("login_employee"));
            // 現在日時をセット
            Date work_date = new Date(System.currentTimeMillis());
            r.setWork_date(work_date);
            // 出勤時間をセット
            r.setStart(request.getParameter("start"));
            // 出勤すると状態は未承認になる
            r.setStatus(1);
            // 提出先をセット
            Employee boss=em.find(Employee.class, Integer.parseInt(request.getParameter("boss")));
            r.setBoss(boss);
            // 入力項目チェック（バリデーション）
            List<String> errors = new ArrayList<String>();

            if (!r.getStart().matches("^([01][0-9]|2[0-3]):[0-5][0-9]$")) {
                errors.add("出勤時間を入力してください");
            }
            // errorsリストに1つでも追加されていたら
            if (errors.size() > 0) {
                List<Relation> relations = em.createNamedQuery("getMyBoss", Relation.class)
                        .setParameter("employee", (Employee) request.getSession().getAttribute("login_employee"))
                        .getResultList();
                // DAOの破棄
                em.close();
                // リクエストスコープに各データをセット
                request.setAttribute("relations", relations);
                request.setAttribute("_token", request.getSession().getId());
                request.setAttribute("card", r);
                request.setAttribute("errors", errors);
                // 画面遷移
                RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/cards/end.jsp");
                rd.forward(request, response);
            } else {
                // データベースに保存
                em.getTransaction().begin();
                em.persist(r);
                em.getTransaction().commit();
                // DAOの破棄
                em.close();
                // セッションスコープにフラッシュメッセージをセットする
                request.getSession().setAttribute("flush", "出勤しました。");
                // トップページにリダイレクト
                response.sendRedirect(request.getContextPath() + "/index.html");
            }

        }

    }
}