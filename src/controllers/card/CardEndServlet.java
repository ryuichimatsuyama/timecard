package controllers.card;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Card;
import utils.DBUtil;

/**
 * Servlet implementation class CardEndServlet
 */
@WebServlet("/card/end")
public class CardEndServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public CardEndServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		EntityManager em = DBUtil.createEntityManager();
		// 該当のIDのカード1件のみをデータベースから取得
		Card c = em.find(Card.class, Integer.parseInt(request.getParameter("id")));

		// 現在時刻を求める
		LocalDateTime now = LocalDateTime.now();
		DateTimeFormatter format = DateTimeFormatter.ofPattern("HH:mm");
		c.setEnd(now.format(format));
		// データベースを更新
		em.getTransaction().begin();
		em.getTransaction().commit();
		em.close();
		request.getSession().setAttribute("flush", "退勤しました。");
		// トップページにリダイレクト
		response.sendRedirect(request.getContextPath() + "/index.html");
		}
	}


