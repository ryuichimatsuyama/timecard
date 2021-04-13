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
import utils.DBUtil;

/**
 * Servlet implementation class MessageIndexServlet
 */
@WebServlet("/messages/index")
public class MessageIndexServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public MessageIndexServlet() {
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
        //		ログイン情報を
        Employee login_employee = (Employee) request.getSession().getAttribute("login_employee");
        //		自分以外の従業員
        List<Employee> messages = em.createNamedQuery("getBossCandidates", Employee.class)
                .setParameter("id", login_employee.getId()).getResultList();
        // DAOの破棄
        em.close();
        //メッセージをセット
        request.setAttribute("messages", messages);
        // セッションスコープにフラッシュメッセージが残っているならば
        if (request.getSession().getAttribute("flush") != null) {
            // セッションスコープにフラッシュメッセージとしてリクエストスコープにセット
            request.setAttribute("flush", request.getSession().getAttribute("flush"));
            // リクエストスコープにセットされたフラッシュメッセージを削除する
            request.getSession().removeAttribute("flush");
        }
        // 画面遷移
        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/messages/index.jsp");
        rd.forward(request, response);
    }

}
