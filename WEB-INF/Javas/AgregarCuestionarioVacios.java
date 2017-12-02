import Beans.Cuestionario;
import Beans.CuestionarioResuelto;

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
import java.util.Vector;

@WebServlet("/AgregarCuestionarioVacio")
public class AgregarCuestionarioVacios extends HttpServlet {
    public void doPost(HttpServletRequest request, HttpServletResponse response) {

        String base = getServletContext().getInitParameter("base");
        String usuario = request.getParameter("user");
        String password = request.getParameter("password");
        String nuevoCues = request.getParameter("nombreCuestionario");

        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        String url = "jdbc:mysql://localhost:3306/" + base;

        try {
            Connection con = DriverManager.getConnection(url, usuario, password);
            PreparedStatement anadirCuestionario = con.prepareStatement("INSERT into cuestionario(Nombre) VALUES (?)");
            anadirCuestionario.setString(1, nuevoCues);
            anadirCuestionario.executeUpdate();

            PreparedStatement nombreUsuario = con.prepareStatement("SELECT Nombre FROM usuario WHERE Usuario=?;");
            nombreUsuario.setString(1,usuario);
            ResultSet queryNombre = nombreUsuario.executeQuery();
            queryNombre.first();
            request.setAttribute("user",usuario);
            request.setAttribute("nombreCompleto",queryNombre.getString("Nombre"));
            request.setAttribute("password", password);


            PreparedStatement cuestionariosResueltos=con.prepareStatement("SELECT * FROM usuario join cuestionario_resuelto where usuario.idUsuario=cuestionario_resuelto.idUsuario;");
            ResultSet cuestionarios=cuestionariosResueltos.executeQuery();

            Vector<CuestionarioResuelto> cuestionarioResueltos=new Vector<>();
            while (cuestionarios.next()) {
                CuestionarioResuelto aux = new CuestionarioResuelto();
                aux.setIdCuestionarioResuelto(cuestionarios.getInt("idCuestionario_Resuelto"));
                aux.setNombreUsuario(cuestionarios.getString("Nombre"));
                DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                aux.setFecha(dateFormat.format(cuestionarios.getTimestamp("fecha")));
                aux.setIdCuestionario(cuestionarios.getInt("idCuestionario"));
                PreparedStatement plantilla = con.prepareStatement("SELECT * FROM centrogerontologico.cuestionario WHERE idCuestionario=?");
                plantilla.setInt(1, cuestionarios.getInt("idCuestionario"));
                ResultSet queryPlantilla = plantilla.executeQuery();
                queryPlantilla.first();
                aux.setNombrePlantilla(queryPlantilla.getString("Nombre"));
                cuestionarioResueltos.add(aux);
            }
            request.setAttribute("cuestionarios",cuestionarioResueltos);

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

            RequestDispatcher disp=getServletContext().getRequestDispatcher("/Investigador.jsp");
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
