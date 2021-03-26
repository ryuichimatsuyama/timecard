package controllers.card;

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
import models.Employee;
import models.Relation;
import utils.DBUtil;

/**
 * Servlet implementation class CardBossServlet
 */
@WebServlet("/card/boss")
public class CardBossServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public CardBossServlet() {
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

		List<Relation> relations = em.createNamedQuery("getMyBoss", Relation.class)
			.setParameter("employee", login_employee).getResultList();


        Card r = em.find(Card.class, Integer.parseInt(request.getParameter("id")));

        em.close();

        if(r != null && login_employee.getId() == r.getEmployee().getId()) {
        	request.setAttribute("relations", relations);
            request.setAttribute("card", r);
            request.setAttribute("_token", request.getSession().getId());
            request.getSession().setAttribute("card_id", r.getId());
        }

        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/cards/end.jsp");
        rd.forward(request, response);
	}

}
