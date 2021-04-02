package controllers.card;

import java.io.IOException;

import javax.persistence.EntityManager;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Card;
import utils.DBUtil;

/**
 * Servlet implementation class CardEditServlet
 */
@WebServlet("/card/edit")
public class CardEditServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public CardEditServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		EntityManager em = DBUtil.createEntityManager();
		// 該当のIDのカード1件のみをデータベースから取得
		Card c = em.find(Card.class, Integer.parseInt(request.getParameter("id")));

		em.close();
		// カード情報とセッションIDをリクエストスコープに登録
		request.setAttribute("_token", request.getSession().getId());
		request.setAttribute("card", c);
		// カードIDをセッションスコープに登録
		request.getSession().setAttribute("card_id", c.getId());
		// 承認ページを表示
		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/cards/edit.jsp");
		rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
