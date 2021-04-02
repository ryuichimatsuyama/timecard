package controllers.approvals;

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
import utils.DBUtil;
import validators.CardValidator;

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
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		EntityManager em = DBUtil.createEntityManager();
		// セッションスコープからカードのidを取得して該当のidのカード１件のみをデータベースから取得
		Card c = em.find(Card.class, (Integer) (request.getSession().getAttribute("card_id")));
		// 出勤時間を更新
		c.setStart(request.getParameter("start_time"));
		// 退勤時間を更新
		c.setEnd(request.getParameter("end_time"));
		// 状態を承認に更新する
		c.setStatus(0);
        List<String> errors = CardValidator.validate(c);
        if(errors.size() > 0) {
            em.close();

            request.setAttribute("_token", request.getSession().getId());
            request.setAttribute("card", c);
            request.setAttribute("errors", errors);

            RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/cards/edit.jsp");
            rd.forward(request, response);
        } else {


		// データベースを更新
		em.getTransaction().begin();
		em.getTransaction().commit();
		em.close();
		request.getSession().setAttribute("flush", "承認しました。");
		// セッションスコープ上の不要になったデータを削除
		request.getSession().removeAttribute("card_id");
		// 承認待ち一覧にリダイレクト
		response.sendRedirect(request.getContextPath() + "/approvals/index");
	}
	}
}
