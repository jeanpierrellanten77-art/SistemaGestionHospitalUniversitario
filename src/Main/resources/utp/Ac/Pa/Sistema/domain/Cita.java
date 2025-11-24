package utp.ac.pa.sistema.domain;

import java.time.LocalDateTime;
import java.util.UUID;

public class Cita {
    private String idCita;
    private Paciente paciente;
    private Medico medico;
    private LocalDateTime fechaHora;
    private String estado;

    public Cita(Paciente paciente, Medico medico, LocalDateTime fechaHora) {
        this.idCita = UUID.randomUUID().toString();
        this.paciente = paciente;
        this.medico = medico;
        this.fechaHora = fechaHora;
        this.estado = "Pendiente";
    }

    public void confirmar() { estado = "Confirmada"; }
    public void cancelar() { estado = "Cancelada"; }

    public String getIdCita() { return idCita; }
    public Paciente getPaciente() { return paciente; }
    public Medico getMedico() { return medico; }
    public LocalDateTime getFechaHora() { return fechaHora; }
    public String getEstado() { return estado; }
    public void setFechaHora(LocalDateTime fechaHora) { this.fechaHora = fechaHora; }

    @Override
    public String toString() {
        return "Cita{" + idCita + ", paciente=" + paciente + ", medico=" + medico + ", fecha=" + fechaHora + ", estado=" + estado + '}';
    }
}
