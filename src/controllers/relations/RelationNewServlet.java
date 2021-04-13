package controllers.relations;

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
 * Servlet implementation class RelationNewServlet
 */
@WebServlet("/relations/new")
public class RelationNewServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public RelationNewServlet() {
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
        // 自分の従業員情報
        Employee login_employee = (Employee) request.getSession().getAttribute("login_employee");
        // 自分以外の従業員員一覧を取得する。
        List<Employee> employees = em.createNamedQuery("getBossCandidates", Employee.class)
                .setParameter("id", login_employee.getId()).getResultList();
        // DAOの破棄
        em.close();
        //		従業員をセット
        request.setAttribute("employees", employees);
        // 画面遷移
        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/relations/new.jsp");
        rd.forward(request, response);
    }

}
