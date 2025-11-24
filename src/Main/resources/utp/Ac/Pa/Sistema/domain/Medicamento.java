package utp.ac.pa.sistema.domain;

import java.util.Objects;

public class Medicamento {
    private String idMed;
    private String nombre;
    private double precio;

    public Medicamento(String idMed, String nombre, double precio) {
        this.idMed = idMed;
        this.nombre = nombre;
        this.precio = precio;
    }

    public int consultarStock(InventarioMedicamentos inventario) {
        if (inventario == null) return 0;
        return inventario.getStock(this);
    }

    public String getIdMed() { return idMed; }
    public String getNombre() { return nombre; }
    public double getPrecio() { return precio; }
    public void setPrecio(double precio) { this.precio = precio; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Medicamento)) return false;
        Medicamento that = (Medicamento) o;
        return Objects.equals(idMed, that.idMed);
    }

    @Override
    public int hashCode() { return Objects.hash(idMed); }

    @Override
    public String toString() { return nombre + " (" + idMed + ")"; }
}
