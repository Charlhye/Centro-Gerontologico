import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@WebServlet("/AgregarCuestionario")
public class AgregarCuestionario extends HttpServlet {
    public void doPost(HttpServletRequest request, HttpServletResponse response) {
        String base = getServletContext().getInitParameter("base");
        String usuario = request.getParameter("user");
        String password = request.getParameter("password");

        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        String url = "jdbc:mysql://localhost:3306/" + base;


        try {
            Connection con = DriverManager.getConnection(url, usuario, password);

            //Buscar en las respuestas por pregunta
            //Subirlas a cuestionario_resuelto_respuesta


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
