package conexion;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConexionBD {

    public static Connection conectar() {

        Connection conexion = null;

        try {

            String url = "jdbc:mysql://localhost:3306/cosmeticos_kitty";
            String usuario = "root";
            String password = "Kitty2026";

            conexion = DriverManager.getConnection(url, usuario, password);

            System.out.println("Conexión exitosa");

        } catch (Exception e) {

            System.out.println("Error: " + e.getMessage());

        }

        return conexion;
    }
}