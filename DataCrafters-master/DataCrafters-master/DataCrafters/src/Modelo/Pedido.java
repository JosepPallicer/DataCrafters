package Modelo;

import java.time.LocalDateTime;

/**
 * Clase que representa un pedido realizado por un cliente para un artículo en la tienda en línea.
 */

public class Pedido {
    private int numeroPedido;
    private Cliente cliente;
    private Articulo articulo;
    private int cantidad;
    private LocalDateTime fechaHora;
    private boolean enviado;

    /**
     * Constructor que inicializa un objeto Pedido con los detalles proporcionados.
     *
     * @param numeroPedido Número único que identifica el pedido.
     * @param cliente      Cliente que realiza el pedido.
     * @param articulo     Artículo solicitado en el pedido.
     * @param cantidad     Cantidad de unidades del artículo solicitado.
     * @param fechaHora    Fecha y hora en que se realiza el pedido.
     * @param enviado      Estado de envío del pedido (true si está enviado, false si no).
     */
    public Pedido(int numeroPedido, Cliente cliente, Articulo articulo, int cantidad, LocalDateTime fechaHora, boolean enviado) {
        this.numeroPedido = numeroPedido;
        this.cliente = cliente;
        this.articulo = articulo;
        this.cantidad = cantidad;
        this.fechaHora = fechaHora;
        this.enviado = false;
    }

    /**
     * Constructor vacío para la creación de un objeto Pedido sin inicializar sus atributos.
     */
    public Pedido(){
        this.numeroPedido = 0;
        this.cliente = null;
        this.articulo = null;
        this.cantidad = 0;
        this.fechaHora = null;
        this.enviado = false;
    }

    /**
     * Obtiene el número único que identifica el pedido.
     *
     * @return El número del pedido.
     */
    public int getNumeroPedido() {
        return numeroPedido;
    }

    /**
     * Establece el número único que identifica el pedido.
     *
     * @param numeroPedido El número del pedido a establecer.
     */
    public void setNumeroPedido(int numeroPedido) {
        this.numeroPedido = numeroPedido;
    }

    /**
     * Obtiene el cliente que realizó el pedido.
     *
     * @return El cliente del pedido.
     */
    public Cliente getCliente() {
        return cliente;
    }

    /**
     * Establece el cliente que realiza el pedido.
     *
     * @param cliente El cliente del pedido.
     */
    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    /**
     * Obtiene el artículo solicitado en el pedido.
     *
     * @return El artículo del pedido.
     */
    public Articulo getArticulo() {
        return articulo;
    }

    /**
     * Establece el artículo solicitado en el pedido.
     *
     * @param articulo El artículo del pedido.
     */
    public void setArticulo(Articulo articulo) {
        this.articulo = articulo;
    }

    /**
     * Obtiene la cantidad de unidades del artículo solicitado.
     *
     * @return La cantidad del artículo en el pedido.
     */
    public int getCantidad() {
        return cantidad;
    }

    /**
     * Establece la cantidad de unidades del artículo solicitado.
     *
     * @param cantidad La cantidad del artículo en el pedido.
     */
    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    /**
     * Obtiene la fecha y hora en que se realizó el pedido.
     *
     * @return La fecha y hora del pedido.
     */
    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

    /**
     * Establece la fecha y hora en que se realizó el pedido.
     *
     * @param fechaHora La fecha y hora del pedido.
     */
    public void setFechaHora(LocalDateTime fechaHora) {
        this.fechaHora = fechaHora;
    }

    /**
     * Calcula el precio total del pedido, teniendo en cuenta el descuento en gastos de envío para el cliente.
     *
     * @return El precio total del pedido.
     */
    public double calcularPrecio() {
        double precioArticulo = articulo.getPrecio();
        double gastosEnvio = articulo.getGastosEnvio();
        double descuentoEnvio = cliente.calcularDtoGastosEnvio(gastosEnvio);

        return (precioArticulo * cantidad) + descuentoEnvio;
    }

    /**
     * Calcula el costo de envío para el pedido, aplicando el descuento correspondiente al cliente.
     *
     * @return El costo de envío del pedido.
     */
    public double precioEnvio() {
        double gastosEnvio = articulo.getGastosEnvio();
        float descuentoCliente = cliente.descuentoEnv();

        return gastosEnvio * (1 - descuentoCliente);
    }

    /**
     * Marca el pedido como enviado.
     *
     * @return true si el pedido se ha marcado como enviado, false si no.
     */
    public boolean pedidoEnviado() {
        this.enviado = true;
        return this.enviado;
    }

    /**
     * Marca el estado del enviodel pedido.
     *
     * @return true si el pedido esta enviado, false si no.
     */
    public boolean comprobarEnviado() {
        return this.enviado;
    }

    /**
     * Representación en formato de cadena del objeto Pedido.
     *
     * @return Una cadena que representa el objeto Pedido.
     */
    @Override
    public String toString() {
        double precioTotal = calcularPrecio();
        double costeEnvio = articulo.getGastosEnvio();
        return "Numero del pedido - " + numeroPedido +
                ", Fecha - " + fechaHora +
                ", Nif del cliente - " + cliente.getNif() +
                ", Nombre del cliente - " + cliente.getNombre() +
                ", Descripcion del articulo - " + articulo.getDescripcion() +
                ", Cantidad - " + cantidad +
                ", Precio del articulo - " + articulo.getPrecio() +
                ", Coste de envio - " + costeEnvio +
                ", Precio total - " + precioTotal +
                ", Enviado? - " + (enviado ? "Si" : "No");
    }

}
