package utp.Ac.Pa.sistema.domain;

import java.util.Date;

public class Factura {
    private int idFactura;
    private Date fecha;
    private double total;
    private Pedido pedido;

    public Factura(int idFactura, Date fecha, Pedido pedido) {
        this.idFactura = idFactura;
        this.fecha = fecha;
        this.pedido = pedido;
        this.total = pedido.calcularTotal();
    }

    public void generarFactura() {
        System.out.println("Factura generada: #" + idFactura + " - Total: $" + total);
    }

    public void imprimir() {
        System.out.println("=== FACTURA ===");
        System.out.println("ID: " + idFactura);
        System.out.println("Fecha: " + fecha);
        System.out.println("Total: $" + total);
        System.out.println("Pedido ID: " + pedido.getIdPedido());
        System.out.println("===============");
    }

    public int getIdFactura() {
        return idFactura;
    }

    public void setIdFactura(int idFactura) {
        this.idFactura = idFactura;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
        this.total = pedido.calcularTotal();
    }
}