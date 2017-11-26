import Beans.CuestionarioResuelto;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.*;
import java.util.Calendar;
import java.util.Map;
import java.util.Vector;

@WebServlet("/Login")
public class Login extends HttpServlet{
    public void doPost(HttpServletRequest request, HttpServletResponse response){
        RequestDispatcher disp=null;
        try{
            String base = getServletContext().getInitParameter("base");
            String usuario = request.getParameter("user");
            String password = request.getParameter("password");

            Class.forName("com.mysql.jdbc.Driver");
            String url = "jdbc:mysql://localhost:3306/" + base;

            Connection con = DriverManager.getConnection(url, usuario, password);
            Statement statement=con.createStatement();
            ResultSet query=statement.executeQuery("SELECT * FROM usuario where Nombre='"+usuario+"';");
            query.first();

            Vector<CuestionarioResuelto> cuestionarioResueltos=new Vector<>();
            Statement statement2=con.createStatement();
            ResultSet cuestionarios;

            int iduser=query.getInt("idUsuario");
            switch (query.getString("Tipo_Usuario")){
                case "Administrador":
                    request.setAttribute("user",usuario);
                    request.setAttribute("password", password);
                    disp=getServletContext().getRequestDispatcher("/Administrador.jsp");
                    break;
                case "Investigador" :
                    request.setAttribute("user",usuario);
                    request.setAttribute("password", password);
                    disp=getServletContext().getRequestDispatcher("/Investigador.jsp");

                    statement2=con.createStatement();
                    cuestionarios=statement2.executeQuery("SELECT Nombre, idCuestionario_Resuelto FROM usuario join cuestionario_resuelto where usuario.idUsuario=cuestionario_resuelto.idUsuario;");

                    while(cuestionarios.next()){
                        CuestionarioResuelto aux=new CuestionarioResuelto();
                        aux.setIdCuestionarioResuelto(cuestionarios.getInt("idCuestionario_Resuelto"));
                        aux.setNombreUsuario(cuestionarios.getString("Nombre"));
                        cuestionarioResueltos.add(aux);
                    }
                    request.setAttribute("cuestionarios",cuestionarioResueltos);
                    break;
                case "Trabajador" :
                    request.setAttribute("user",usuario);
                    request.setAttribute("password", password);
                    disp=getServletContext().getRequestDispatcher("/Trabajador.jsp");

                    statement=con.createStatement();
                    cuestionarios=statement2.executeQuery("SELECT Nombre, idCuestionario_Resuelto FROM usuario join cuestionario_resuelto where usuario.idUsuario=cuestionario_resuelto.idUsuario AND usuario.idUsuario="+iduser+";");

                    while(cuestionarios.next()){
                        CuestionarioResuelto aux=new CuestionarioResuelto();
                        aux.setIdCuestionarioResuelto(cuestionarios.getInt("idCuestionario_Resuelto"));
                        aux.setNombreUsuario(cuestionarios.getString("Nombre"));
                        cuestionarioResueltos.add(aux);
                    }
                    request.setAttribute("cuestionarios",cuestionarioResueltos);
                    break;
            }

            query.close();
            statement.close();
            con.close();
            disp.forward(request,response);
        }catch (Exception ex){
            request.setAttribute("error", "El usuario o la contraseña son incorrectos. Contacte al Admintrador");
            disp=getServletContext().getRequestDispatcher("/Login.jsp");
            ex.printStackTrace();
            try {
                disp.forward(request,response);
            } catch (ServletException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
