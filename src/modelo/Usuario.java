package modelo;

public class Usuario {
    
    private int idUsuario;
    private String nombre;
    private String usuario;
    private String contrasena;
    private String rol;

    // Constructor vacío
    public Usuario() {}

    // Constructor completo
    public Usuario(int idUsuario, String nombre, String usuario,
                   String contrasena, String rol) {
        this.idUsuario  = idUsuario;
        this.nombre     = nombre;
        this.usuario    = usuario;
        this.contrasena = contrasena;
        this.rol        = rol;
    }

    // Métodos del diagrama de clases
    public boolean iniciarSesion(String usuarioIngresado, 
                                  String claveIngresada) {
        return this.usuario.equals(usuarioIngresado)
            && this.contrasena.equals(claveIngresada);
    }

    public void cerrarSesion() {
        System.out.println("Sesión cerrada: " + this.usuario);
    }

    // Getters y Setters
    public int getIdUsuario() { return idUsuario; }
    public void setIdUsuario(int idUsuario) { 
        this.idUsuario = idUsuario; 
    }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { 
        this.nombre = nombre; 
    }

    public String getUsuario() { return usuario; }
    public void setUsuario(String usuario) { 
        this.usuario = usuario; 
    }

    public String getContrasena() { return contrasena; }
    public void setContrasena(String contrasena) { 
        this.contrasena = contrasena; 
    }

    public String getRol() { return rol; }
    public void setRol(String rol) { 
        this.rol = rol; 
    }

    @Override
    public String toString() {
        return nombre + " (" + rol + ")";
    }
}