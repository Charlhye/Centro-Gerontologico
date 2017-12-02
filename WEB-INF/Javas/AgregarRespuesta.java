import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.*;

@WebServlet("/AgregarRespuesta")
public class AgregarRespuesta extends HttpServlet {
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

            PreparedStatement insertPregunta = con.prepareStatement("INSERT into pregunta(Titulo, idCuestionario) values(?,?)");
            insertPregunta.setString(1, pregunta);
            insertPregunta.setInt(2, cuestionario);
            insertPregunta.executeUpdate();

            PreparedStatement findPregunta = con.prepareStatement("SELECT * FROM pregunta WHERE Titulo=?");
            findPregunta.setString(1, pregunta);
            ResultSet queryFindPregunta = findPregunta.executeQuery();
            queryFindPregunta.first();
            int idPregunta = queryFindPregunta.getInt("idPregunta");

            int cont=1;

            while(true){
                try{
                    if(!request.getParameter(cont+"").equals(null)) {
                        PreparedStatement insertRespuesta = con.prepareStatement("INSERT into respuesta(Tipo_Pregunta, Descripcion, idPregunta) VALUES (?, ?, ?)");
                        insertRespuesta.setInt(1, 3);
                        insertRespuesta.setString(2, request.getParameter(cont + ""));
                        insertRespuesta.setInt(3, idPregunta);
                        insertRespuesta.executeUpdate();
                    }
                }catch(NullPointerException ex){
                    PreparedStatement insertRespuesta = con.prepareStatement("INSERT into respuesta(Tipo_Pregunta, Descripcion, idPregunta) VALUES (?, ?, ?)");
                    insertRespuesta.setInt(1,4);
                    insertRespuesta.setString(2, "Otro");
                    insertRespuesta.setInt(3, idPregunta);
                    insertRespuesta.executeUpdate();
                    break;
                }
                cont++;
            }

            PreparedStatement nombreUsuario = con.prepareStatement("SELECT Nombre FROM usuario WHERE Usuario=?;");
            nombreUsuario.setString(1,usuario);
            ResultSet queryNombre = nombreUsuario.executeQuery();
            queryNombre.first();
            request.setAttribute("user",usuario);
            request.setAttribute("nombreCompleto",queryNombre.getString("Nombre"));
            request.setAttribute("password", password);
            RequestDispatcher disp=getServletContext().getRequestDispatcher("/Blank.jsp");

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