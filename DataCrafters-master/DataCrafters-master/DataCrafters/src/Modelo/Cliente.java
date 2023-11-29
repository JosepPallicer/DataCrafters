package Modelo;

/**
 * Clase abstracta que representa un cliente en el sistema de la tienda en línea.
 */
public abstract class Cliente {
    private String nombre;
    private String domicilio;
    private String nif;
    private String email;

    /**
     * Método abstracto para obtener el tipo de cliente.
     *
     * @return El tipo de cliente.
     */
    public abstract String tipoCliente();

    /**
     * Método abstracto para calcular el gasto anual del cliente.
     *
     * @return El gasto anual del cliente.
     */
    public abstract float calcAnual();

    /**
     * Método abstracto para calcular el descuento en gastos de envío del cliente.
     *
     * @return El descuento en gastos de envío del cliente.
     */
    public abstract float descuentoEnv();

    /**
     * Constructor que inicializa un objeto Cliente con los detalles proporcionados.
     *
     * @param nombre    El nombre del cliente.
     * @param domicilio La dirección del cliente.
     * @param nif       El NIF (Número de Identificación Fiscal) del cliente.
     * @param email     La dirección de correo electrónico del cliente.
     */
    public Cliente(String nombre, String domicilio, String nif, String email) {
        this.nombre = nombre;
        this.domicilio = domicilio;
        this.nif = nif;
        this.email = email;
    }

    /**
     * Obtiene el nombre del cliente.
     *
     * @return El nombre del cliente.
     */

    public String getNombre() {
        return nombre;
    }

    /**
     * Establece el nombre del cliente.
     *
     * @param nombre El nuevo nombre del cliente.
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Obtiene la dirección del cliente.
     *
     * @return La dirección del cliente.
     */
    public String getDomicilio() {
        return domicilio;
    }

    /**
     * Establece la dirección del cliente.
     * @param domicilio La nueva dirección del cliente.
     */
    public void setDomicilio(String domicilio) {
        this.domicilio = domicilio;
    }

    /**
     * Obtiene el NIF del cliente.
     * @return El NIF del cliente.
     */
    public String getNif() {
        return nif;
    }

    /**
     * Establece el NIF del cliente.
     * @param nif El nuevo NIF del cliente.
     */
    public void setNif(String nif) {
        this.nif = nif;
    }

    /**
     * Obtiene la dirección de correo electrónico del cliente.
     * @return La dirección de correo electrónico del cliente.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Establece la dirección de correo electrónico del cliente.
     * @param email La nueva dirección de correo electrónico del cliente.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Método para calcular el descuento en gastos de envío del cliente.
     *
     * @param gastosEnvioOriginales Los gastos de envío originales antes del descuento.
     * @return Los gastos de envío después de aplicar el descuento.
     */
    public double calcularDtoGastosEnvio(double gastosEnvioOriginales) {
        return gastosEnvioOriginales;
    }

    /**
     * Representación en cadena del objeto Cliente.
     * @return Una cadena que representa los detalles del cliente.
     */
    @Override
    public String toString() {
        return "Nombre - " + nombre +
                ", Domicilio - " + domicilio +
                ", Nif - " + nif +
                ", Email - " + email +
                ", Tipo de cliente - " + tipoCliente();
    }
}
