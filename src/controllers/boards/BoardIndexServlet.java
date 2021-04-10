package controllers.boards;

import java.io.IOException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Board;
import utils.DBUtil;

/**
 * Servlet implementation class BoardIndexServlet
 */
@WebServlet("/boards/index")
public class BoardIndexServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public BoardIndexServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		EntityManager em = DBUtil.createEntityManager();
		// 該当のIDの従業員1件のみをデータベースから取得
		List<Board> boards = em.createNamedQuery("getAllMessages", Board.class)
				.getResultList();
		em.close(); // メッセージデータをリクエストスコープにセットしてshow.jspを呼び出す
		request.setAttribute("boards", boards);

		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/boards/index.jsp");
		rd.forward(request, response);
	}

}
