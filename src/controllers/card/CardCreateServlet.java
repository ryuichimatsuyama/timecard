package controllers.card;

import java.io.IOException;
import java.sql.Date;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Card;
import models.Employee;
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
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		EntityManager em = DBUtil.createEntityManager();

		Card r = new Card();

		r.setEmployee((Employee) request.getSession().getAttribute("login_employee"));
		Date work_date = new Date(System.currentTimeMillis());
		r.setWork_date(work_date);
		// 現在時刻を求める
		LocalDateTime now = LocalDateTime.now();
		DateTimeFormatter format = DateTimeFormatter.ofPattern("HH:mm");
		String start = now.format(format);
		r.setStart(start);
		em.getTransaction().begin();
		em.persist(r);
		em.getTransaction().commit();
		em.close();
		request.getSession().setAttribute("flush", "出勤しました。");
		response.sendRedirect(request.getContextPath() + "/index.html");

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

	}

}
