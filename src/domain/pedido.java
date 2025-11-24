import java.util.List;
import java.util.Date;

public class Pedido {
    private int idPedido;
    private Date fechaHora;
    private List<Plato> platos;
    private Mesa mesa;
    private Empleado mesero;
    private boolean pagado;

    public void agregarPlato(Plato plato) {}
    public double calcularTotal() { return 0.0; }
}