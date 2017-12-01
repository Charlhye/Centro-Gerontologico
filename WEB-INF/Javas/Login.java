import Beans.*;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;
import java.io.IOException;
import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
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

            PreparedStatement statement=con.prepareStatement("SELECT * FROM usuario where Usuario=?;");
            statement.setString(1,usuario);
            ResultSet query=statement.executeQuery();
            query.first();

            Vector<CuestionarioResuelto> cuestionarioResueltos=new Vector<>();
            PreparedStatement statement2;
            ResultSet cuestionarios;

            int iduser=query.getInt("idUsuario");
            switch (query.getString("Tipo_Usuario")){
                case "Administrador":
                    request.setAttribute("user",usuario);
                    request.setAttribute("password", password);
                    PreparedStatement usuarios=con.prepareStatement("SELECT * FROM usuario WHERE NOT Nombre=? AND usuario.Activo=1");
                    usuarios.setString(1,"administrador");
                    ResultSet queryUsers=usuarios.executeQuery();
                    Vector<Usuario> usuarios1=new Vector<>();
                    while (queryUsers.next()){
                        Usuario aux=new Usuario();
                        aux.setIdUsuario(queryUsers.getInt("idUsuario"));
                        aux.setUsuario(queryUsers.getString("Usuario"));
                        aux.setNombre(queryUsers.getString("Nombre"));
                        aux.setTipoUsuario(queryUsers.getString("Tipo_Usuario"));
                        usuarios1.add(aux);
                    }
                    request.setAttribute("usuarios",usuarios1);
                    disp=getServletContext().getRequestDispatcher("/Administrador.jsp");
                    break;
                case "Investigador" :
                    PreparedStatement nombreUsuario = con.prepareStatement("SELECT Nombre FROM usuario WHERE Usuario=?;");
                    nombreUsuario.setString(1,usuario);
                    ResultSet queryNombre = nombreUsuario.executeQuery();
                    queryNombre.first();
                    request.setAttribute("user",usuario);
                    request.setAttribute("nombreCompleto",queryNombre.getString("Nombre"));
                    request.setAttribute("password", password);
                    disp=getServletContext().getRequestDispatcher("/Investigador.jsp");

                    statement2=con.prepareStatement("SELECT * FROM usuario join cuestionario_resuelto where usuario.idUsuario=cuestionario_resuelto.idUsuario;");
                    cuestionarios=statement2.executeQuery();

                    while (cuestionarios.next()) {
                        CuestionarioResuelto aux = new CuestionarioResuelto();
                        aux.setIdCuestionarioResuelto(cuestionarios.getInt("idCuestionario_Resuelto"));
                        aux.setNombreUsuario(cuestionarios.getString("Nombre"));
                        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        aux.setFecha(dateFormat.format(cuestionarios.getTimestamp("fecha")));
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
                    break;
                case "Trabajador" :
                    PreparedStatement nombreUsuario2 = con.prepareStatement("SELECT Nombre FROM usuario WHERE Usuario=?;");
                    nombreUsuario2.setString(1,usuario);
                    ResultSet queryNombre2 = nombreUsuario2.executeQuery();
                    queryNombre2.first();
                    request.setAttribute("user",usuario);
                    request.setAttribute("nombreCompleto",queryNombre2.getString("Nombre"));
                    request.setAttribute("password", password);
                    disp=getServletContext().getRequestDispatcher("/Trabajador.jsp");

                    statement2=con.prepareStatement("SELECT * FROM usuario join cuestionario_resuelto where usuario.idUsuario=cuestionario_resuelto.idUsuario AND usuario.idUsuario=?;");
                    statement2.setInt(1,iduser);
                    cuestionarios=statement2.executeQuery();
                    while(cuestionarios.next()){
                        CuestionarioResuelto aux=new CuestionarioResuelto();
                        aux.setIdCuestionarioResuelto(cuestionarios.getInt("idCuestionario_Resuelto"));
                        aux.setNombreUsuario(cuestionarios.getString("Nombre"));
                        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        aux.setFecha(dateFormat.format(cuestionarios.getTimestamp("fecha")));
                        cuestionarioResueltos.add(aux);
                    }
                    for (CuestionarioResuelto it:cuestionarioResueltos
                         ) {
                        System.out.println(it.idCuestionarioResuelto);
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
