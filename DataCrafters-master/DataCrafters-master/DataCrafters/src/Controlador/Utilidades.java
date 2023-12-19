package Controlador;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class Utilidades {

    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/onlinestore";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "Ecoquimic1445";

    /**
     * Método con el que nos conectamos a la BBDD
     * @return Una instancia de la interfaz Connection que es la que representa la conexión de la BBDD.
     * @throws SQLException Si encuentra algún error cuando intenta conectar con la BBDD.
     */
    public static Connection obtenerConexion() throws SQLException {
        return DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
    }

    /**
     * Método con el que cerrar la conexión con la BBDD.
     * @param conexion Es la conexión que vamos a cerrar.
     * @throws SQLException Si encuentra algún error cuando intenta conectar con la BBDD.
     */

    public static void cerrarConexion(Connection conexion) throws SQLException {
        if (conexion != null && !conexion.isClosed()) {
            conexion.close();
        }
    }
}

