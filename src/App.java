import java.util.Date;

public class App {
    public static void main(String[] args) {
        // Ejemplo básico de uso para probar funcionalidad
        Restaurante restaurante = new Restaurante("La Delicia", "Calle Falsa 123");
        
        // Abrir restaurante
        restaurante.abrirRestaurante();
        
        // Crear empleados
        Empleado cocinero = new Empleado(1, "Juan Pérez", "12345678", Rol.COCINERO);
        Empleado mozo = new Empleado(2, "Ana Gómez", "87654321", Rol.MOZO);
        
        // Agregar empleados al restaurante
        restaurante.getEmpleados().add(cocinero);
        restaurante.getEmpleados().add(mozo);
        
        // Crear mesa
        Mesa mesa1 = new Mesa(1, 4);
        restaurante.getMesas().add(mesa1);
        
        // Crear pedido
        Pedido pedido = new Pedido(1, new Date(), mesa1, mozo);
        pedido.agregarPlato(new Plato(1, "Hamburguesa", 15.0, null)); // Ingredientes no definidos en este ejemplo
        
        // Registrar pedido
        restaurante.registrarPedido(pedido);
        
        System.out.println("Restaurante inicializado y pedido registrado.");
    }
}