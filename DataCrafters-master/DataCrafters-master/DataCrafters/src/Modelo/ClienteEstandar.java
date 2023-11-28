package Modelo;

/**
 * Clase que representa a un cliente estándar en el sistema de la tienda en línea.
 * Extiende la clase abstracta Cliente.
 */
public class ClienteEstandar extends Cliente {

    /**
     * Constructor que inicializa un objeto ClienteEstandar con los detalles proporcionados.
     * @param nombre    El nombre del cliente estándar.
     * @param domicilio La dirección del cliente estándar.
     * @param nif       El NIF (Número de Identificación Fiscal) del cliente estándar.
     * @param email     La dirección de correo electrónico del cliente estándar.
     */
    public ClienteEstandar(String nombre, String domicilio, String nif, String email) {
        super(nombre, domicilio, nif, email);
    }

    /**
     * Obtiene el tipo de cliente (Estándar).
     * @return El tipo de cliente.
     */
    @Override
    public String tipoCliente() {
        return "Estándar";
    }

    /**
     * Calcula el gasto anual del cliente estándar.
     * @return El gasto anual del cliente estándar que aquí es 0.
     */
    @Override
    public float calcAnual() {
        return 0;
    }

    /**
     * Calcula el descuento en gastos de envío para el cliente estándar.
     * @return El descuento en gastos de envío para el cliente estándar que aquí es 0.
     */
    @Override
    public float descuentoEnv() {
        return 0;
    }

    /**
     * Calcula el descuento en gastos de envío para el cliente estándar.
     * En este caso, no hay descuento, por lo que devuelve los gastos de envío originales.
     * @param gastosEnvioOriginales Los gastos de envío originales antes del descuento.
     * @return Los gastos de envío después de aplicar el descuento que aquí no tiene cambios.
     */
    @Override
    public double calcularDtoGastosEnvio(double gastosEnvioOriginales) {
        // Sin descuento para clientes estándar
        return gastosEnvioOriginales;
    }

}
