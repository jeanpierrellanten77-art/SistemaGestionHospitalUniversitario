package utp.ac.pa.sistema.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class GestorCitas {
    private List<Cita> citasRegistradas;

    public GestorCitas() {
        this.citasRegistradas = new ArrayList<>();
    }

    public boolean agendarCita(Paciente p, Medico m, LocalDateTime fechaHora) {
        for (Cita c : citasRegistradas) {
            if (c.getMedico().equals(m) && c.getFechaHora().equals(fechaHora)) return false;
            if (c.getPaciente().equals(p) && c.getFechaHora().equals(fechaHora)) return false;
        }
        citasRegistradas.add(new Cita(p, m, fechaHora));
        return true;
    }

    public List<Cita> getCitasRegistradas() { return citasRegistradas; }
}
