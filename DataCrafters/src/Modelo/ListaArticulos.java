package Modelo;

public class ListaArticulos extends Lista<Articulo> {

    // Constructor
    public ListaArticulos() {
        super();
    }

    // Método para buscar un artículo por su código
    public Articulo buscarPorCodigo(String codigo) {
        for (Articulo articulo : lista) {
            if (articulo.getCodigo().equals(codigo)) {
                return articulo;
            }
        }
        return null;
    }

    // Método para añadir artículos
    public void agregarArticulos(Articulo articulo) {
        this.agregar(articulo);
    }

    // Método para mostrar todos los artículos
    public void mostrarArticulos() {
        this.mostrar();
    }

}
