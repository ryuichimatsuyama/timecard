package controllers.messages;

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
import models.Message;
import utils.DBUtil;

/**
 * Servlet implementation class MessageShowServlet
 */
@WebServlet("/messages/show")
public class MessageShowServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public MessageShowServlet() {
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
		// 該当のIDの従業員1件のみをデータベースから取得
		Employee login_employee = (Employee) request.getSession().getAttribute("login_employee");
		Employee e = em.find(Employee.class, Integer.parseInt(request.getParameter("id")));
		List<Message> messages = em.createNamedQuery("getOurMessages", Message.class).setParameter("send", e)
				.setParameter("get", login_employee).setParameter("send", login_employee).setParameter("get", e)
				.getResultList();
		em.close(); // メッセージデータをリクエストスコープにセットしてshow.jspを呼び出す
		request.setAttribute("messages", messages);
		request.setAttribute("name", e);
		request.getSession().setAttribute("employee_id",e.getId());

		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/messages/show.jsp");
		rd.forward(request, response);
	}

}
