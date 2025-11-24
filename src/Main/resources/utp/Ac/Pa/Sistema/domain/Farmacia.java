package utp.ac.pa.sistema.domain;

public class Farmacia {
    private String idFarmacia;
    private InventarioMedicamentos inventario;

    public Farmacia(String idFarmacia) {
        this.idFarmacia = idFarmacia;
        this.inventario = new InventarioMedicamentos();
    }

    public boolean solicitarMedicamento(Medicamento m, int qty) {
        return inventario.registrarSalida(m, qty);
    }

    public boolean dispensarReceta(Receta r) {
        if (r == null || !r.validar()) return false;
        for (var e : r.getItems().entrySet()) {
            if (!inventario.registrarSalida(e.getKey(), e.getValue())) return false;
        }
        return true;
    }

    public String getIdFarmacia() { return idFarmacia; }
    public InventarioMedicamentos getInventario() { return inventario; }
}
