package utp.ac.pa.sistema.domain;

public class Usuario {
    private String idUsuario;
    private String username;
    private String hashPassword;
    private Rol rol;

    public Usuario(String idUsuario, String username, String hashPassword, Rol rol) {
        this.idUsuario = idUsuario;
        this.username = username;
        this.hashPassword = hashPassword;
        this.rol = rol;
    }

    public boolean autenticar(String password) {
        if (password == null) return false;
        return password.equals(hashPassword);
    }

    public String getIdUsuario() { return idUsuario; }
    public String getUsername() { return username; }
    public String getHashPassword() { return hashPassword; }
    public Rol getRol() { return rol; }
    public void setHashPassword(String hashPassword) { this.hashPassword = hashPassword; }
    public void setRol(Rol rol) { this.rol = rol; }

    @Override
    public String toString() { return username + " (" + idUsuario + ")"; }
}
