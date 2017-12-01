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

@WebServlet("/CambiarPassword")
public class CambiarPassword extends HttpServlet {
    public void doPost(HttpServletRequest request, HttpServletResponse response) {
        String base = getServletContext().getInitParameter("base");
        String usuario = request.getParameter("user");
        String password = request.getParameter("password");

        String usuarioAVer = request.getParameter("usuarioACambiar");
        String viejoPass = request.getParameter("viejoPass");
        String nuevoPass = request.getParameter("nuevoPass");
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        String url = "jdbc:mysql://localhost:3306/" + base;

        Connection adminCon = null;
        try {
            adminCon = DriverManager.getConnection(url, usuario, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            Connection con = DriverManager.getConnection(url, usuarioAVer, viejoPass);//Checar si el viejo pass es correcto

            try {
                PreparedStatement cambiarPass=adminCon.prepareStatement("SET PASSWORD FOR ?@? =?");
                cambiarPass.setString(1, usuarioAVer);
                cambiarPass.setString(2,"localhost");
                cambiarPass.setString(3,nuevoPass);
                cambiarPass.executeUpdate();
                request.setAttribute("statusCambioPass", "Cambiado");
            } catch (SQLException e) {
                e.printStackTrace();
            }

        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("statusCambioPass", "Error: El password anterior no es correcto");
        }

        try {
            PreparedStatement usuarios = adminCon.prepareStatement("SELECT * FROM usuario WHERE NOT Nombre=? AND usuario.Activo=1");
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
        } catch (SQLException e) {
            e.printStackTrace();
        }

        request.setAttribute("user",usuario);
        request.setAttribute("password", password);

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