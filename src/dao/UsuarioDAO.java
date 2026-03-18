package dao;

import conexion.ConexionBD;
import modelo.Usuario;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO {

    private Connection conexion;

    public UsuarioDAO() {
        this.conexion = ConexionBD.conectar();
    }

    // ─── LOGIN ───────────────────────────────────────────────
public Usuario login(String usuario, String contrasena) {
    String sql = "SELECT * FROM usuarios WHERE correo=? " +
                 "AND contrasena=?";
        try {
            PreparedStatement ps = conexion.prepareStatement(sql);
            ps.setString(1, usuario);
            ps.setString(2, contrasena);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
               return new Usuario(
    rs.getInt("id_usuario"),
    rs.getString("nombre"),
    rs.getString("correo"),
    rs.getString("contrasena"),
    rs.getString("rol")
);
            }
        } catch (SQLException e) {
            System.out.println("Error login: " + e.getMessage());
        }
        return null;
    }

    // ─── INSERTAR ────────────────────────────────────────────
    public boolean insertar(Usuario usuario) {
String sql = "INSERT INTO usuarios (nombre, correo, " +
             "contrasena, rol) VALUES (?, ?, ?, ?)";
        try {
            PreparedStatement ps = conexion.prepareStatement(sql);
            ps.setString(1, usuario.getNombre());
            ps.setString(2, usuario.getUsuario());
            ps.setString(3, usuario.getContrasena());
            ps.setString(4, usuario.getRol());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error insertar: " + e.getMessage());
            return false;
        }
    }

    // ─── CONSULTAR TODOS ─────────────────────────────────────
    public List<Usuario> consultarTodos() {
        List<Usuario> lista = new ArrayList<>();
        String sql = "SELECT * FROM usuarios";
        try {
            Statement st = conexion.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
lista.add(new Usuario(
    rs.getInt("id_usuario"),
    rs.getString("nombre"),
    rs.getString("correo"),
    rs.getString("contrasena"),
    rs.getString("rol")
));
            }
        } catch (SQLException e) {
            System.out.println("Error consultar: " + e.getMessage());
        }
        return lista;
    }

    // ─── ACTUALIZAR ──────────────────────────────────────────
    public boolean actualizar(Usuario usuario) {
        String sql = "UPDATE usuarios SET nombre=?, usuario=?, " +
                     "contrasena=?, rol=? " +
                     "WHERE id_usuario=?";
        try {
            PreparedStatement ps = conexion.prepareStatement(sql);
            ps.setString(1, usuario.getNombre());
            ps.setString(2, usuario.getUsuario());
            ps.setString(3, usuario.getContrasena());
            ps.setString(4, usuario.getRol());
            ps.setInt(5,    usuario.getIdUsuario());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error actualizar: " + e.getMessage());
            return false;
        }
    }

    // ─── ELIMINAR ────────────────────────────────────────────
    public boolean eliminar(int idUsuario) {
        String sql = "DELETE FROM usuarios WHERE id_usuario=?";
        try {
            PreparedStatement ps = conexion.prepareStatement(sql);
            ps.setInt(1, idUsuario);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error eliminar: " + e.getMessage());
            return false;
        }
    }
}