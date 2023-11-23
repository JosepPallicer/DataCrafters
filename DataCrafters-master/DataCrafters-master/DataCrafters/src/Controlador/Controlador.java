
package Controlador;

import Modelo.*;
import java.sql.*;


public class Controlador {


    private static final String URL = "jdbc:mysql://localhost:3306/onlinestore";
    private static final String USUARIO = "root";
    private static final String CONTRASENA = "1983";
    private Connection connection;

    public Controlador() {
        try {
            // Establecer la conexión al iniciar el controlador
            connection = DriverManager.getConnection(URL, USUARIO, CONTRASENA);
            System.out.println(("Conectado correctamente con la base de datos"));
        } catch (SQLException e) {
            System.out.println("Error al conectar con la base de datos");
            e.printStackTrace();

        }
    }

    // Método para cerrar la conexión al finalizar la aplicación
    public void cerrarConexion() throws SQLException {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("La Base de datos se ha cerrado adecuadamente");
            }
        } catch (SQLException e) {
            System.err.println("Error al cerrar la base de datos: " + e.getMessage());
            throw e; // Arroja la excepción para que el controlador superior pueda manejarla
        }
    }

    // Método para agregar un artículo
    public void agregarArticulo(String nombre, String descripcion, double precio, int tiempoPreparacion, double gastosEnvio) {
        try {
            CallableStatement statement = connection.prepareCall("{CALL agregarArticulo(?, ?, ?, ?, ?)}");
            statement.setString(1, nombre);
            statement.setString(2, descripcion);
            statement.setDouble(3, precio);
            statement.setInt(4, tiempoPreparacion);
            statement.setDouble(5, gastosEnvio);

            statement.execute();
            statement.close();
        } catch (SQLException e) {
            System.err.println("Error al agregar el artículo: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Método para eliminar un artículo
    public void eliminarArticulo(int idCodigo) {
        try {
            CallableStatement statement = connection.prepareCall("{CALL eliminarArticulo(?)}");
            statement.setInt(1, idCodigo);

            statement.execute();
            statement.close();
        } catch (SQLException e) {
            System.err.println("Error al eliminar el artículo: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Método para mostrar todos los artículos
    public void mostrarArticulos() {
        try {
            CallableStatement statement = connection.prepareCall("{CALL mostrarArticulos()}");
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                System.out.println("Código: " + resultSet.getString("nombre"));
                System.out.println("Descripción: " + resultSet.getString("descripcion"));
                System.out.println("Precio: " + resultSet.getDouble("precio"));
                System.out.println("Tiempo de preparación: " + resultSet.getInt("tiempoPreparacion"));
                System.out.println("Gastos de envío: " + resultSet.getDouble("gastosEnvio"));
                System.out.println("------------------------");
            }

            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            System.err.println("Error al mostrar los artículos: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public Articulo buscarArticulo(int idCodigo) {
        try {
            // Preparar la llamada al stored procedure
            String sql = "CALL buscarArticulo(?)";
            try (CallableStatement stmt = connection.prepareCall(sql)) {
                // Establecer los parámetros del stored procedure
                stmt.setInt(1, idCodigo);

                // Ejecutar el stored procedure
                stmt.execute();

                // Obtener el resultado de la búsqueda
                try (ResultSet rs = stmt.getResultSet()) {
                    if (rs.next()) {
                        // Mostrar los detalles del artículo encontrado
                        System.out.println("Código: " + rs.getString("id_codigo"));
                        System.out.println("Descripción: " + rs.getString("descripcion"));
                        System.out.println("Precio: " + rs.getDouble("precio"));
                        System.out.println("Tiempo de Preparación: " + rs.getInt("tiempoPreparacion"));
                        System.out.println("Gastos de Envío: " + rs.getDouble("gastosEnvio"));
                    } else {
                        System.out.println("No se encontró ningún artículo con ese código.");
                    }


                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String agregarCliente(String p_nif, String p_email, String p_nombre, String p_apellido1, String p_apellido2, String p_domicilio, String p_tipo) {
        String mensaje = null;

        try {
            // Preparar la llamada al stored procedure
            String sql = "CALL agregarCliente(?, ?, ?, ?, ?, ?, ?)";
            try (CallableStatement stmt = connection.prepareCall(sql)) {
                // Establecer los parámetros del stored procedure
                stmt.setString(1, p_nif);
                stmt.setString(2, p_email);
                stmt.setString(3, p_nombre);
                stmt.setString(4, p_apellido1);
                stmt.setString(5, p_apellido2);
                stmt.setString(6, p_domicilio);
                stmt.setString(7, p_tipo);

                // Ejecutar el stored procedure
                stmt.execute();

                // Obtener el resultado del mensaje
                try (ResultSet rs = stmt.getResultSet()) {
                    if (rs.next()) {
                        mensaje = rs.getString("mensaje");
                        System.out.println(mensaje);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return mensaje;
    }

    public String eliminarCliente(String nif) {
        String mensaje = null;

        try {
            String sql = "CALL eliminarCliente(?, ?)";
            try (CallableStatement stmt = connection.prepareCall(sql)) {
                stmt.setString(1, nif);
                stmt.registerOutParameter(2, Types.VARCHAR);
                stmt.execute();
                mensaje = stmt.getString(2);
                System.out.println("" + mensaje);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return mensaje;
    }


    // Método para obtener el mensaje del procedimiento almacenado
    private String obtenerMensaje(CallableStatement stmt) throws SQLException {
        String mensaje = null;

        // Obtener el resultado de la ejecución del procedimiento almacenado
        try (ResultSet rs = stmt.getResultSet()) {
            if (rs.next()) {
                mensaje = rs.getString("MESSAGE_TEXT");
            }
        }

        return mensaje;
    }


    public void buscarCliente(String nif) {
        try {
            String sql = "CALL buscarCliente(?)";
            try (CallableStatement stmt = connection.prepareCall(sql)) {
                stmt.setString(1, nif);
                stmt.execute();

                try (ResultSet rs = stmt.getResultSet()) {
                    if (rs.next()) {
                        // Mostrar detalles del cliente encontrado
                        System.out.println("NIF: " + rs.getString("nif"));
                        System.out.println("Email: " + rs.getString("email"));
                        System.out.println("Nombre: " + rs.getString("nombre"));
                        System.out.println("Apellido1: " + rs.getString("apellido1"));
                        System.out.println("Apellido2: " + rs.getString("apellido2"));
                        System.out.println("Domicilio: " + rs.getString("domicilio"));
                        System.out.println("Tipo: " + rs.getString("tipo"));
                    } else {
                        System.out.println("No se encontró ningún cliente con ese NIF.");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void mostrarPedidos() {
        try {
            CallableStatement statement = connection.prepareCall("{CALL mostrarPedidos()}");
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                // Asegúrate de usar el nombre correcto de la columna
                System.out.println("Número de Pedido: " + resultSet.getInt("id_numeroPedido"));
                System.out.println("Fecha y Hora: " + resultSet.getTimestamp("fechaHora"));
                System.out.println("Enviado: " + resultSet.getBoolean("enviado"));
                System.out.println("Pagado: " + resultSet.getBoolean("pagado"));
                System.out.println("------------------------");
            }

            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            System.err.println("Error al mostrar los pedidos: " + e.getMessage());
            e.printStackTrace();
        }
    }


    public void buscarPedido(int numeroPedido) {
        try {
            String sql = "CALL buscarPedido(?)";
            try (CallableStatement stmt = connection.prepareCall(sql)) {
                stmt.setInt(1, numeroPedido);
                stmt.execute();

                try (ResultSet rs = stmt.getResultSet()) {
                    if (rs.next()) {
                        System.out.println("Número de Pedido: " + rs.getInt("numeroPedido"));
                        System.out.println("Cliente: " + rs.getString("cliente"));  // Ajustado el nombre de la columna
                        System.out.println("Fecha y Hora: " + rs.getTimestamp("fechaHora"));
                        System.out.println("Enviado: " + rs.getBoolean("enviado"));
                    } else {
                        System.out.println("No se encontró ningún pedido con ese número.");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void eliminarPedido(int numeroPedido) {
        try {
            CallableStatement statement = connection.prepareCall("{CALL eliminarPedido(?)}");
            statement.setInt(1, numeroPedido);

            boolean resultado = statement.execute();

            if (!resultado) {
                System.out.println("Pedido eliminado satisfactoriamente.");
            } else {
                System.out.println("Error al eliminar el pedido.");
            }

            statement.close();
        } catch (SQLException e) {
            System.err.println("Error al eliminar el pedido: " + e.getMessage());
            e.printStackTrace();
        }
    }
    public void mostrarPedidosEnviados() {
        try {
            CallableStatement statement = connection.prepareCall("{CALL mostrarPedidosEnviados()}");
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                System.out.println("Número de Pedido: " + resultSet.getInt("id_numeroPedido"));
                System.out.println("Fecha y Hora: " + resultSet.getTimestamp("fechaHora"));
                System.out.println("Enviado: " + resultSet.getBoolean("enviado"));
                System.out.println("Pagado: " + resultSet.getBoolean("pagado"));
                System.out.println("------------------------");
            }

            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            System.err.println("Error al mostrar los pedidos enviados: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void mostrarPedidosPendientes() {
        try {
            CallableStatement statement = connection.prepareCall("{CALL mostrarPedidosPendientes()}");
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                System.out.println("Número de Pedido: " + resultSet.getInt("id_numeroPedido"));
                System.out.println("Fecha y Hora: " + resultSet.getTimestamp("fechaHora"));
                System.out.println("Enviado: " + resultSet.getBoolean("enviado"));
                System.out.println("Pagado: " + resultSet.getBoolean("pagado"));
                System.out.println("------------------------");
            }

            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            System.err.println("Error al mostrar pedidos pendientes: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void agregarPedido (String nifCliente, String nombreArticulo, int cantidad, boolean enviado){
        try {
            CallableStatement statement = connection.prepareCall("{CALL crearPedido(?, ?, ?, ?, NOW())}");
            statement.setString(1, nifCliente);
            statement.setString(2, nombreArticulo);
            statement.setInt(3, cantidad);
            statement.setBoolean(4, enviado);


           boolean resultado = statement.execute();
           if(!resultado){
               System.out.println("Pedido creado satisfactoriamente.");

           }else{
               System.out.println("Error al agregar el pedido.");
           }
            statement.close();

        }catch (SQLException e) {
            System.err.println("Error al agregar el pedido: " + e.getMessage());

            // Obtener el código de estado SQL
            int sqlErrorCode = e.getErrorCode();

            // Verificar el código de estado SQL para determinar el tipo de error
            switch (sqlErrorCode) {

                case 45000:
                    System.err.println("Error: El cliente no existe");
                    break;
                case 47000:
                    System.err.println("Error: El artículo no existe");
                    break;
                default:
                    System.err.println("Error desconocido al agregar el pedido.");
            }

            e.printStackTrace();
        }


    }




}