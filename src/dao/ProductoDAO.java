package dao;

import conexion.ConexionBD;
import modelo.Producto;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductoDAO {

    private Connection conexion;

    public ProductoDAO() {
        this.conexion = ConexionBD.conectar();
    }

    // ─── INSERTAR ────────────────────────────────────────────
    public boolean insertar(Producto producto) {
        String sql = "INSERT INTO productos (nombre, descripcion, " +
                     "precio, stock) VALUES (?, ?, ?, ?)";
        try {
            PreparedStatement ps = conexion.prepareStatement(sql);
            ps.setString(1, producto.getNombre());
            ps.setString(2, producto.getDescripcion());
            ps.setDouble(3, producto.getPrecioVenta());
            ps.setInt(4,    producto.getStock());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error insertar: " + e.getMessage());
            return false;
        }
    }

    // ─── CONSULTAR TODOS ─────────────────────────────────────
    public List<Producto> consultarTodos() {
        List<Producto> lista = new ArrayList<>();
        String sql = "SELECT * FROM productos WHERE estado=1";
        try {
            Statement st = conexion.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                Producto p = new Producto(
                    rs.getInt("id_producto"),
                    rs.getString("nombre"),
                    rs.getString("descripcion"),
                    0,
                    rs.getDouble("precio"),
                    rs.getInt("stock"),
                    ""
                );
                lista.add(p);
            }
        } catch (SQLException e) {
            System.out.println("Error consultar: " + e.getMessage());
        }
        return lista;
    }

    // ─── ACTUALIZAR ──────────────────────────────────────────
    public boolean actualizar(Producto producto) {
        System.out.println("ID a actualizar: " + producto.getIdProducto());
        String sql = "UPDATE productos SET nombre=?, " +
                     "descripcion=?, precio=?, stock=? " +
                     "WHERE id_producto=?";
        try {
            PreparedStatement ps = conexion.prepareStatement(sql);
            ps.setString(1, producto.getNombre());
            ps.setString(2, producto.getDescripcion());
            ps.setDouble(3, producto.getPrecioVenta());
            ps.setInt(4,    producto.getStock());
            ps.setInt(5,    producto.getIdProducto());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error actualizar: " + e.getMessage());
            return false;
        }
    }

    // ─── ELIMINAR ────────────────────────────────────────────
    public boolean eliminar(int idProducto) {
        String sql = "UPDATE productos SET estado=0 " +
                     "WHERE id_producto=?";
        try {
            PreparedStatement ps = conexion.prepareStatement(sql);
            ps.setInt(1, idProducto);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error eliminar: " + e.getMessage());
            return false;
        }
    }

    // ─── BUSCAR POR NOMBRE ───────────────────────────────────
    public List<Producto> buscarPorNombre(String nombre) {
        List<Producto> lista = new ArrayList<>();
        String sql = "SELECT * FROM productos " +
                     "WHERE nombre LIKE ? AND estado=1";
        try {
            PreparedStatement ps = conexion.prepareStatement(sql);
            ps.setString(1, "%" + nombre + "%");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Producto p = new Producto(
                    rs.getInt("id_producto"),
                    rs.getString("nombre"),
                    rs.getString("descripcion"),
                    0,
                    rs.getDouble("precio"),
                    rs.getInt("stock"),
                    ""
                );
                lista.add(p);
            }
        } catch (SQLException e) {
            System.out.println("Error buscar: " + e.getMessage());
        }
        return lista;
    }
}