package Beans;

public class Respuesta {
    public int idRespuesta;
    public int tipoDePregunta;
    public String descripcion;

    public int getIdRespuesta() {
        return idRespuesta;
    }

    public void setIdRespuesta(int idRespuesta) {
        this.idRespuesta = idRespuesta;
    }

    public int getTipoDePregunta() {
        return tipoDePregunta;
    }

    public void setTipoDePregunta(int tipoDePregunta) {
        this.tipoDePregunta = tipoDePregunta;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
