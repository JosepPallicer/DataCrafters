package Modelo;

public class ListaPedidos extends Lista<Pedido> {

    // Constructor
    public ListaPedidos() {
        super();
    }

    // Método para agregar un pedido
    public void agregarPedido(Pedido pedido){
        this.agregar(pedido);
    }

    // Método para ver todos los pedidos
    public void verPedidos(Pedido pedido){
        this.mostrar();
    }

    // Método para buscar un pedido por su número de pedido
    public Pedido buscarPorNumeroPedido(int numeroPedido) {
        for (Pedido pedido : lista) {
            if (pedido.getNumeroPedido() == numeroPedido) {
                return pedido;
            }
        }
        return null;
    }

    // Método para mostrar todos los pedidos de un cliente específico
    public void buscarPedidosCliente(String nifCliente) {
        for (Pedido pedido : lista) {
            if (pedido.getCliente().getNif().equals(nifCliente)) {
                System.out.println(pedido);
            }
        }
    }

}
