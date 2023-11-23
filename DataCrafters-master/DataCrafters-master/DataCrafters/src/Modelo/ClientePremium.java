package Modelo;

public class ClientePremium extends Cliente {
    private double cuotaAnual;
    private double dtoGastosEnvio;

    public ClientePremium(String nombre, String domicilio, String nif, String email) {
        super(nombre, domicilio, nif, email);
    }

    @Override
    public String tipoCliente() {
        return "Premium";
    }

    @Override
    public float calcAnual() {
        return (float) cuotaAnual;
    }

    @Override
    public float descuentoEnv() {
        return (float) dtoGastosEnvio;
    }

    // Getters y setters

    public double getCuotaAnual() {
        return cuotaAnual;
    }

    public void setCuotaAnual(double cuotaAnual) {
        this.cuotaAnual = cuotaAnual;
    }

    public double getDtoGastosEnvio() {
        return dtoGastosEnvio;
    }

    public void setDtoGastosEnvio(double dtoGastosEnvio) {
        this.dtoGastosEnvio = dtoGastosEnvio;
    }

    @Override
    public double calcularDtoGastosEnvio(double gastosEnvioOriginales) {
        // Aplicar el descuento
        return gastosEnvioOriginales * (1 - dtoGastosEnvio);
    }
}
