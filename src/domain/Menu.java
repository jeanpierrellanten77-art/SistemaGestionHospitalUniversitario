package utp.Ac.Pa.sistema.domain;

import java.util.ArrayList;
import java.util.List;

public class Menu {
    private List<Plato> platosDisponibles;

    public Menu() {
        this.platosDisponibles = new ArrayList<>();
    }

    public void agregarPlato(Plato plato) {
        platosDisponibles.add(plato);
    }

    public void eliminarPlato(int idPlato) {
        platosDisponibles.removeIf(plato -> plato.getIdPlato() == idPlato);
    }

    public List<Plato> obtenerPlatos() {
        return new ArrayList<>(platosDisponibles);
    }

    public List<Plato> getPlatosDisponibles() {
        return platosDisponibles;
    }
}