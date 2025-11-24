package utp.ac.pa.sistema.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class HistorialClinico {
    private String idHist;
    private Paciente paciente;
    private List<EntradaHistorial> entradas;

    public HistorialClinico(Paciente paciente) {
        this.idHist = UUID.randomUUID().toString();
        this.paciente = paciente;
        this.entradas = new ArrayList<>();
    }

    public void agregarEntrada(EntradaHistorial e) {
        if (e != null) entradas.add(e);
    }

    public List<EntradaHistorial> obtenerCronologico() {
        return Collections.unmodifiableList(entradas);
    }

    public String getIdHist() { return idHist; }
    public Paciente getPaciente() { return paciente; }
}
