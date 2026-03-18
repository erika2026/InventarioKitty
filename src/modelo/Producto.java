package modelo;

public class Producto {
    
    private int idProducto;
    private String nombre;
    private String descripcion;
    private double precioCompra;
    private double precioVenta;
    private int stock;
    private String categoria;

    // Constructor vacío
    public Producto() {}

    // Constructor completo
    public Producto(int idProducto, String nombre, String descripcion,
                    double precioCompra, double precioVenta, 
                    int stock, String categoria) {
        this.idProducto  = idProducto;
        this.nombre      = nombre;
        this.descripcion = descripcion;
        this.precioCompra = precioCompra;
        this.precioVenta  = precioVenta;
        this.stock       = stock;
        this.categoria   = categoria;
    }

    // Métodos del diagrama de clases
    public void aumentarStock(int cantidad) {
        if (cantidad > 0) {
            this.stock += cantidad;
        }
    }

    public boolean reducirStock(int cantidad) {
        if (cantidad > 0 && this.stock >= cantidad) {
            this.stock -= cantidad;
            return true;
        }
        return false;
    }

    // Getters y Setters
    public int getIdProducto() { return idProducto; }
    public void setIdProducto(int idProducto) { 
        this.idProducto = idProducto; 
    }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { 
        this.nombre = nombre; 
    }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { 
        this.descripcion = descripcion; 
    }

    public double getPrecioCompra() { return precioCompra; }
    public void setPrecioCompra(double precioCompra) { 
        this.precioCompra = precioCompra; 
    }

    public double getPrecioVenta() { return precioVenta; }
    public void setPrecioVenta(double precioVenta) { 
        this.precioVenta = precioVenta; 
    }

    public int getStock() { return stock; }
    public void setStock(int stock) { 
        this.stock = stock; 
    }

    public String getCategoria() { return categoria; }
    public void setCategoria(String categoria) { 
        this.categoria = categoria; 
    }
}