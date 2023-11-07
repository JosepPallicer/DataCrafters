package Modelo;

public class ListaClientes extends Lista<Cliente> {

    // Constructor
    public ListaClientes() {
        super();
    }

    // Método para añadir clientes
    public void agregarCliente (Cliente cliente){
        this.agregar(cliente);
    }

    // Método para mostrar clientes
    public void mostrarClientes(){
        this.mostrar();
    }

    // Método para buscar un cliente por su NIF
    public Cliente buscarPorNIF(String nif) {
        for (Cliente cliente : lista) {
            if (cliente.getNif().equals(nif)) {
                return cliente;
            }
        }
        return null;
    }

}
