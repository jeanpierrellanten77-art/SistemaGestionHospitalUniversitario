package utp.ac.pa.sistema.domain;

import java.util.ArrayList;
import java.util.List;

public class Medico {
    private String idMedico;
    private String nombre;
    private List<Especialidad> especialidades;

    public Medico(String idMedico, String nombre) {
        this.idMedico = idMedico;
        this.nombre = nombre;
        this.especialidades = new ArrayList<>();
    }

    public void emitirReceta(Receta r) { }

    public void addEspecialidad(Especialidad e) {
        if (e != null && !especialidades.contains(e)) especialidades.add(e);
    }

    public String getIdMedico() { return idMedico; }
    public String getNombre() { return nombre; }
    public List<Especialidad> getEspecialidades() { return especialidades; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    @Override
    public String toString() { return nombre + " (" + idMedico + ")"; }
}
