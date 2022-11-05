package controllers.salary;

import java.io.IOException;
import java.sql.Date;
import java.time.Duration;
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
		// ログイン従業員取得
		Employee login_employee = (Employee) request.getSession().getAttribute("login_employee");
		// DAOインスタンスの生成
		EntityManager em = DBUtil.createEntityManager();
		// 自分の承認されたcard一覧取得
		List<Card> mycards = em.createNamedQuery("getMyApprovedTimes", Card.class)
				.setParameter("employee", login_employee).getResultList();
		int count = 0; // ループカウンター
		String year = ""; // 注目するcard情報の年
		String month = ""; // 注目するcard情報の月
		String pre_year = ""; // 前のcard情報の年
		String pre_month = ""; // 前のcard情報の月
		// 注目する月の日数
		int days = 0;
		// 注目する月の全労働時間(分)
		long total_time = 0;
		// JSPで表示するsalaryリスト
		List<Salary> salary_list = new ArrayList<Salary>();
		while (count != mycards.size()) {
			// 1つのcard情報を抜き出す
			Card card = mycards.get(count);
			// 勤務年月を取得
			Date work_date = card.getWork_date();
			// - で分離
			String[] values = work_date.toString().split("-");
			// 年を抜き出す
			year = values[0];
			// 月を抜き出す
			month = values[1];
			// 出勤時刻取得(xx:xx)
			String start = card.getStart();
			// 退勤時刻取得(xx:xx)
			String end = card.getEnd();
			// 休憩時間取得(xx:xx)
			String break_time = login_employee.getBreak_time();
			// 休憩時間を分に直す
			String[] split = break_time.split(":");
			long break_minutes = TimeUnit.HOURS.toMinutes(Integer.parseInt(split[0])) + Integer.parseInt(split[1]);
			// 出勤時間を分に直す
			String[] split_start = start.split(":");
			LocalTime start_time = LocalTime.of(Integer.parseInt(split_start[0]), Integer.parseInt(split_start[1]));
			// 退勤時間を分に直す
			String[] split_end = end.split(":");
			LocalTime end_time = LocalTime.of(Integer.parseInt(split_end[0]), Integer.parseInt(split_end[1]));
			// 出退勤の差分を求めて分に直す
			long minutes = ChronoUnit.MINUTES.between(start_time, end_time);
			// 最初のデータならば
			if (count == 0) {
				// 注目する年月の総労働実時間に加算
				Duration d1 = Duration.ofMinutes(minutes - break_minutes);
				LocalTime time = LocalTime.MIN.plus(d1);
				String output = time.toString();
				String[] split1 = output.split(":");
				long minutes2 = TimeUnit.HOURS.toMinutes(Integer.parseInt(split1[0])) + Integer.parseInt(split1[1]);
				total_time += (minutes2);
				// 次のcard情報との比較のため保存
				pre_year = year;
				pre_month = month;
				// 労働日数を1増やす
				days++;
				// カウンターを1増やす
				count++;
				// 強制的に次のループへ移動
				continue;
			} else { // 2つめ以降のデータならば
						// 前のデータと比べて年月が一緒ならば
				if (year.equals(pre_year) && month.equals(pre_month)) {
					// 注目する年月の総労働実時間に加算
					Duration d1 = Duration.ofMinutes(minutes - break_minutes);
					LocalTime time = LocalTime.MIN.plus(d1);
					String output = time.toString();
					String[] split1 = output.split(":");
					long minutes2 = TimeUnit.HOURS.toMinutes(Integer.parseInt(split1[0])) + Integer.parseInt(split1[1]);
					total_time += (minutes2);
					// 次のcard情報との比較のため保存
					pre_year = year;
					pre_month = month;
					// 労働日数を1増やす
					days++;
					// カウンターを1増やす
					count++;
					// 強制的に次のループへ移動
					continue;
				} else { // 前のデータと比べて年月が違ったならば
							// サラリーインスタンス作成
					Salary salary = new Salary();
					// フィールドをセット
					// 労働日数
					salary.setDays(days);
					// 総労働時間
					Duration d = Duration.ofMinutes(total_time);
					String s = d.toString();
					salary.setTotal_time(s.replace("PT", "").replace("H", "時間").replace("M", "分"));
					// 年月
					salary.setYear_month(pre_year + "年" + pre_month + "月");
					// 時給(円)
					int wage = Integer.parseInt(login_employee.getWage());
					salary.setWage(wage);
					// 月給 = 時給 * (総労働時間(分) / 60.0(分))
					long money = (long) (wage * (total_time / 60.0));
					salary.setSalary(money);
					// リストに追加
					salary_list.add(salary);
					// 初期化
					total_time = 0;
					days = 0;
					// 注目する年月の総労働実時間に加算
					Duration d1 = Duration.ofMinutes(minutes - break_minutes);
					LocalTime time = LocalTime.MIN.plus(d1);
					String output = time.toString();
					String[] split1 = output.split(":");
					long minutes2 = TimeUnit.HOURS.toMinutes(Integer.parseInt(split1[0])) + Integer.parseInt(split1[1]);
					total_time += (minutes2);
					// 次のcard情報との比較のため保存
					pre_year = year;
					pre_month = month;
					// 労働日数を1増やす
					days++;
					// カウンターを1増やす
					count++;
				}
			}
		}
		// サラリーインスタンス作成
		Salary salary = new Salary();
		// フィールドをセット
		// 労働日数
		salary.setDays(days);
		// 総労働時間(時)
		Duration d = Duration.ofMinutes(total_time);
		String s = d.toString();
		if (total_time == 0) {
			salary.setTotal_time(String.valueOf(0));
		} else {
			salary.setTotal_time(s.replace("PT", "").replace("H", "時間").replace("M", "分"));
		}
		// 年月
		salary.setYear_month(year + "年" + month + "月");
		// 時給(円)
		int wage = Integer.parseInt(login_employee.getWage());
		salary.setWage(wage);
		// 月給 = 時給 * (総労働時間(分) / 60.0(分))
		long money = (long) (wage * (total_time / 60.0));
		salary.setSalary(money);
		// リストに追加
		salary_list.add(salary);
		// DAOの破棄
		em.close();
		// salary_listをセット
		request.setAttribute("salary_list", salary_list);
		// 画面遷移
		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/salary/index.jsp");
		rd.forward(request, response);
	}
}