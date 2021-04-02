package controllers.employees;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;

import javax.persistence.EntityManager;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Employee;
import utils.DBUtil;
import utils.EncryptUtil;
import validators.EmployeeValidator;

/**
 * Servlet implementation class EmployeeCreateServlet
 */
@WebServlet("/employees/create")
public class EmployeeCreateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public EmployeeCreateServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String _token = (String) request.getParameter("_token");
		if (_token != null && _token.equals(request.getSession().getId())) {
			EntityManager em = DBUtil.createEntityManager();
			// 従業員のインスタンスを生成
			Employee e = new Employee();
			// eの各フィールドにデータを代入
			e.setCode(request.getParameter("code"));
			e.setName(request.getParameter("name"));
			e.setPassword(EncryptUtil.getPasswordEncrypt(request.getParameter("password"),
					(String) this.getServletContext().getAttribute("pepper")));
			e.setAdmin_flag(Integer.parseInt(request.getParameter("admin_flag")));

			Timestamp currentTime = new Timestamp(System.currentTimeMillis());
			e.setCreated_at(currentTime);
			e.setUpdated_at(currentTime);
			e.setDelete_flag(0);
			e.setBreak_time(request.getParameter("break_time"));
			e.setWage(request.getParameter("wage"));
			// バリデーション
			List<String> errors = EmployeeValidator.validate(e, true, true);
			if (errors.size() > 0) {
				em.close();

				request.setAttribute("_token", request.getSession().getId());
				request.setAttribute("employee", e);
				request.setAttribute("errors", errors);
				// 前のページに戻る
				RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/employees/new.jsp");
				rd.forward(request, response);
			} else {
				// データベースに保存
				em.getTransaction().begin();
				em.persist(e);
				em.getTransaction().commit();
				request.getSession().setAttribute("flush", "登録が完了しました。");
				em.close();
				// 従業員ページにリダイレクト
				response.sendRedirect(request.getContextPath() + "/employees/index");
			}
		}
	}

}
