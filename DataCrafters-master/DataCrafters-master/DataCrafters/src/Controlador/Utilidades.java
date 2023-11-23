package Controlador;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Utilidades {

    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/onlinestore";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "1983";

    // Método para obtener una conexión a la base de datos
    public static Connection obtenerConexion() throws SQLException {
        return DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
    }

    // Método para cerrar una conexión
    public static void cerrarConexion(Connection conexion) throws SQLException {
        if (conexion != null && !conexion.isClosed()) {
            conexion.close();
        }
    }
}

