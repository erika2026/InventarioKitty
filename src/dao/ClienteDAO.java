package dao;

import conexion.ConexionBD;
import modelo.Cliente;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClienteDAO {

    private Connection conexion;

    public ClienteDAO() {
        this.conexion = ConexionBD.conectar();
    }

    // ─── INSERTAR ────────────────────────────────────────────
    public boolean insertar(Cliente cliente) {
        String sql = "INSERT INTO clientes (nombre, telefono, " +
                     "direccion, correo) VALUES (?, ?, ?, ?)";
        try {
            PreparedStatement ps = conexion.prepareStatement(sql);
            ps.setString(1, cliente.getNombre());
            ps.setString(2, cliente.getTelefono());
            ps.setString(3, cliente.getDireccion());
            ps.setString(4, cliente.getCorreo());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error insertar: " + e.getMessage());
            return false;
        }
    }

    // ─── CONSULTAR TODOS ─────────────────────────────────────
    public List<Cliente> consultarTodos() {
        List<Cliente> lista = new ArrayList<>();
        String sql = "SELECT * FROM clientes";
        try {
            Statement st = conexion.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                lista.add(new Cliente(
                    rs.getInt("id_cliente"),
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
    public boolean actualizar(Cliente cliente) {
        String sql = "UPDATE clientes SET nombre=?, telefono=?, " +
                     "direccion=?, correo=? " +
                     "WHERE id_cliente=?";
        try {
            PreparedStatement ps = conexion.prepareStatement(sql);
            ps.setString(1, cliente.getNombre());
            ps.setString(2, cliente.getTelefono());
            ps.setString(3, cliente.getDireccion());
            ps.setString(4, cliente.getCorreo());
            ps.setInt(5,    cliente.getIdCliente());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error actualizar: " + e.getMessage());
            return false;
        }
    }

    // ─── ELIMINAR ────────────────────────────────────────────
    public boolean eliminar(int idCliente) {
        String sql = "DELETE FROM clientes WHERE id_cliente=?";
        try {
            PreparedStatement ps = conexion.prepareStatement(sql);
            ps.setInt(1, idCliente);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error eliminar: " + e.getMessage());
            return false;
        }
    }
}