package Controlador;

import java.time.LocalDateTime;
import java.util.List;

import Modelo.*;

public class Controlador {
    private Datos datos;
    public Controlador() {
        datos = new Datos ();
    }

    public void crearArticulo(String codigo, String descripcion, double precio, int tiempoPreparacion, double gastosEnvio) {
        Articulo articulo = new Articulo(codigo, descripcion, precio, tiempoPreparacion, gastosEnvio);
        datos.getListaArticulos().agregarArticulos(articulo);
    }

    public void mostrarArticulos() {
        datos.getListaArticulos().mostrarArticulos();
    }

    public Articulo buscarPorCodigo(String codigo) {
        return datos.getListaArticulos().buscarPorCodigo(codigo);
    }

    public void crearCliente(Cliente cliente) {
        datos.getListaClientes().agregarCliente(cliente);
    }

    public void mostrarClientes() {
        datos.getListaClientes().mostrarClientes();
    }

    public Cliente buscarPorNIF(String nif) {
        return datos.getListaClientes().buscarPorNIF(nif);
    }

    public void crearPedido (int numeroPedido, Cliente cliente, Articulo articulo, int cantidad, LocalDateTime fechaHora,boolean enviado){
        Pedido pedido = new Pedido(numeroPedido, cliente, articulo, cantidad, fechaHora, enviado);
        datos.getListaPedidos().agregarPedido(pedido);
    }

    public void mostrarPedidos() {
        datos.getListaPedidos().mostrar();
    }

    public Pedido mostrarPorNumero(int numeroPedido) {
        return datos.getListaPedidos().buscarPorNumeroPedido(numeroPedido);
    }
}
