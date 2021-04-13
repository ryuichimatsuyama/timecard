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
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // DAOインスタンスの生成
        EntityManager em = DBUtil.createEntityManager();
        // ログイン従業員取得
        Employee login_employee = (Employee) request.getSession().getAttribute("login_employee");

        // 開くページ数を取得（デフォルトは1ページ目）
        int page;
        try {
            page = Integer.parseInt(request.getParameter("page"));
        } catch (Exception e) {
            page = 1;
        }
        // 自分宛の日報のみ表示。 最大件数と開始位置を指定して部下のカードを取得
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
            //localtimeに変換
            LocalTime start_localtime = LocalTime.of(Integer.parseInt(split_start[0]),
                    Integer.parseInt(split_start[1]));
            // 出勤したらnullを返す
            if (Objects.isNull(end)) {
                card.setWork_minutes("");
            } else {
                String[] split_end = end.split(":");
                //localtimeに変換
                LocalTime end_localtime = LocalTime.of(Integer.parseInt(split_end[0]), Integer.parseInt(split_end[1]));
                // 出退勤の差分を求めて分に直す
                long minutes = ChronoUnit.MINUTES.between(start_localtime, end_localtime);
                // 労働時間から休憩時間を引いて時間に直す
                Duration d = Duration.ofMinutes(minutes - minutes1);
                LocalTime time = LocalTime.MIN.plus(d);
                String output = time.toString();
                // 労働時間をセット
                card.setWork_minutes(output);
                // 日給を計算するために分に変換
                String[] split1 = output.split(":");
                long minutes2 = TimeUnit.HOURS.toMinutes(Integer.parseInt(split1[0])) + Integer.parseInt(split1[1]);
                long wage = Long.parseLong(card.getEmployee().getWage());
                // 日給をセット
                card.setWage(wage * minutes2 / 60);
            }
        }

        // 全件数を取得
        long cards_count = (long) em.createNamedQuery("getBossCount", Long.class).setParameter("boss", login_employee)
                .getSingleResult();
        // DAOの破棄
        em.close();
        // リクエストスコープに各データをセット
        request.setAttribute("cards", cards);
        request.setAttribute("cards_count", cards_count); // 全件数
        request.setAttribute("page", page);// ページ数
        // フラッシュメッセージがセッションスコープにセットされていたら
        if (request.getSession().getAttribute("flush") != null) {
            // セッションスコープ内のフラッシュメッセージをリクエストスコープに保存し、セッションスコープからは削除する
            request.setAttribute("flush", request.getSession().getAttribute("flush"));
            request.getSession().removeAttribute("flush");
        }
        // 承認待ち一覧を表示
        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/approvals/index.jsp");
        rd.forward(request, response);
    }

}
