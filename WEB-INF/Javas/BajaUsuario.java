import Beans.Usuario;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.*;
import java.util.Vector;

@WebServlet("/BajaUsuario")
public class BajaUsuario extends HttpServlet {
    public void doPost(HttpServletRequest request, HttpServletResponse response) {
        String base = getServletContext().getInitParameter("base");
        String usuario = request.getParameter("user");
        String password = request.getParameter("password");
        String usuarioABorrar =request.getParameter("usuarioABorrar");

        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        String url = "jdbc:mysql://localhost:3306/" + base;

        try {
            Connection con = DriverManager.getConnection(url, usuario, password);
            PreparedStatement tabla=con.prepareStatement("DROP USER ?@?;");
            tabla.setString(1,usuarioABorrar);
            tabla.setString(1,"localhost");
            tabla.executeUpdate();
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
        request.setAttribute("statusBaja", "Borrado");

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
