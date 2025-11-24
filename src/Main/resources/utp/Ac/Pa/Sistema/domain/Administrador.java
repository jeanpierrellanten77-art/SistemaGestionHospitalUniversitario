package utp.ac.pa.sistema.domain;

public class Administrador extends Usuario {
    private int nivelAcceso;
    private GeneradorReportes generadorReportes;

    public Administrador(String idUsuario, String username, String hashPassword, Rol rol, int nivelAcceso, GeneradorReportes generadorReportes) {
        super(idUsuario, username, hashPassword, rol);
        this.nivelAcceso = nivelAcceso;
        this.generadorReportes = generadorReportes;
    }

    public void crearUsuario(Usuario u) { }

    public Report generarReporteEventos() {
        if (generadorReportes == null) return null;
        return generadorReportes.generarReporteEventos(null);
    }

    public int getNivelAcceso() { return nivelAcceso; }
    public void setNivelAcceso(int nivelAcceso) { this.nivelAcceso = nivelAcceso; }
}
