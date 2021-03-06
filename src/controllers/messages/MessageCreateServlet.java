package controllers.messages;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
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
 * Servlet implementation class MessageCreateServlet
 */
@WebServlet("/messages/create")
public class MessageCreateServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public MessageCreateServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // DAOインスタンスの生成
        EntityManager em = DBUtil.createEntityManager();
        // ログイン情報
        Employee login_employee = (Employee) request.getSession().getAttribute("login_employee");
        // セッションスコープから従業員のIDを取得して
        // 該当のIDの従業員1件のみをデータベースから取得
        Employee e = em.find(Employee.class, (Integer) (request.getSession().getAttribute("employee_id")));
        // メッセージのインスタンスを生成
        Message m = new Message();
        // mの各フィールドにデータを代入
        m.setGet(e);
        m.setSend(login_employee);
        Timestamp currentTime = new Timestamp(System.currentTimeMillis());
        m.setCreated_at(currentTime);
        String message = request.getParameter("message");
        m.setMessage(message);
        // バリデーター の呼び出し
        List<String> errors = new ArrayList<String>();
//        空欄なら
        if (message == null || message.equals("")) {
            errors.add("メッセージを入力してください");
        }
        // errorsリストに1つでも追加されていたら
        if (errors.size() > 0) {
            //      相手と自分のみのメッセージのみ
            List<Message> messages = em.createNamedQuery("getOurMessages", Message.class).setParameter("send", e)
                    .setParameter("get", login_employee).setParameter("send", login_employee).setParameter("get", e)
                    .getResultList();
            // DAOの破棄
            em.close();
            // リクエストスコープに各データをセット
            request.setAttribute("messages", messages);
            request.setAttribute("name", e);
            request.setAttribute("errors", errors);
            // 前の画面に戻る
            RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/messages/show.jsp");
            rd.forward(request, response);
        } else {
            // データベースに保存
            em.getTransaction().begin();
            em.persist(m);
            em.getTransaction().commit();
            //      相手と自分のみのメッセージのみ
            List<Message> messages = em.createNamedQuery("getOurMessages", Message.class).setParameter("send", e)
                    .setParameter("get", login_employee).setParameter("send", login_employee).setParameter("get", e)
                    .getResultList();
            // DAOの破棄
            em.close();
            // リクエストスコープに各データをセット
            request.setAttribute("messages", messages);
            request.setAttribute("name", e);

            // メッセージページにリダイレクト
            RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/messages/show.jsp");
            rd.forward(request, response);
        }
    }
}