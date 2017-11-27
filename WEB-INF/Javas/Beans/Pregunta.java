package Beans;

import java.util.Vector;

public class Pregunta {
    public int idPregunta;
    public String titulo;
    public int idCuestionario;
    public Vector<Respuesta> respuestas;

    public int getIdPregunta() {
        return idPregunta;
    }

    public void setIdPregunta(int idPregunta) {
        this.idPregunta = idPregunta;
    }

    public String getTitulo() {
        return titulo;
    }

    public Vector<Respuesta> getRespuestas() {
        return respuestas;
    }

    public void setRespuestas(Vector<Respuesta> respuestas) {
        this.respuestas = respuestas;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;

    }

    public int getIdCuestionario() {
        return idCuestionario;
    }

    public void setIdCuestionario(int idCuestionario) {
        this.idCuestionario = idCuestionario;
    }

}
