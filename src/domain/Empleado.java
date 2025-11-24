package utp.Ac.Pa.sistema.domain;

import java.util.ArrayList;
import java.util.List;

public class Empleado {
    private int idEmpleado;
    private String nombre;
    private String dni;
    private Rol rol;
    private List<Turno> turnos;

    public Empleado(int idEmpleado, String nombre, String dni, Rol rol) {
        this.idEmpleado = idEmpleado;
        this.nombre = nombre;
        this.dni = dni;
        this.rol = rol;
        this.turnos = new ArrayList<>();
    }

    public void registrarDesempeno(String observacion) {
        System.out.println("Desempeño registrado para " + nombre + ": " + observacion);
    }

    public int getIdEmpleado() {
        return idEmpleado;
    }

    public void setIdEmpleado(int idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }

    public List<Turno> getTurnos() {
        return turnos;
    }

    public void setTurnos(List<Turno> turnos) {
        this.turnos = turnos;
    }
}