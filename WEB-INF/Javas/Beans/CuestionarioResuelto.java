package Beans;

public class CuestionarioResuelto {
    public int idCuestionarioResuelto;
    public int idUsuario;
    public String nombreUsuario;
    public String fecha;

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public int getIdCuestionarioResuelto() {
        return idCuestionarioResuelto;
    }

    public void setIdCuestionarioResuelto(int idCuestionarioResuelto) {
        this.idCuestionarioResuelto = idCuestionarioResuelto;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }


}
