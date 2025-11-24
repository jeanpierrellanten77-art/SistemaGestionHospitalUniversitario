import java.util.List;

public class Restaurante {
    private String direccion;
    private String nombre;
    private List<Mesa> mesas;
    private List<Empleado> empleados;
    private Menu menu;
    private Inventario inventario;

    public void abrirRestaurante() {}
    public void cerrarRestaurante() {}
    public void registrarPedido(Pedido pedido) {}
    public Factura generarFactura(Pedido pedido) { return null; }
    public ReporteVentas generarReporte() { return null; }
}