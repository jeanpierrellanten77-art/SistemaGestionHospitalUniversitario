package utp.ac.pa.sistema.domain;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Receta {
    private String idReceta;
    private Tratamiento tratamiento;
    private Map<Medicamento, Integer> items;

    public Receta(Tratamiento tratamiento) {
        this.idReceta = UUID.randomUUID().toString();
        this.tratamiento = tratamiento;
        this.items = new HashMap<>();
    }

    public void agregarItem(Medicamento m, int qty) {
        if (m == null || qty <= 0) return;
        items.merge(m, qty, Integer::sum);
    }

    public boolean validar() { return !items.isEmpty(); }

    public String getIdReceta() { return idReceta; }
    public Tratamiento getTratamiento() { return tratamiento; }
    public Map<Medicamento, Integer> getItems() { return Collections.unmodifiableMap(items); }

    @Override
    public String toString() {
        return "Receta{" + idReceta + ", items=" + items.size() + '}';
    }
}
