import Beans.Cuestionario;
import Beans.CuestionarioResuelto;
import Beans.Pregunta;
import Beans.Respuesta;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Vector;

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

            int idCuestionario = Integer.parseInt(request.getParameter("Cuestionario"));

            PreparedStatement usuario1 = con.prepareStatement("SELECT idUsuario FROM usuario WHERE Usuario=?");
            usuario1.setString(1, usuario);
            ResultSet queryUsuario = usuario1.executeQuery();
            queryUsuario.first();
            int id = queryUsuario.getInt("idUsuario");

            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Calendar cal = Calendar.getInstance();
            String fechaactual = dateFormat.format(cal.getTime());

            PreparedStatement insert = con.prepareStatement("INSERT into cuestionario_resuelto(idUsuario, fecha, idCuestionario) Values(?, ?, ?);");
            insert.setInt(1, id);
            insert.setString(2, fechaactual);
            insert.setInt(3, idCuestionario);
            insert.executeUpdate();

            PreparedStatement order = con.prepareStatement("select idCuestionario_Resuelto from cuestionario_resuelto order by idCuestionario_Resuelto desc;");
            ResultSet queryOrder = order.executeQuery();
            queryOrder.first();
            int idLast = queryOrder.getInt("idCuestionario_Resuelto");

            PreparedStatement preguntas = con.prepareStatement("SELECT * FROM pregunta WHERE idCuestionario=?");
            preguntas.setInt(1,idCuestionario);
            ResultSet queryPreguntas = preguntas.executeQuery();    //Query para buscar pregunta
            while (queryPreguntas.next()) {
                String resp = request.getParameter(queryPreguntas.getString("idPregunta"));
                PreparedStatement aux = con.prepareStatement("SELECT idRespuesta FROM respuesta WHERE descripcion=? AND idPregunta=?");//agregar otro a tabla de respuestas
                System.out.println(resp+", "+queryPreguntas.getInt("idPregunta"));
                aux.setString(1, resp);
                aux.setInt(2, queryPreguntas.getInt("idPregunta"));
                ResultSet queryId = aux.executeQuery();
                queryId.first();
                String otroresp = request.getParameter(queryPreguntas.getString("idPregunta") + "-other");
                PreparedStatement insert2 = con.prepareStatement("INSERT into cuestionario_resuelto_respuesta(idCuestionarioR,idRespuesta, abierta) Values(?,?,?)");
                insert2.setInt(1, idLast);
                insert2.setInt(2, queryId.getInt("idRespuesta"));
                insert2.setString(3, otroresp);
                insert2.executeUpdate();

            }
            PreparedStatement nombreUsuario = con.prepareStatement("SELECT Nombre FROM usuario WHERE Usuario=?;");
            nombreUsuario.setString(1, usuario);
            ResultSet queryNombre = nombreUsuario.executeQuery();
            queryNombre.first();
            request.setAttribute("user", usuario);
            request.setAttribute("nombreCompleto", queryNombre.getString("Nombre"));
            request.setAttribute("password", password);
            PreparedStatement statement = con.prepareStatement("SELECT * FROM usuario join cuestionario_resuelto where usuario.idUsuario=cuestionario_resuelto.idUsuario AND usuario.idUsuario=?;");
            statement.setInt(1, id);
            ResultSet cuestionarios = statement.executeQuery();
            Vector<CuestionarioResuelto> cuestionarioResueltos = new Vector<>();
            while (cuestionarios.next()) {
                CuestionarioResuelto aux = new CuestionarioResuelto();
                aux.setIdCuestionarioResuelto(cuestionarios.getInt("idCuestionario_Resuelto"));
                aux.setNombreUsuario(cuestionarios.getString("Nombre"));
                DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                aux.setFecha(dateFormat.format(cuestionarios.getTimestamp("fecha")));
                aux.setIdCuestionario(cuestionarios.getInt("idCuestionario"));
                PreparedStatement plantilla = con.prepareStatement("SELECT * FROM centrogerontologico.cuestionario WHERE idCuestionario=?");
                plantilla.setInt(1, cuestionarios.getInt("idCuestionario"));
                ResultSet queryPlantilla = plantilla.executeQuery();
                queryPlantilla.first();
                aux.setNombrePlantilla(queryPlantilla.getString("Nombre"));
                cuestionarioResueltos.add(aux);
            }
            request.setAttribute("cuestionarios", cuestionarioResueltos);


            PreparedStatement cuestionariosVacios = con.prepareStatement("SELECT * FROM cuestionario");
            ResultSet queryCuestionariosVacios = cuestionariosVacios.executeQuery();

            Vector<Cuestionario> cuestionarios1 = new Vector<>();
            while (queryCuestionariosVacios.next()){
                Cuestionario aux = new Cuestionario();
                aux.idCuestionario = queryCuestionariosVacios.getInt("idCuestionario");
                aux.nombre = queryCuestionariosVacios.getString("Nombre");
                cuestionarios1.add(aux);
            }
            request.setAttribute("cuestionariosVacios", cuestionarios1);

            RequestDispatcher disp = getServletContext().getRequestDispatcher("/Trabajador.jsp");
            try {
                disp.forward(request, response);
            } catch (ServletException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
