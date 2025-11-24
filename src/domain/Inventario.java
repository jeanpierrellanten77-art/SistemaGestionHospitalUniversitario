package utp.Ac.Pa.sistema.domain;

import java.util.HashMap;
import java.util.Map;

public class Inventario {
    private Map<Ingrediente, Double> stock;

    public Inventario() {
        this.stock = new HashMap<>();
    }

    public void registrarIngrediente(Ingrediente ing) {
        if (!stock.containsKey(ing)) {
            stock.put(ing, 0.0);
        }
    }

    public void actualizarStock(Ingrediente ing, double cantidad) {
        if (stock.containsKey(ing)) {
            stock.put(ing, stock.get(ing) + cantidad);
        } else {
            stock.put(ing, cantidad);
        }
    }

    public boolean verificarDisponibilidad(Plato plato) {
        for (Ingrediente ing : plato.getIngredientes()) {
            if (!stock.containsKey(ing) || stock.get(ing) <= 0) {
                return false;
            }
        }
        return true;
    }

    public void consumir(Ingrediente ing, double cantidad) {
        if (stock.containsKey(ing)) {
            double actual = stock.get(ing);
            if (actual >= cantidad) {
                stock.put(ing, actual - cantidad);
            }
        }
    }

    public void reponer(Ingrediente ing, double cantidad) {
        if (stock.containsKey(ing)) {
            stock.put(ing, stock.get(ing) + cantidad);
        } else {
            stock.put(ing, cantidad);
        }
    }

    public Map<Ingrediente, Double> getStock() {
        return stock;
    }
}