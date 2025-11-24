package utp.Ac.Pa.sistema.domain;

import java.util.ArrayList;
import java.util.List;

public class Cocina {
    private List<Pedido> pedidosPendientes;
    private List<Pedido> pedidosListos;

    public Cocina() {
        this.pedidosPendientes = new ArrayList<>();
        this.pedidosListos = new ArrayList<>();
    }

    public void prepararPedido(Pedido pedido) {
        pedidosPendientes.add(pedido);
        // Simulación de preparación
        System.out.println("Preparando pedido #" + pedido.getIdPedido());
        // Al terminar, mover a listos
        pedidosPendientes.remove(pedido);
        pedidosListos.add(pedido);
        pedido.getMesa().notificarListo(pedido); // Notificar a la mesa
    }

    public List<Pedido> getPedidosPendientes() {
        return pedidosPendientes;
    }

    public List<Pedido> getPedidosListos() {
        return pedidosListos;
    }
}