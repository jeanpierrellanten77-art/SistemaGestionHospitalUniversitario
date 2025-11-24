package utp.ac.pa.sistema.domain;

import java.time.LocalDateTime;

public class EntradaHistorial {
    private LocalDateTime fecha;
    private String tipo;
    private String referenciaID;

    public EntradaHistorial(LocalDateTime fecha, String tipo, String referenciaID) {
        this.fecha = fecha;
        this.tipo = tipo;
        this.referenciaID = referenciaID;
    }

    public LocalDateTime getFecha() { return fecha; }
    public String getTipo() { return tipo; }
    public String getReferencia() { return referenciaID; }

    @Override
    public String toString() {
        return fecha + " - " + tipo + " - " + referenciaID;
    }
}
