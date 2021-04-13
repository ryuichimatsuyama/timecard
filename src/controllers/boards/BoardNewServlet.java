package controllers.boards;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Board;

/**
 * Servlet implementation class BoardNewServlet
 */
@WebServlet("/boards/new")
public class BoardNewServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public BoardNewServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // TODO Auto-generated method stub
        // Boardインスタンス生成
        Board b = new Board();
        // board情報をリクエストスコープに登録
        request.setAttribute("board", b);
        // 投稿ページを表示
        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/boards/new.jsp");
        rd.forward(request, response);
    }

}
