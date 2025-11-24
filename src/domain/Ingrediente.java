package utp.Ac.Pa.sistema.domain;

public class Ingrediente {
    private String nombre;
    private String unidadMedida;

    public Ingrediente(String nombre, String unidadMedida) {
        this.nombre = nombre;
        this.unidadMedida = unidadMedida;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getUnidadMedida() {
        return unidadMedida;
    }

    public void setUnidadMedida(String unidadMedida) {
        this.unidadMedida = unidadMedida;
    }
}