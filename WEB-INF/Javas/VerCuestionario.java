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

@WebServlet("/VerCuestionario")
public class VerCuestionario extends HttpServlet {
    public void doPost(HttpServletRequest request, HttpServletResponse response) {
        String base = getServletContext().getInitParameter("base");
        String usuario = request.getParameter("user");
        String password = request.getParameter("password");
        int idCuestionarioR = Integer.parseInt(request.getParameter("idCuestR"));


        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        String url = "jdbc:mysql://localhost:3306/" + base;

        try {
            Connection con = DriverManager.getConnection(url, usuario, password);
            Statement respuestas=con.createStatement();
            ResultSet queryRespuestas=respuestas.executeQuery("SELECT * FROM cuestionario_resuelto join cuestionario_resuelto_respuesta where cuestionario_resuelto.idCuestionario_Resuelto=cuestionario_resuelto_respuesta.idCuestionarioR and cuestionario_resuelto.idCuestionario_Resuelto="+idCuestionarioR+";");
            Vector <Pregunta> preguntasVector = new Vector<>();
            while(queryRespuestas.next()){
                Statement preguntas=con.createStatement();
                ResultSet queryPreguntas=preguntas.executeQuery("select * from pregunta join respuesta where pregunta.idPregunta=respuesta.idPregunta and idRespuesta="+queryRespuestas.getInt("idRespuesta")+";");
                queryPreguntas.first();
                Pregunta auxPreg=new Pregunta();
                auxPreg.setTitulo(queryPreguntas.getString("Titulo"));
                auxPreg.setRespuestas(new Vector<>());
                Respuesta auxResp=new Respuesta();
                auxResp.setDescripcion(queryPreguntas.getString("Descripcion"));
                auxPreg.getRespuestas().add(auxResp);
                preguntasVector.add(auxPreg);
            }

            request.setAttribute("user",usuario);
            request.setAttribute("password", password);
            request.setAttribute("preguntas", preguntasVector);

            RequestDispatcher disp=getServletContext().getRequestDispatcher("/RespuestasCuestionario.jsp");
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