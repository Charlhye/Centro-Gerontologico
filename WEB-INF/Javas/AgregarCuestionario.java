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

            Statement usuario1 = con.createStatement();
            ResultSet queryUsuario = usuario1.executeQuery("SELECT idUsuario FROM usuario WHERE  nombre='"+usuario+"'");
            queryUsuario.first();
            int id=queryUsuario.getInt("idUsuario");
            Statement insert=con.createStatement();
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Calendar cal = Calendar.getInstance();
            String fechaactual=dateFormat.format(cal.getTime());

            insert.executeUpdate("INSERT into cuestionario_resuelto(idUsuario, fecha) Values('"+id+"', '"+fechaactual+"');");

            Statement order = con.createStatement();
            ResultSet queryOrder = order.executeQuery("select idCuestionario_Resuelto from cuestionario_resuelto order by idCuestionario_Resuelto desc;");
            queryOrder.first();
            int idLast=queryOrder.getInt("idCuestionario_Resuelto");

            Statement preguntas = con.createStatement();
            ResultSet queryPreguntas = preguntas.executeQuery("SELECT * FROM pregunta");    //Query para buscar pregunta
            while(queryPreguntas.next()){
                String resp=request.getParameter(queryPreguntas.getString("Titulo"));
                Statement aux = con.createStatement();
                System.out.println(resp);
                ResultSet queryId = aux.executeQuery("SELECT idRespuesta FROM respuesta WHERE descripcion='"+resp+"'");
                queryId.first();
                Statement insert2=con.createStatement();
                insert2.executeUpdate("INSERT into cuestionario_resuelto_respuesta(idCuestionarioR,idRespuesta) Values('"+idLast+"','"+queryId.getInt("idRespuesta")+"')");

            }

            request.setAttribute("user",usuario);
            request.setAttribute("password", password);
            Statement statement=con.createStatement();
            ResultSet cuestionarios=statement.executeQuery("SELECT * FROM usuario join cuestionario_resuelto where usuario.idUsuario=cuestionario_resuelto.idUsuario AND usuario.idUsuario="+id+";");
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
