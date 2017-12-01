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
import java.util.Vector;

@WebServlet("/RealizarEncuesta")
public class RealizarEncuesta extends HttpServlet {
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
            PreparedStatement preguntas = con.prepareStatement("SELECT * FROM pregunta");
            ResultSet queryPreguntas = preguntas.executeQuery();    //Query para buscar pregunta
            Vector<Pregunta> preguntas1 = new Vector<>();
            while(queryPreguntas.next()){
                Pregunta pregunta = new Pregunta();
                pregunta.setTitulo(queryPreguntas.getString("Titulo"));
                pregunta.setIdPregunta(queryPreguntas.getInt("idPregunta"));
                pregunta.setRespuestas(new Vector<>());
                preguntas1.add(pregunta);

            }
            PreparedStatement respuestas = con.prepareStatement("SELECT Titulo,Descripcion FROM respuesta join pregunta WHERE respuesta.idPregunta = pregunta.idPregunta");//por cada pregunta query para buscar respuestas de pregunta
            ResultSet queryRespuestas = respuestas.executeQuery();
            while(queryRespuestas.next()){
                for (Pregunta aux:preguntas1) {
                    if(aux.getTitulo().equals(queryRespuestas.getString("Titulo"))){
                        Respuesta respuesta = new Respuesta();
                        respuesta.setDescripcion(queryRespuestas.getString("Descripcion"));
                        aux.getRespuestas().add(respuesta);
                    }
                }
            }
            request.setAttribute("preguntas",preguntas1);
            request.setAttribute("user",usuario);
            request.setAttribute("password", password);
            RequestDispatcher disp=getServletContext().getRequestDispatcher("/Cuestionarios.jsp");
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
