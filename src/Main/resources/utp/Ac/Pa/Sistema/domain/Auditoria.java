package utp.ac.pa.sistema.domain;

import java.time.LocalDateTime;
import java.util.UUID;

public class Auditoria {
    private String idAudit;
    private String accion;
    private Usuario usuario;
    private LocalDateTime timestamp;

    public Auditoria(String accion, Usuario usuario) {
        this.idAudit = UUID.randomUUID().toString();
        this.accion = accion;
        this.usuario = usuario;
        this.timestamp = LocalDateTime.now();
    }

    public static Auditoria registrarAccion(String a, Usuario u) {
        return new Auditoria(a, u);
    }

    public String getIdAudit() { return idAudit; }
    public String getAccion() { return accion; }
    public Usuario getUsuario() { return usuario; }
    public LocalDateTime getTimestamp() { return timestamp; }

    @Override
    public String toString() {
        return timestamp + " - " + usuario + " - " + accion;
    }
}
