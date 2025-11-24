import java.util.ArrayList;
import java.util.List;

public class Mesa {
    private int numero;
    private int capacidad;
    private EstadoMesa estado;
    private List<Pedido> pedidosPendientes;
    private List<Pedido> pedidosListos;
    private List<Pedido> historialPedidos;

    public Mesa(int numero, int capacidad) {
        this.numero = numero;
        this.capacidad = capacidad;
        this.estado = EstadoMesa.LIBRE;
        this.pedidosPendientes = new ArrayList<>();
        this.pedidosListos = new ArrayList<>();
        this.historialPedidos = new ArrayList<>();
    }

    public void ocupar() {
        this.estado = EstadoMesa.OCUPADA;
    }

    public void liberar() {
        this.estado = EstadoMesa.LIBRE;
    }

    public void notificarListo(Pedido pedido) {
        pedidosPendientes.remove(pedido);
        pedidosListos.add(pedido);
        historialPedidos.add(pedido);
    }

    // Getters y Setters
    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public int getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(int capacidad) {
        this.capacidad = capacidad;
    }

    public EstadoMesa getEstado() {
        return estado;
    }

    public void setEstado(EstadoMesa estado) {
        this.estado = estado;
    }

    public List<Pedido> getPedidosPendientes() {
        return pedidosPendientes;
    }

    public List<Pedido> getPedidosListos() {
        return pedidosListos;
    }

    public List<Pedido> getHistorialPedidos() {
        return historialPedidos;
    }
}