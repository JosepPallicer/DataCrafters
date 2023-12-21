
package Controlador;

import Modelo.*;
import java.sql.*;

/**
 * El controlador es el encargado de gestionar la interacción entre la BBDD y contiene
 * la lógica de negocio que permite la manipulación de los clientes, articulos y pedidos.
 * Por otro lado, se encarga de la conexión y desconexión con la BBDD.
 */

public class Controlador implements AutoCloseable{

    private Connection connection;

    private final SessionFactory sessionFactory;

    /**
     * Constructor del controlador con el que establecemos la conexión con la BBDD
     */
    public Controlador(SessionFactory sessionFactory) {
        try {
            // Establezco la conexión al iniciar el controlador
            connection = Utilidades.obtenerConexion();
            this.sessionFactory = sessionFactory;
            System.out.println("Conectado correctamente con la base de datos");
        } catch (SQLException e) {
            System.out.println("Error al conectar con la base de datos");
            e.printStackTrace();
        }
    }

    /**
     * Método que permite cerrar la conexión con la BBDD.
     */
    public void close() {
        try {
            Utilidades.cerrarConexion(connection);
            System.out.println("La base de datos se ha cerrado adecuadamente");
        } catch (SQLException e) {
            System.err.println("Error al cerrar la base de datos: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Método para agregar un pedido a la base de datos.
     *
     * @param nombre  Nombre del artículo
     * @param descripcion Descripción del articulo
     * @param precio  Precio del artículo
     * @param tiempoPreparacion  Tiempo de preparacion para el artículo
     * @param gastosEnvio  Gastos de envio del artículo
     */

   public void agregarArticulo(String nombre, String descripcion, double precio, int tiempoPreparacion, double gastosEnvio) {
        String sql = "{CALL agregarArticulo(?, ?, ?, ?, ?)}";
        try (CallableStatement statement = connection.prepareCall(sql)) {
            statement.setString(1, nombre);
            statement.setString(2, descripcion);
            statement.setDouble(3, precio);
            statement.setInt(4, tiempoPreparacion);
            statement.setDouble(5, gastosEnvio);

            statement.execute();
            System.out.println("Articulo creado satisfactoriamente");
        } catch (SQLException e) {
            System.err.println("Error al agregar el artículo: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Metodo qie permite eliminar un artículo por el código
     * @param idCodigo  id del código del artículo que vamos a eliminar
     */

    public void eliminarArticulo(int idCodigo) {
        String sql = "{CALL eliminarArticulo(?)}";

        try (CallableStatement statement = connection.prepareCall(sql)) {
            statement.setInt(1, idCodigo);
            statement.execute();
            System.out.println("Articulo eliminado satisfactoriamente");
        } catch (SQLException e) {
            System.err.println("Error al eliminar el artículo: " + e.getMessage());
            e.printStackTrace();
        }
    }



    /**
     * Método
     * @return true cuando la operación ha sido correcta, false en el caso contrario
     */


    public boolean mostrarArticulos() {
        try (Session session = sessionFactory.openSession()) {

            String hql = "FROM articulos";
            List<Articulo> articulos = session.createQuery(hql, Articulo.class).list();

            for (Articulo articulo : articulos) {

                System.out.println("Articulo: " + resultSet.getString("nombre"));
                System.out.println("Descripción: " + resultSet.getString("descripcion"));
                System.out.println("Precio: " + resultSet.getDouble("precio"));
                System.out.println("Tiempo de preparación: " + resultSet.getInt("tiempoPreparacion"));
                System.out.println("Gastos de envío: " + resultSet.getDouble("gastosEnvio"));
                System.out.println("------------------------");
            }
        } catch (Exception e) {
            System.err.println("Error al mostrar los artículos: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Método que busca un articulo por su nombre
     * @param nombre Nombre del artículo
     * @return Nos devuelve una instancia de la clase Srticulo  cuando lo encuentra y null en caso contrario
     */
    public Articulo buscarArticulo(String nombre) {
        try {
            String sql = "CALL buscarArticulo(?)";
            try (CallableStatement stmt = connection.prepareCall(sql)) {
                stmt.setString(1, nombre);
                stmt.execute();

                try (ResultSet rs = stmt.getResultSet()) {
                    if (rs.next()) {

                        System.out.println("Artículo: " + rs.getString("nombre"));
                        System.out.println("Descripción: " + rs.getString("descripcion"));
                        System.out.println("Precio: " + rs.getDouble("precio"));
                        System.out.println("Tiempo de Preparación: " + rs.getInt("tiempoPreparacion"));
                        System.out.println("Gastos de Envío: " + rs.getDouble("gastosEnvio"));
                    } else {
                        System.out.println("Artículo no encontrado, asegúrate de poner correctamente el nombre.");
                    }
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * Método que me permite agregar un cliente a la BBDD
     * @param p_nif NIF del clinte
     * @param p_email Email del cliente
     * @param p_nombre Nombre del cliente
     * @param p_apellido1 Primer apellido del cliente
     * @param p_apellido2 Segundo apellido del cliente
     * @param p_domicilio Domicilio del cliente
     * @param p_tipo Tipo de cliente, premiun o standar
     * @return Devuelve un mensaje que me indica si se ha introducido correctamente el cliente o no.
     */
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

                // Ejecutao el stored procedure
                stmt.execute();

                // Obtengo el resultado del mensaje
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

    /**
     * Método que muestra a los clientes de la base de datos
     */
    public void mostrarClientes() {
        String sql = "{CALL mostrarClientes()}";

        try (CallableStatement statement = connection.prepareCall(sql)) {
            // Ejecutar el procedimiento almacenado
            statement.execute();

            // Obtener el resultado de la consulta
            try (ResultSet rs = statement.getResultSet()) {
                System.out.println("Listado de Clientes:");
                while (rs.next()) {
                    System.out.println("NIF: " + rs.getString("nif"));
                    System.out.println("Email: " + rs.getString("email"));
                    System.out.println("Nombre: " + rs.getString("nombre"));
                    System.out.println("Primer Apellido: " + rs.getString("apellido1"));
                    System.out.println("Segundo Apellido: " + rs.getString("apellido2"));
                    System.out.println("Domicilio: " + rs.getString("domicilio"));
                    System.out.println("Tipo: " + rs.getString("tipo"));
                    System.out.println("-----------------------------");
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al mostrar clientes: " + e.getMessage());
            e.printStackTrace();
        }
    }


    /**
     * Método para eliminar de la BBDD  al cliente a través de su nif
     * @param nif NIF del cliente
     * @return Nos devuelve un mensaje con el que podemos saber si se ha eliminado o no al cliente
     */
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

    /**
     * Método que nos permite buscar un cliente con su NIF en la BBDD
     * @param nif NIF del cliente
     */

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

    /**
     * Método con el que se me muestra los pedidos existentes en la BBDD
     */

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

    /**
     * Método con el que buscar un pedido en concreto, a partir del número pedido
     * @param numeroPedido Número del pedido que buscamos
     */

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

    /**
     * Método para eliminar un pedido de la BBDD
     * @param numeroPedido Número de pedido que buscamos
     */

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

    /**
     * Método que muetra los pedidos enviados de la BBDD
     */
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

    /**
     * Método que muetra los pedidos pendientes de la BBDD
     */

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

    /**
     * Método para agregar un pedido a la BBDD
     * @param nifCliente Nif del cliente
     * @param nombreArticulo Nombre del artículo
     * @param cantidad Cantidad del producto (artículo)
     * @param enviado Estado en el que se encuentra el pedido
     */
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