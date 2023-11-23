package Modelo;

public abstract class Cliente {
    private String nombre;
    private String domicilio;
    private String nif;
    private String email;

    public abstract String tipoCliente();

    public abstract float calcAnual();

    public abstract float descuentoEnv();

    // Constructor
    public Cliente(String nombre, String domicilio, String nif, String email) {
        this.nombre = nombre;
        this.domicilio = domicilio;
        this.nif = nif;
        this.email = email;
    }

    // Getters y Setters
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDomicilio() {
        return domicilio;
    }

    public void setDomicilio(String domicilio) {
        this.domicilio = domicilio;
    }

    public String getNif() {
        return nif;
    }

    public void setNif(String nif) {
        this.nif = nif;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    // Método para calcular el descuento en gastos de envío
    public double calcularDtoGastosEnvio(double gastosEnvioOriginales) {
        return gastosEnvioOriginales;
    }

    // toString para ver el objeto Cliente
    @Override
    public String toString() {
        return "Nombre - " + nombre +
                ", Domicilio - " + domicilio +
                ", Nif - " + nif +
                ", Email - " + email +
                ", Tipo de cliente - " + tipoCliente();
    }
}
