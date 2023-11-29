package Modelo;

import java.time.LocalDateTime;

public class Pedido {
    private int numeroPedido;
    private Cliente cliente;
    private Articulo articulo;
    private int cantidad;
    private LocalDateTime fechaHora;
    private boolean enviado;

    // Constructor
    public Pedido(int numeroPedido, Cliente cliente, Articulo articulo, int cantidad, LocalDateTime fechaHora, boolean enviado) {
        this.numeroPedido = numeroPedido;
        this.cliente = cliente;
        this.articulo = articulo;
        this.cantidad = cantidad;
        this.fechaHora = fechaHora;
        this.enviado = false;
    }
    // construcctor vacio
    public Pedido(){
        this.numeroPedido = 0;
        this.cliente = null;
        this.articulo = null;
        this.cantidad = 0;
        this.fechaHora = null;
        this.enviado = false;
    }

    // Getters y Setters
    public int getNumeroPedido() {
        return numeroPedido;
    }

    public void setNumeroPedido(int numeroPedido) {
        this.numeroPedido = numeroPedido;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Articulo getArticulo() {
        return articulo;
    }

    public void setArticulo(Articulo articulo) {
        this.articulo = articulo;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(LocalDateTime fechaHora) {
        this.fechaHora = fechaHora;
    }

    // Método para calcular el precio total del pedido
    public double calcularPrecio() {
        double precioArticulo = articulo.getPrecio();
        double gastosEnvio = articulo.getGastosEnvio();
        double descuentoEnvio = cliente.calcularDtoGastosEnvio(gastosEnvio);

        return (precioArticulo * cantidad) + descuentoEnvio;
    }

    public double precioEnvio() {
        double gastosEnvio = articulo.getGastosEnvio();
        float descuentoCliente = cliente.descuentoEnv();

        return gastosEnvio * (1 - descuentoCliente);
    }

    // Método para marcar el pedido como enviado
    public boolean pedidoEnviado() {
        this.enviado = true;
        return this.enviado;
    }

    // Método para obtener el estado de envío del pedido
    public boolean comprobarEnviado() {
        return this.enviado;
    }

    @Override
    public String toString() {
        double precioTotal = calcularPrecio();
        double costeEnvio = articulo.getGastosEnvio();
        return "Numero del pedido - " + numeroPedido +
                ", Fecha - " + fechaHora +
                ", Nif del cliente - " + cliente.getNif() +
                ", Nombre del cliente - " + cliente.getNombre() +
                ", Codigo del articulo - " + articulo.getCodigo() +
                ", Descripcion del articulo - " + articulo.getDescripcion() +
                ", Cantidad - " + cantidad +
                ", Precio del articulo - " + articulo.getPrecio() +
                ", Coste de envio - " + costeEnvio +
                ", Precio total - " + precioTotal +
                ", Enviado? - " + (enviado ? "Si" : "No");
    }

}
