package utp.ac.pa.sistema.domain;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class InventarioMedicamentos {
    private Map<Medicamento, Integer> registros;

    public InventarioMedicamentos() {
        this.registros = new HashMap<>();
    }

    public void registrarEntrada(Medicamento m, int qty) {
        if (m == null || qty <= 0) return;
        registros.merge(m, qty, Integer::sum);
    }

    public boolean registrarSalida(Medicamento m, int qty) {
        if (m == null || qty <= 0) return false;
        int actual = registros.getOrDefault(m, 0);
        if (actual < qty) return false;
        registros.put(m, actual - qty);
        return true;
    }

    public int getStock(Medicamento m) {
        return registros.getOrDefault(m, 0);
    }

    public Map<Medicamento, Integer> getRegistros() {
        return Collections.unmodifiableMap(registros);
    }
}
