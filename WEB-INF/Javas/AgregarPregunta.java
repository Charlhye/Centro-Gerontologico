import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@WebServlet("/AgregarPregunta")
public class AgregarPregunta extends HttpServlet {
    public void doPost(HttpServletRequest request, HttpServletResponse response) {

        String base = getServletContext().getInitParameter("base");
        String usuario = request.getParameter("user");
        String password = request.getParameter("password");
        String pregunta = request.getParameter("pregunta");
        int cuestionario = Integer.parseInt(request.getParameter("Cuestionario"));

        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        String url = "jdbc:mysql://localhost:3306/" + base;

        try {
            Connection con = DriverManager.getConnection(url, usuario, password);

            request.setAttribute("user", usuario);
            request.setAttribute("password", password);
            request.setAttribute("pregunta", pregunta);
            request.setAttribute("idCuestionario", cuestionario);

            RequestDispatcher disp=getServletContext().getRequestDispatcher("/AñadirPregunta.jsp");
            disp.forward(request,response);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}