package modelo;

public class Proveedor {
    
    private int idProveedor;
    private String nombre;
    private String telefono;
    private String direccion;
    private String correo;

    // Constructor vacío
    public Proveedor() {}

    // Constructor completo
    public Proveedor(int idProveedor, String nombre, String telefono,
                     String direccion, String correo) {
        this.idProveedor = idProveedor;
        this.nombre      = nombre;
        this.telefono    = telefono;
        this.direccion   = direccion;
        this.correo      = correo;
    }

    // Método del diagrama de clases
    public void registrarProveedor() {
        System.out.println("Proveedor registrado: " + this.nombre);
    }

    // Getters y Setters
    public int getIdProveedor() { return idProveedor; }
    public void setIdProveedor(int idProveedor) { 
        this.idProveedor = idProveedor; 
    }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { 
        this.nombre = nombre; 
    }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { 
        this.telefono = telefono; 
    }

    public String getDireccion() { return direccion; }
    public void setDireccion(String direccion) { 
        this.direccion = direccion; 
    }

    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { 
        this.correo = correo; 
    }

    @Override
    public String toString() { 
        return nombre; 
    }
}