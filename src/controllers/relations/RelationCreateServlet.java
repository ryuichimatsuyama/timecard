package controllers.relations;

import java.io.IOException;

import javax.persistence.EntityManager;
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
        EntityManager em = DBUtil.createEntityManager();


		r.setEmployee((Employee) request.getSession().getAttribute("login_employee"));
		r.setBoss(em.find(Employee.class, Integer.parseInt(request.getParameter("boss"))));

			em.getTransaction().begin();
			em.persist(r);
			em.getTransaction().commit();

			em.close();
			request.getSession().setAttribute("flush", "登録が完了しました。");

			response.sendRedirect(request.getContextPath() + "/index.html");
		}
	}
