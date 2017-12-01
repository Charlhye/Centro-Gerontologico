import Beans.Usuario;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;
import java.io.IOException;
import java.sql.*;
import java.util.Vector;

@WebServlet("/AltaUsuario")
public class AltaUsuario extends HttpServlet{
    public void doPost(HttpServletRequest request, HttpServletResponse response){
        String base = getServletContext().getInitParameter("base");
        String usuario = request.getParameter("user");
        String password = request.getParameter("password");

        String usuarioNuevo = request.getParameter("usuarioNuevo");
        String passNuevo= request.getParameter("passNuevo");
        String ocupacion = request.getParameter("ocupacion");

        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        String url = "jdbc:mysql://localhost:3306/" + base;

        try {
            Connection con = DriverManager.getConnection(url, usuario, password);
            PreparedStatement insert=con.prepareStatement("INSERT into usuario(Tipo_Usuario, Nombre) Values(?, ?);");
            insert.setString(1,ocupacion);
            insert.setString(2,usuarioNuevo);
            insert.executeUpdate();
            PreparedStatement addUser=con.prepareStatement("GRANT SELECT,ALTER,UPDATE,INSERT,LOCK TABLES ON centrogerontologico.* TO ?@? IDENTIFIED BY ?;");
            addUser.setString(1,usuarioNuevo);
            addUser.setString(2,"localhost");
            addUser.setString(3,passNuevo);
            addUser.executeUpdate();


            PreparedStatement usuarios=con.prepareStatement("SELECT * FROM usuario WHERE NOT Nombre=?");
            usuarios.setString(1,"administrador");
            ResultSet queryUsers=usuarios.executeQuery();
            Vector<Usuario> usuarios1=new Vector<>();
            while (queryUsers.next()){
                Usuario aux=new Usuario();
                aux.setIdUsuario(queryUsers.getInt("idUsuario"));
                aux.setNombre(queryUsers.getString("Nombre"));
                aux.setTipoUsuario(queryUsers.getString("Tipo_Usuario"));
                usuarios1.add(aux);
            }
            request.setAttribute("usuarios",usuarios1);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        request.setAttribute("user",usuario);
        request.setAttribute("password", password);
        request.setAttribute("statusAlta", "Registrado");
        RequestDispatcher disp=getServletContext().getRequestDispatcher("/Administrador.jsp");
        try {
            disp.forward(request,response);
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
