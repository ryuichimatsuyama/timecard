package controllers.relations;

import java.io.IOException;
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
 * Servlet implementation class RelationIndexServlet
 */
@WebServlet("/relations/index")
public class RelationIndexServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public RelationIndexServlet() {
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
		Employee login_employee = (Employee) request.getSession().getAttribute("login_employee");
		int page = 1;
		try {
			page = Integer.parseInt(request.getParameter("page"));
		} catch (NumberFormatException e) {

		}
		// 自分が登録した上司一覧
		List<Relation> relations = em.createNamedQuery("getMyBoss", Relation.class)
				.setParameter("employee", login_employee).getResultList();

		long relations_count = (long) em.createNamedQuery("getMyBossCount", Long.class)
				.setParameter("employee", login_employee).getSingleResult();

		em.close();

		request.setAttribute("relations", relations);
		request.setAttribute("relations_count", relations_count);
		request.setAttribute("page", page);
		if (request.getSession().getAttribute("flush") != null) {
			request.setAttribute("flush", request.getSession().getAttribute("flush"));
			request.getSession().removeAttribute("flush");
		}

		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/relations/index.jsp");
		rd.forward(request, response);
	}

}
