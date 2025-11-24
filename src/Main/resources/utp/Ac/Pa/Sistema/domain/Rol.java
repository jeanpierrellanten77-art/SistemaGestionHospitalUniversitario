package utp.ac.pa.sistema.domain;

import java.util.HashSet;
import java.util.Set;

public class Rol {
    private String idRol;
    private String nombre;
    private Set<String> permisos;

    public Rol(String idRol, String nombre) {
        this.idRol = idRol;
        this.nombre = nombre;
        this.permisos = new HashSet<>();
    }

    public boolean tienePermiso(String perm) { return permisos.contains(perm); }
    public void addPermiso(String perm) { if (perm != null) permisos.add(perm); }

    public String getIdRol() { return idRol; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
}
