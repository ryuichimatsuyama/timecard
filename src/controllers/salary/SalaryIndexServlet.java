package controllers.salary;
import java.io.IOException;
import java.sql.Date;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
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
import models.Salary;
import utils.DBUtil;
/**
 * Servlet implementation class SalaryIndexServlet
 */
@WebServlet("/salary/index")
public class SalaryIndexServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public SalaryIndexServlet() {
		super();
		// TODO Auto-generated constructor stub
	}
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stubs
		Employee login_employee = (Employee) request.getSession().getAttribute("login_employee");
		EntityManager em = DBUtil.createEntityManager();
		List<Card> mycards = em.createNamedQuery("getMyAllTimes", Card.class).setParameter("employee", login_employee)
				.getResultList();
		String year="";
		String tmp_year="";
		String month="";
		String tmp_month="";
		Integer days = 0;
//		long salary = 0;
		long total_time = 0;
		Salary salary = null;;
		List<Salary> salary_list = new ArrayList<Salary>();
		Date init_work_date = mycards.get(0).getWork_date();
		String[] init_values = init_work_date.toString().split("-");
//		System.out.println(init_work_date);
		for (Card card : mycards) {
//			System.out.println(salary_list);
//			System.out.println(card.getId());
			Date work_date = card.getWork_date();
			String[] values = work_date.toString().split("-");
			tmp_year = values[0];
			tmp_month = values[1];
			if(tmp_year.equals(init_values[0]) && tmp_month.equals(init_values[1])){
				year = tmp_year;
				month = tmp_month;
				String start = card.getStart();
				String end = card.getEnd();
				String break_time = login_employee.getBreak_time();
				// 休憩時間を分に直す
				String[] split = break_time.split(":");
				long minutes1 = TimeUnit.HOURS.toMinutes(Integer.parseInt(split[0])) + Integer.parseInt(split[1]);
				String[] split_start = start.split(":");
				LocalTime start_localtime = LocalTime.of(Integer.parseInt(split_start[0]),
						Integer.parseInt(split_start[1]));
				String[] split_end = end.split(":");
				LocalTime end_localtime = LocalTime.of(Integer.parseInt(split_end[0]),
						Integer.parseInt(split_end[1]));
				// 出退勤の差分を求めて分に直す
				long minutes = ChronoUnit.MINUTES.between(start_localtime, end_localtime);
				System.out.println(minutes);
				System.out.println(minutes1);
				System.out.println(minutes - minutes1);
				total_time  += minutes - minutes1;
				System.out.println("total_time: " + total_time);
				days++;
				salary = new Salary();
				salary.setDays(days);
				days = 0;
				salary.setYear_month(year + "- "+ month);
				salary.setTotal_time(total_time);
				total_time = 0;
				salary.setWage(Integer.parseInt(login_employee.getWage()));
				salary.setSalary((int)(Integer.parseInt(login_employee.getWage()) * total_time / 60.0));
				salary_list.add(salary);
			}else{
//				private long salary;
//
//				private Integer days;
//
//				private String year_month;
//
//				private long total_time;
//
//				private Integer wage;
			}
//			// 月給を計算するために分に変換
//			String[] split1 = output.split(":");
//			long minutes2 = TimeUnit.HOURS.toMinutes(Integer.parseInt(split1[0])) + Integer.parseInt(split1[1]);
//
//			if (values[0].equals(year) && values[1].equals(month)) {
//					total_time = minutes2;
//					days++;
//			} else {
//				Salary salarys = new Salary();
//				salarys.setSalary(salary);
//				salary_list.add(salarys);
//				days = 0;
//				total_time = 0;
//			}
		}
		for(Salary s : salary_list){
			System.out.println("年月" + s.getYear_month());
			System.out.println("出勤日数: " + s.getDays());
			System.out.println("労働時間: " + s.getTotal_time() + "分");
			System.out.println("月給: " + s.getSalary());
		}
		em.close();
		request.setAttribute("salary", salary_list);
		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/salary/index.jsp");
		rd.forward(request, response);
	}
}
