package Vista;

import Controlador.Controlador;
import Modelo.*;

import java.sql.Timestamp;
import java.time.format.DateTimeParseException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;
public class GestionOS {
    private Controlador controlador;
    Scanner teclado = new Scanner(System.in);

    public GestionOS() {
        controlador = new Controlador();
    }
    public void inicio() {
        boolean salir = false;
        char opcio;
        do {
            System.out.println("1. Gestión Articulo");
            System.out.println("2. Gestión Clientes");
            System.out.println("3. Gestión Pedido");
            System.out.println("0. Salir");
            opcio = pedirOpcion();
            switch (opcio) {

                case '1':
                   gestionArticulos();
                    break;

                case '2':
                   gestionClientes();
                    break;

                case '3':
                   gestionPedidos();
                    break;

                case '0':
                    salir = true;
            }
        } while (!salir);
    }
    char pedirOpcion() {
        String resp;
        System.out.println("Elige una opción (1,2,3 o 0):");
                resp = teclado.nextLine();
        if (resp.isEmpty()) {
            resp = " ";
        }
        return resp.charAt(0);
    }

    public void gestionArticulos() {
        boolean salir = false;
        char opcio;
        do {
            System.out.println("Que desea hacer?");
            System.out.println("1. Añadir un artículo");
            System.out.println("2. Mostrar los artículos");
            System.out.println("3. Buscar artículo");
            System.out.println("0. Atrás");
            opcio = pedirOpcion();
            switch (opcio) {
                case '1':
                    System.out.println("Introduce el nombre del artículo");
                    String nombre = teclado.nextLine();
                    System.out.println("Introduce la descripción del artículo");
                    String descripcion = teclado.nextLine();
                    System.out.println("Introduce el precio del artículo");
                    double precio = Double.parseDouble(teclado.nextLine());
                    System.out.println("Introduce las horas de preparación del artículo");
                    int tiempoPreparacion = Integer.parseInt(teclado.nextLine());
                    System.out.println("Introduce los gastos de envío del artículo");
                    double gastosEnvio = Double.parseDouble(teclado.nextLine());
                    controlador.agregarArticulo(nombre, descripcion, precio, tiempoPreparacion, gastosEnvio);
                    break;
                case '2':
                    controlador.mostrarArticulos();
                    break;
                case '3':
                    System.out.println("Introduce el ID del artículo que quieres buscar:");
                    int idCodigo = Integer.parseInt(teclado.nextLine());
                    Articulo articuloEncontrado = controlador.buscarArticulo(idCodigo);

                    if (articuloEncontrado == null) {
                        System.out.println("El artículo con ID " + idCodigo + " no existe.");
                    } else {
                        System.out.println(articuloEncontrado);
                    }
                    break;
                case '0':
                    salir = true;
                    break;

            }
        } while (!salir);
    }


        public void gestionClientes() {
            boolean salir = false;
            char opcio;
            do {
                System.out.println("¿Qué desea hacer con clientes?");
                System.out.println("1. Añadir un cliente");
                System.out.println("2. Eliminar un cliente");
                System.out.println("3. Buscar un cliente por NIF");
                System.out.println("0. Atrás");
                opcio = pedirOpcion();
                switch (opcio) {
                    case '1':
                        System.out.println("Introduce el NIF del cliente");
                        String nif = teclado.nextLine();
                        System.out.println("Introduce el email del cliente");
                        String email = teclado.nextLine();
                        System.out.println("Introduce el nombre del cliente");
                        String nombre = teclado.nextLine();
                        System.out.println("Introduce el primer apellido del cliente");
                        String apellido1 = teclado.nextLine();
                        System.out.println("Introduce el segundo apellido del cliente");
                        String apellido2 = teclado.nextLine();
                        System.out.println("Introduce el domicilio del cliente");
                        String domicilio = teclado.nextLine();
                        System.out.println("Introduce el tipo del cliente");
                        String tipo = teclado.nextLine();
                        controlador.agregarCliente(nif, email, nombre, apellido1, apellido2, domicilio, tipo);
                        break;
                    case '2':
                        System.out.println("Introduce el NIF del cliente a eliminar");
                        nif = teclado.nextLine();
                        controlador.eliminarCliente(nif);
                        break;
                    case '3':
                        System.out.println("Introduce el NIF del cliente a buscar");
                        nif = teclado.nextLine();
                        controlador.buscarCliente(nif);
                        break;
                    case '0':
                        salir = true;
                }
            } while (!salir);
        }




private void gestionPedidos() {
    boolean salir = false;
    char opcion;

    do {
        System.out.println("¿Qué desea hacer con pedidos?");
        System.out.println("1. Crear un pedido");
        System.out.println("2. Mostrar todos los pedidos");
        System.out.println("3. Buscar un pedido por número");
        System.out.println("4. Eliminar un pedido");
        System.out.println("5. Mostrar pedidos enviados de un cliente");
        System.out.println("6. Mostrar pedidos pendientes de un cliente");
        System.out.println("0. Atrás");
        opcion = pedirOpcion();

        switch (opcion) {
            case '1':
                crearPedido();
                break;

            case '2':
                controlador.mostrarPedidos();
                break;

            case '3':
                System.out.println("Introduce el número del pedido a buscar: ");
                int numeroPedido = Integer.parseInt(teclado.nextLine());
                controlador.buscarPedido(numeroPedido);
                break;

            case '4':
                System.out.println("Introduce el número del pedido a eliminar: ");
                int numeroPedidoEliminar = Integer.parseInt(teclado.nextLine());
                controlador.eliminarPedido(numeroPedidoEliminar);
                break;

            case '5':
                System.out.println(" Pedidos enviados: ");
                controlador.mostrarPedidosEnviados();
                break;

            case '6':
                System.out.println("Pedidos pendientes:");
                controlador.mostrarPedidosPendientes();
                break;

            case '0':
                salir = true;
        }
    } while (!salir);
}



    public static void main(String[] args) {
        GestionOS gestionOS = new GestionOS();
        gestionOS.inicio();
    }

    private void crearPedido() {
        System.out.println("Introduce el NIF del cliente: ");
        String nifCliente = teclado.nextLine();

        System.out.println("Introduce el nombre del artículo: ");
        String nombreArticulo = teclado.nextLine();

        System.out.println("Introduce la cantidad: ");
        int cantidad = Integer.parseInt(teclado.nextLine());

        System.out.println("¿El pedido está enviado? (true/false): ");
        boolean enviado = Boolean.parseBoolean(teclado.nextLine());


        controlador.agregarPedido(nifCliente, nombreArticulo, cantidad, enviado);
    }

}




   /* public void gestionClientes(){
        boolean salir = false;
        char opcio;
        do {
            System.out.println("Que desea hacer?");
            System.out.println("1. Añadir un cliente");
            System.out.println("2. Mostrar los clientes");
            System.out.println("3. Buscar un cliente");
            System.out.println("0. Atras");
            opcio = pedirOpcion();
            switch (opcio) {
                case '1':
                    System.out.println("Que tipo de cliente se va a registrar? Pulsa S para estándar o P para premium");
                    String tipoCliente = teclado.nextLine();
                    System.out.println("Introduce el nombre del cliente");
                    String nombre = teclado.nextLine();
                    System.out.println("Introduce el domicilio del cliente");
                    String domicilio = teclado.nextLine();
                    System.out.println("Introduce el nif del cliente");
                    String nif = (teclado.nextLine());
                    System.out.println("Introduce el email del cliente");
                    String email = (teclado.nextLine());

                    Cliente cliente;

                    if(tipoCliente.equalsIgnoreCase("S")){
                        cliente = new ClienteEstandar(nombre,domicilio,nif,email);
                    }else if (tipoCliente.equalsIgnoreCase("P")){
                        cliente = new ClientePremium(nombre,domicilio,nif,email);
                    } else {
                        System.out.println("Este tipo de cliente no es válido.");
                        return;
                    }
                    controlador.crearCliente(cliente);
                    break;
                case '2':
                    controlador.mostrarClientes();
                    break;
                case '3':
                    System.out.println("Introduce el nif del cliente que quieres buscar:");
                    nif = teclado.nextLine();
                    Cliente clienteEncontrado = controlador.buscarPorNIF(nif);

                    if (clienteEncontrado != null) {
                        System.out.println(clienteEncontrado);
                    } else {
                        System.out.println("El cliente con el nif " + nif + " no existe.");
                    }
                    break;
                case '0':
                    salir = true;
            }
        } while (!salir);
    }
    public void gestionPedidos(){
        boolean salir = false;
        char opcio;
        do {
            System.out.println("Que desea hacer?");
            System.out.println("1. Añadir un pedido");
            System.out.println("2. Mostrar los pedidos");
            System.out.println("3. Buscar pedidos por numero de pedido");
            System.out.println("0. Atras");
            opcio = pedirOpcion();
            switch (opcio) {
                case '1':
                    System.out.println("Introduce el número del pedido");
                    int numeroPedido = Integer.parseInt(teclado.nextLine());

                    boolean nifValido = false;
                    Cliente clienteEncontrado = null;
                    while (!nifValido){
                    System.out.println("Introduce el nif del cliente que hace el pedido");
                    String nif = teclado.nextLine();
                    clienteEncontrado = controlador.buscarPorNIF(nif);
                    if (clienteEncontrado == null) {
                        System.out.println("El cliente con el nif " + nif + " no existe. Ingresa un nif válido.");
                    }else {
                        nifValido = true;
                    }
                    }

                    boolean codigoValido = false;
                    Articulo articuloEncontrado = null;
                    while (!codigoValido){
                    System.out.println("Introduce el codigo del articulo");
                    String codigo = teclado.nextLine();
                    articuloEncontrado = controlador.buscarPorCodigo(codigo);
                    if (articuloEncontrado == null) {
                        System.out.println("El articulo con el código " + codigo + " no existe. Ingresa otro código.");
                    } else {
                        codigoValido = true;
                    }
                    }

                    System.out.println("Introduce la cantidad");
                    int cantidad = Integer.parseInt(teclado.nextLine());

                    boolean fechaValida = false;
                    LocalDateTime fechaHora = null;
                    while (!fechaValida) {
                        try {
                            System.out.println("Introduce la fecha del pedido (formato: dd-MM-yyyy HH:mm):");
                            String fechaHoraStr = teclado.nextLine();
                            fechaHora = LocalDateTime.parse(fechaHoraStr, DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm"));
                            fechaValida = true;
                        } catch (DateTimeParseException e) {
                            System.out.println("Fecha no válida. Por favor, ingrese la fecha en el formato correcto (dd-MM-yyyy HH:mm).");
                        } catch (Exception e) {
                            System.out.println("Ocurrió un error: " + e.getMessage());
                        }
                    }

                    boolean enviado =false;
                    controlador.crearPedido(numeroPedido, clienteEncontrado, articuloEncontrado, cantidad, fechaHora,enviado);
                    break;

                case '2':
                    controlador.mostrarPedidos();
                    break;

                case '3':
                    Pedido pedidoEncontrado = null;
                    while (pedidoEncontrado == null) {
                        System.out.println("Introduce el número del pedido deseado:");
                        numeroPedido = Integer.parseInt(teclado.nextLine());
                        pedidoEncontrado = controlador.mostrarPorNumero(numeroPedido);

                        if (pedidoEncontrado == null) {
                            System.out.println("El pedido " + numeroPedido + " no existe. Ingresa otro número.");
                        } else {
                            System.out.println(pedidoEncontrado);
                        }
                    }
                    break;

                case '0':
                    salir = true;
            }
        } while (!salir);
    }*/


