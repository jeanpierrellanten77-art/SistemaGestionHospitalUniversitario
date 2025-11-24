package utp.ac.pa.sistema.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Tratamiento {
    private String idTrat;
    private String descripcion;
    private Medico medico;
    private List<String> observaciones;
    private boolean finalizado;

    public Tratamiento(String descripcion, Medico medico) {
        this.idTrat = UUID.randomUUID().toString();
        this.descripcion = descripcion;
        this.medico = medico;
        this.observaciones = new ArrayList<>();
        this.finalizado = false;
    }

    public void agregarObservacion(String obs) {
        if (obs != null && !obs.trim().isEmpty()) observaciones.add(obs);
    }

    public void finalizar() { finalizado = true; }

    public String getIdTrat() { return idTrat; }
    public String getDescripcion() { return descripcion; }
    public Medico getMedico() { return medico; }
    public List<String> getObservaciones() { return observaciones; }
    public boolean isFinalizado() { return finalizado; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    @Override
    public String toString() { return "Tratamiento{" + idTrat + ", " + descripcion + '}'; }
}
