package controllers.boards;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import models.Board;
import models.Employee;
import utils.DBUtil;

import javax.persistence.EntityManager;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

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
        // DAOインスタンスの生成
        EntityManager em = DBUtil.createEntityManager();
        //boardインスタンス生成
        Board b = new Board();
        //メッセージをセット
        String message = request.getParameter("message");
        b.setMessage(message);

        // partという変数を作る
        Part part = request.getPart("file");
        if (part.getSize() != 0) {
            // part(主にjsp)から送られてきたファイル名を取得
            String filename = this.getFileName(part);
            b.setFile(filename);
            String filePath = getServletContext().getRealPath("/images/") + filename;
            File uploadDir = new File(getServletContext().getRealPath("/images/"));
            if (!uploadDir.exists()) {
                uploadDir.mkdir();
            }
            part.write(filePath);
            /* S3 */
            String region = (String) this.getServletContext().getAttribute("region");
            String bucketName = (String) this.getServletContext().getAttribute("bucketName");
            // クライアントを生成
            AmazonS3 s3 = AmazonS3ClientBuilder.standard()
                    // リージョンを AP_NORTHEAST_1(東京) に設定
                    .withRegion(region).build();
            // === ファイルから直接アップロードする場合 ===
            // アップロードするファイル
            File file = new File(filePath);
            // ファイルをアップロード
            s3.putObject(
                    // アップロード先バケット名
                    bucketName,
                    // アップロード後のキー名
                    "images/" + filename,
                    // ファイルの実体
                    file);
        }
        //        作成日時セット
        Timestamp currentTime = new Timestamp(System.currentTimeMillis());
        b.setCreated_at(currentTime);
        //        作成者セット
        b.setEmployee((Employee) request.getSession().getAttribute("login_employee"));
        // バリデーター の呼び出し
        List<String> errors = new ArrayList<String>();
        //        空欄ならば
        if (message == null || message.equals("")) {
            errors.add("メッセージを入力してください");
        }
        // errorsリストに1つでも追加されていたら
        if (errors.size() > 0) {
            // DAOの破棄
            em.close();
            // リクエストスコープにerrorsをセット
            request.setAttribute("errors", errors);
            // 前の画面に戻る
            RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/boards/new.jsp");
            rd.forward(request, response);
        } else {
            // データベースに保存

            em.getTransaction().begin();
            em.persist(b);
            em.getTransaction().commit();
            // DAOの破棄
            em.close();
            //            boardをセット
            request.setAttribute("board", b);
            // セッションスコープにフラッシュメッセージをセットする
            request.getSession().setAttribute("flush", "送信が完了しました。");
            // 画面遷移
            response.sendRedirect(request.getContextPath() + "/boards/index");
        }
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
