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
            Statement stat=con.createStatement();
            stat.executeUpdate("delete from usuario where Nombre = '"+usuarioABorrar+"' limit 1");
            Statement tabla=con.createStatement();
            tabla.executeUpdate("DROP USER '"+usuarioABorrar+"'@'localhost';");

            Statement usuarios=con.createStatement();
            ResultSet queryUsers=usuarios.executeQuery("SELECT * FROM usuario WHERE NOT Nombre='administrador'");
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
