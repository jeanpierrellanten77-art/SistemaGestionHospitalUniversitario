package utp.ac.pa.sistema.domain;

import java.time.LocalDate;
import java.time.Period;

public class Paciente {
    private String idPaciente;
    private String nombre;
    private LocalDate fechaNacimiento;

    public Paciente(String idPaciente, String nombre, LocalDate fechaNacimiento) {
        this.idPaciente = idPaciente;
        this.nombre = nombre;
        this.fechaNacimiento = fechaNacimiento;
    }

    public void registrar() { }

    public int getEdad() {
        if (fechaNacimiento == null) return 0;
        return Period.between(fechaNacimiento, LocalDate.now()).getYears();
    }

    public String getIdPaciente() { return idPaciente; }
    public String getNombre() { return nombre; }
    public LocalDate getFechaNacimiento() { return fechaNacimiento; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public void setFechaNacimiento(LocalDate fechaNacimiento) { this.fechaNacimiento = fechaNacimiento; }

    @Override
    public String toString() { return nombre + " (" + idPaciente + ")"; }
}
