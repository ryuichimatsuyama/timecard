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

			if(tmp_year.equals(year) && tmp_month.equals(month)){
				year = tmp_year;
				month = tmp_month;
				total_time  += minutes - minutes1;
				days++;
			}else{
				Salary salary1=new Salary();
				salary1.setDays(days);
				salary1.setTotal_time(total_time);
				salary_list.add(salary1);
				total_time=0;
				days=0;
				total_time+=minutes-minutes1;
				days++;
				year=tmp_year;
				month=tmp_year;
			}
		}
		Salary salary1=new Salary();
		salary1.setDays(days);
		salary1.setTotal_time(total_time);
		salary_list.add(salary1);

		for(Salary s : salary_list){
			System.out.println("年月" + s.getYear_month());
			System.out.println("出勤日数: " + s.getDays());
			System.out.println("労働時間: " + s.getTotal_time() + "分");
			System.out.println("月給: " + s.getSalary());
				}
		em.close();
		request.setAttribute("salary_list", salary_list);
		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/salary/index.jsp");
		rd.forward(request, response);
	}
}