package Modelo;

/**
 * Clase que representa a un cliente premium en el sistema de la tienda en línea.
 * Extiende la clase abstracta Cliente.
 */

public class ClientePremium extends Cliente {
    private double cuotaAnual;
    private double dtoGastosEnvio;

    /**
     * Constructor que inicializa un objeto ClientePremium con los detalles proporcionados.
     *
     * @param nombre    El nombre del cliente premium.
     * @param domicilio La dirección del cliente premium.
     * @param nif       El NIF (Número de Identificación Fiscal) del cliente premium.
     * @param email     La dirección de correo electrónico del cliente premium.
     */
    public ClientePremium(String nombre, String domicilio, String nif, String email) {
        super(nombre, domicilio, nif, email);
    }

    /**
     * Obtiene el tipo de cliente (Premium).
     *
     * @return El tipo de cliente.
     */
    @Override
    public String tipoCliente() {
        return "Premium";
    }

    /**
     * Calcula el gasto anual del cliente premium, que corresponde a la cuota anual.
     *
     * @return El gasto anual del cliente premium.
     */
    @Override
    public float calcAnual() {
        return (float) cuotaAnual;
    }

    /**
     * Obtiene el descuento en gastos de envío para el cliente premium.
     *
     * @return El descuento en gastos de envío para el cliente premium.
     */
    @Override
    public float descuentoEnv() {
        return (float) dtoGastosEnvio;
    }

    // Getters y setters
    /**
     * Obtiene la cuota anual del cliente premium.
     *
     * @return La cuota anual del cliente premium.
     */
    public double getCuotaAnual() {
        return cuotaAnual;
    }

    /**
     * Establece la cuota anual del cliente premium.
     *
     * @param cuotaAnual La cuota anual a establecer.
     */
    public void setCuotaAnual(double cuotaAnual) {
        this.cuotaAnual = cuotaAnual;
    }

    /**
     * Obtiene el descuento en gastos de envío para el cliente premium.
     *
     * @return El descuento en gastos de envío para el cliente premium.
     */
    public double getDtoGastosEnvio() {
        return dtoGastosEnvio;
    }

    /**
     * Establece el descuento en gastos de envío para el cliente premium.
     *
     * @param dtoGastosEnvio El descuento en gastos de envío a establecer.
     */
    public void setDtoGastosEnvio(double dtoGastosEnvio) {
        this.dtoGastosEnvio = dtoGastosEnvio;
    }

    /**
     * Calcula el descuento en gastos de envío para el cliente premium.
     *
     * @param gastosEnvioOriginales Los gastos de envío originales antes del descuento.
     * @return Los gastos de envío después de aplicar el descuento.
     */
    @Override
    public double calcularDtoGastosEnvio(double gastosEnvioOriginales) {
        // Aplicar el descuento
        return gastosEnvioOriginales * (1 - dtoGastosEnvio);
    }
}
