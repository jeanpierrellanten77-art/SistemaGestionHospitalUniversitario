package utp.ac.pa.sistema.domain;

import java.util.ArrayList;
import java.util.List;

public class GestorPacientes {
    private List<Paciente> pacientesRegistrados;
    private List<Medico> medicosRegistrados;

    public GestorPacientes() {
        this.pacientesRegistrados = new ArrayList<>();
        this.medicosRegistrados = new ArrayList<>();
    }

    public Paciente registrarPaciente(Paciente p) {
        if (p == null) return null;
        if (!pacientesRegistrados.contains(p)) pacientesRegistrados.add(p);
        return p;
    }

    public Medico registrarMedico(Medico m) {
        if (m == null) return null;
        if (!medicosRegistrados.contains(m)) medicosRegistrados.add(m);
        return m;
    }

    public HistorialClinico crearHistorial(Paciente p) {
        if (p == null) return null;
        return new HistorialClinico(p);
    }

    public List<Paciente> getPacientesRegistrados() { return pacientesRegistrados; }
    public List<Medico> getMedicosRegistrados() { return medicosRegistrados; }
}
