package Modelo;

/**
 * Esta clase representa un artículo dentro del sistema de nuestra tieda en línea
 */
public class Articulo {
    private String nombre;
    private String descripcion;
    private double precio;
    private int tiempoPreparacion;
    private double gastosEnvio;

    /**
     * Constructor que inicializa un objeto Articulo con los detalles proporcionados.
     *
     * @param nombre           El nombre  del artículo.
     * @param descripcion      La descripción del artículo.
     * @param precio           El precio del artículo.
     * @param tiempoPreparacion El tiempo de preparación del artículo.
     * @param gastosEnvio      Los gastos de envío asociados al artículo.
     */
    public Articulo(String nombre, String descripcion, double precio, int tiempoPreparacion, double gastosEnvio) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.tiempoPreparacion = tiempoPreparacion;
        this.gastosEnvio = gastosEnvio;
    }
    /**
     * Obtiene el nombre o código del artículo.
     *
     * @return El nombre o código del artículo.
     */

    public String getNombre() {
        return nombre;
    }
    /**
     * Establece el nombre o código del artículo.
     *
     * @param nombre Nombre del artículo.
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    /**
     * Obtiene la descripción del artículo.
     *
     * @return La descripción del artículo.
     */
    public String getDescripcion() {
        return descripcion;
    }
    /**
     * Establece la descripción del artículo.
     *
     * @param descripcion La nueva descripción del artículo.
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    /**
     * Obtiene el precio del artículo.
     *
     * @return El precio del artículo.
     */
    public double getPrecio() {
        return precio;
    }
    /**
     * Establece el precio del artículo.
     *
     * @param precio El nuevo precio del artículo.
     */
    public void setPrecio(double precio) {
        this.precio = precio;
    }
    /**
     * Obtiene el tiempo de preparación del artículo.
     *
     * @return El tiempo de preparación del artículo.
     */
    public int getTiempoPreparacion() {
        return tiempoPreparacion;
    }
    /**
     * Establece el tiempo de preparación del artículo.
     *
     * @param tiempoPreparacion El nuevo tiempo de preparación del artículo.
     */
    public void setTiempoPreparacion(int tiempoPreparacion) {
        this.tiempoPreparacion = tiempoPreparacion;
    }
    /**
     * Obtiene los gastos de envío asociados al artículo.
     *
     * @return Los gastos de envío del artículo.
     */
    public double getGastosEnvio() {
        return gastosEnvio;
    }
    /**
     * Establece los gastos de envío asociados al artículo.
     *
     * @param gastosEnvio Los nuevos gastos de envío del artículo.
     */
    public void setGastosEnvio(double gastosEnvio) {
        this.gastosEnvio = gastosEnvio;
    }
    /**
     * Representación en cadena del objeto Articulo.
     *
     * @return Una cadena que representa los detalles del artículo.
     */

    @Override
    public String toString() {
        return "Código - '" + nombre + '\'' +
                ", Descripción - '" + descripcion + '\'' +
                ", Precio - " + precio +
                ", Tiempo Preparación - " + tiempoPreparacion +
                ", Gastos de Envio - " + gastosEnvio;
    }
}
