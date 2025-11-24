package utp.ac.pa.sistema.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Hospital {
    private String id;
    private String nombre;
    private String direccion;
    private List<Medico> medicos;

    public Hospital(String id, String nombre, String direccion) {
        this.id = id;
        this.nombre = nombre;
        this.direccion = direccion;
        this.medicos = new ArrayList<>();
    }

    public void agregarMedico(Medico m) {
        if (m != null && !medicos.contains(m)) medicos.add(m);
    }

    public List<Medico> buscarMedicoPorEspecialidad(Especialidad e) {
        if (e == null) return new ArrayList<>();
        return medicos.stream()
                .filter(m -> m.getEspecialidades().stream()
                .anyMatch(es -> es.getIdEsp().equals(e.getIdEsp())))
                .collect(Collectors.toList());
    }

    public String getId() { return id; }
    public String getNombre() { return nombre; }
    public String getDireccion() { return direccion; }
    public List<Medico> getMedicos() { return medicos; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public void setDireccion(String direccion) { this.direccion = direccion; }

    @Override
    public String toString() { return nombre + " (" + id + ")"; }
}
