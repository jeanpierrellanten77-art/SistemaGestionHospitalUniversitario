package utp.ac.pa.sistema.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class GeneradorReportes {
    private List<Auditoria> auditorias;

    public GeneradorReportes() {
        this.auditorias = new ArrayList<>();
    }

    public void addAuditoria(Auditoria a) {
        if (a != null) auditorias.add(a);
    }

    public Report generarReporteEventos(List<Auditoria> auds) {
        List<Auditoria> source = (auds == null) ? auditorias : auds;

        String id = UUID.randomUUID().toString();
        String title = "Reporte de Eventos";
        StringBuilder sb = new StringBuilder();

        for (Auditoria a : source) {
            sb.append(a.toString()).append("\n");
        }

        return new Report(id, title, sb.toString(), new ArrayList<>(source));
    }

    public List<Auditoria> getAuditorias() {
        return auditorias;
    }
}