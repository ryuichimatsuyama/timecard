package controllers.approvals;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import javax.persistence.EntityManager;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Card;
import models.Employee;
import utils.DBUtil;

/**
 * Servlet implementation class ApprovalIndexServlet
 */
@WebServlet("/approvals/index")
public class ApprovalIndexServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public ApprovalIndexServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		EntityManager em = DBUtil.createEntityManager();

		Employee login_employee = (Employee) request.getSession().getAttribute("login_employee");


		int page;
		try {
			page = Integer.parseInt(request.getParameter("page"));
		} catch (Exception e) {
			page = 1;
		}
		// 自分宛の日報のみ表示。
		List<Card> cards = em.createNamedQuery("getBossCards", Card.class).setParameter("boss", login_employee)
				.setFirstResult(15 * (page - 1)).setMaxResults(15).getResultList();
		for (Card card : cards) {
			String start = card.getStart();
			String end = card.getEnd();
			String break_time = card.getEmployee().getBreak_time();
			// 休憩時間を分に直す
			String[] split = break_time.split(":");
			long minutes1 = TimeUnit.HOURS.toMinutes(Integer.parseInt(split[0])) + Integer.parseInt(split[1]);
			String[] split_start = start.split(":");
			LocalTime start_localtime = LocalTime.of(Integer.parseInt(split_start[0]),
					Integer.parseInt(split_start[1]));
			// 出勤したらnullを返す
			if (Objects.isNull(end)) {
				card.setWork_minutes("");
			} else {
				String[] split_end = end.split(":");
				LocalTime end_localtime = LocalTime.of(Integer.parseInt(split_end[0]), Integer.parseInt(split_end[1]));
				// 出退勤の差分を求めて分に直す
				long minutes = ChronoUnit.MINUTES.between(start_localtime, end_localtime);
				// 労働時間から休憩時間を引いて時間に直す
				Duration d = Duration.ofMinutes(minutes - minutes1);
				LocalTime time = LocalTime.MIN.plus(d);
				String output = time.toString();
				card.setWork_minutes(output);
				// 日給を計算するために分に変換
				String[] split1 = output.split(":");
				long minutes2 = TimeUnit.HOURS.toMinutes(Integer.parseInt(split1[0])) + Integer.parseInt(split1[1]);
				long wage = Long.parseLong(card.getEmployee().getWage());
				card.setWage(wage * minutes2 / 60);
			}
		}


		long cards_count = (long) em.createNamedQuery("getBossCount", Long.class).setParameter("boss", login_employee)
				.getSingleResult();

		em.close();

		request.setAttribute("cards", cards);
		request.setAttribute("cards_count", cards_count);
		request.setAttribute("page", page);

		if (request.getSession().getAttribute("flush") != null) {
			request.setAttribute("flush", request.getSession().getAttribute("flush"));
			request.getSession().removeAttribute("flush");
		}

		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/approvals/index.jsp");
		rd.forward(request, response);
	}

}
