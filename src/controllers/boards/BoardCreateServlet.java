package controllers.boards;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;

import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import models.Board;
import utils.DBUtil;

/**
 * Servlet implementation class BoardCreateServlet
 */
@MultipartConfig
@WebServlet("/boards/create")
public class BoardCreateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public BoardCreateServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		EntityManager em = DBUtil.createEntityManager();

		Board b = new Board();

		String message = request.getParameter("message");
		b.setMessage(message);

		// partという変数を作る
		Part part = request.getPart("file");
		// part(主にjsp)から送られてきたファイル名を取得
		String filename = this.getFileName(part);
		b.setFile(filename);
		String filePath = getServletContext().getRealPath("/images/") + filename;
		System.out.println("filepath: "+filePath);
		File uploadDir = new File(getServletContext().getRealPath("/images/"));
		if (!uploadDir.exists()) {
			uploadDir.mkdir();
		}
		part.write(filePath);
		Timestamp currentTime = new Timestamp(System.currentTimeMillis());
		b.setCreated_at(currentTime);

		em.getTransaction().begin();
		em.persist(b);
		em.getTransaction().commit();
		em.close();
		request.setAttribute("board", b);

		response.sendRedirect(request.getContextPath() + "/boards/index");

	}

	private String getFileName(Part part) {
		String filename = null;
		for (String dispotion : part.getHeader("Content-Disposition").split(";")) {
			if (dispotion.trim().startsWith("filename")) {
				filename = dispotion.substring(dispotion.indexOf("=") + 1).replace("\"", "").trim();
				filename = filename.substring(filename.lastIndexOf("\\") + 1);
				break;
			}
		}
		return filename;
	}

}
