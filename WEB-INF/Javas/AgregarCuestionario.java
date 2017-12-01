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

            PreparedStatement usuario1 = con.prepareStatement("SELECT idUsuario FROM usuario WHERE Usuario=?");
            usuario1.setString(1, usuario);
            ResultSet queryUsuario = usuario1.executeQuery();
            queryUsuario.first();
            int id=queryUsuario.getInt("idUsuario");

            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Calendar cal = Calendar.getInstance();
            String fechaactual=dateFormat.format(cal.getTime());

            PreparedStatement insert=con.prepareStatement("INSERT into cuestionario_resuelto(idUsuario, fecha) Values(?, ?);");
            insert.setInt(1, id);
            insert.setString(2,fechaactual);
            insert.executeUpdate();

            PreparedStatement order = con.prepareStatement("select idCuestionario_Resuelto from cuestionario_resuelto order by idCuestionario_Resuelto desc;");
            ResultSet queryOrder = order.executeQuery();
            queryOrder.first();
            int idLast=queryOrder.getInt("idCuestionario_Resuelto");

            PreparedStatement preguntas = con.prepareStatement("SELECT * FROM pregunta");
            ResultSet queryPreguntas = preguntas.executeQuery();    //Query para buscar pregunta
            while(queryPreguntas.next()){
                String resp=request.getParameter(queryPreguntas.getString("Titulo"));
                PreparedStatement aux = con.prepareStatement("SELECT idRespuesta FROM respuesta WHERE descripcion=?");
                aux.setString(1,resp);
                ResultSet queryId = aux.executeQuery();
                queryId.first();
                PreparedStatement insert2=con.prepareStatement("INSERT into cuestionario_resuelto_respuesta(idCuestionarioR,idRespuesta) Values(?,?)");
                insert2.setInt(1,idLast);
                insert2.setInt(2, queryId.getInt("idRespuesta"));
                insert2.executeUpdate();

            }
            PreparedStatement nombreUsuario = con.prepareStatement("SELECT Nombre FROM usuario WHERE Usuario=?;");
            nombreUsuario.setString(1,usuario);
            ResultSet queryNombre = nombreUsuario.executeQuery();
            queryNombre.first();
            request.setAttribute("user",usuario);
            request.setAttribute("nombreCompleto",queryNombre.getString("Nombre"));
            request.setAttribute("password", password);
            PreparedStatement statement=con.prepareStatement("SELECT * FROM usuario join cuestionario_resuelto where usuario.idUsuario=cuestionario_resuelto.idUsuario AND usuario.idUsuario=?;");
            statement.setInt(1, id);
            ResultSet cuestionarios=statement.executeQuery();
            Vector<CuestionarioResuelto> cuestionarioResueltos=new Vector<>();
            while(cuestionarios.next()){
                CuestionarioResuelto aux=new CuestionarioResuelto();
                aux.setIdCuestionarioResuelto(cuestionarios.getInt("idCuestionario_Resuelto"));
                aux.setNombreUsuario(cuestionarios.getString("Nombre"));
                DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                aux.setFecha(dateFormat.format(cuestionarios.getTimestamp("fecha")));
                cuestionarioResueltos.add(aux);
            }
            request.setAttribute("cuestionarios",cuestionarioResueltos);
            RequestDispatcher disp=getServletContext().getRequestDispatcher("/Trabajador.jsp");
            try {
                disp.forward(request,response);
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
