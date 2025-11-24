package utp.ac.pa.sistema.domain;

import java.util.ArrayList;
import java.util.List;

public class Especialidad {
    private String idEsp;
    private String nombre;
    private List<Medico> medicos;

    public Especialidad(String idEsp, String nombre) {
        this.idEsp = idEsp;
        this.nombre = nombre;
               this.medicos = new ArrayList<>();
    }

    public void asignarMedico(Medico m) {
        if (m != null && !medicos.contains(m)) medicos.add(m);
        if (m != null && !m.getEspecialidades().contains(this)) m.addEspecialidad(this);
    }

    public String getIdEsp() { return idEsp; }
    public String getNombre() { return nombre; }
    public List<Medico> getMedicos() { return medicos; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    @Override
    public String toString() { return nombre + " (" + idEsp + ")"; }
}
