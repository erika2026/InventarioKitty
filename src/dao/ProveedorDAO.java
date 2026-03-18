package dao;

import conexion.ConexionBD;
import modelo.Proveedor;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProveedorDAO {

    private Connection conexion;

    public ProveedorDAO() {
        this.conexion = ConexionBD.conectar();
    }

    // ─── INSERTAR ────────────────────────────────────────────
    public boolean insertar(Proveedor proveedor) {
        String sql = "INSERT INTO proveedores (nombre, telefono, " +
                     "direccion, correo) VALUES (?, ?, ?, ?)";
        try {
            PreparedStatement ps = conexion.prepareStatement(sql);
            ps.setString(1, proveedor.getNombre());
            ps.setString(2, proveedor.getTelefono());
            ps.setString(3, proveedor.getDireccion());
            ps.setString(4, proveedor.getCorreo());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error insertar: " + e.getMessage());
            return false;
        }
    }

    // ─── CONSULTAR TODOS ─────────────────────────────────────
    public List<Proveedor> consultarTodos() {
        List<Proveedor> lista = new ArrayList<>();
        String sql = "SELECT * FROM proveedores";
        try {
            Statement st = conexion.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                lista.add(new Proveedor(
                    rs.getInt("id_proveedor"),
                    rs.getString("nombre"),
                    rs.getString("telefono"),
                    rs.getString("direccion"),
                    rs.getString("correo")
                ));
            }
        } catch (SQLException e) {
            System.out.println("Error consultar: " + e.getMessage());
        }
        return lista;
    }

    // ─── ACTUALIZAR ──────────────────────────────────────────
    public boolean actualizar(Proveedor proveedor) {
        String sql = "UPDATE proveedores SET nombre=?, telefono=?, " +
                     "direccion=?, correo=? " +
                     "WHERE id_proveedor=?";
        try {
            PreparedStatement ps = conexion.prepareStatement(sql);
            ps.setString(1, proveedor.getNombre());
            ps.setString(2, proveedor.getTelefono());
            ps.setString(3, proveedor.getDireccion());
            ps.setString(4, proveedor.getCorreo());
            ps.setInt(5,    proveedor.getIdProveedor());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error actualizar: " + e.getMessage());
            return false;
        }
    }

    // ─── ELIMINAR ────────────────────────────────────────────
    public boolean eliminar(int idProveedor) {
        String sql = "DELETE FROM proveedores WHERE id_proveedor=?";
        try {
            PreparedStatement ps = conexion.prepareStatement(sql);
            ps.setInt(1, idProveedor);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error eliminar: " + e.getMessage());
            return false;
        }
    }
}