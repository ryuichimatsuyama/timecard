package controllers.approvals;

import java.io.IOException;

import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Card;
import utils.DBUtil;

/**
 * Servlet implementation class ApprovalCreateServlet
 */
@WebServlet("/approvals/create")
public class ApprovalCreateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public ApprovalCreateServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		EntityManager em = DBUtil.createEntityManager();
		// セッションスコープからカードのidを取得して該当のidのカード１件のみをデータベースから取得
		Card c = em.find(Card.class, (Integer) (request.getSession().getAttribute("card_id")));
		c.setStart(request.getParameter("start_time"));
		c.setEnd(request.getParameter("end_time"));
		// 状態を承認に更新する
		c.setStatus(0);
		em.getTransaction().begin();
		em.getTransaction().commit();
		em.close();
		request.getSession().setAttribute("flush", "承認しました。");
		request.getSession().removeAttribute("card_id");

        response.sendRedirect(request.getContextPath() + "/approvals/index");
	}

}
