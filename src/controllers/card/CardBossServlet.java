package controllers.card;

import java.io.IOException;
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
 * Servlet implementation class CardBossServlet
 */
@WebServlet("/card/boss")
public class CardBossServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public CardBossServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // DAOインスタンスの生成
        EntityManager em = DBUtil.createEntityManager();
        // ログイン従業員取得
        Employee login_employee = (Employee) request.getSession().getAttribute("login_employee");
        // 登録した上司一覧を取得
        List<Relation> relations = em.createNamedQuery("getMyBoss", Relation.class)
                .setParameter("employee", login_employee).getResultList();
        //カードインスタンス生成
        Card r = new Card();
        // DAOの破棄
        em.close();

        // カード情報とrelationsをセッションIDをリクエストスコープに登録
        request.setAttribute("relations", relations);
        request.setAttribute("card", r);
        request.setAttribute("_token", request.getSession().getId());

        // 提出先選択ページ表示
        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/cards/end.jsp");
        rd.forward(request, response);
    }

}
