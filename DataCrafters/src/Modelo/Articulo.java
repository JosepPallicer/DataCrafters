package Modelo;

public class Articulo {
    private String codigo;
    private String descripcion;
    private double precio;
    private int tiempoPreparacion;
    private double gastosEnvio;



    // Constructor
    public Articulo(String codigo, String descripcion, double precio, int tiempoPreparacion, double gastosEnvio) {
        this.codigo = codigo;
        this.descripcion = descripcion;
        this.precio = precio;
        this.tiempoPreparacion = tiempoPreparacion;
        this.gastosEnvio = gastosEnvio;
    }

    // Getters y Setters
    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public int getTiempoPreparacion() {
        return tiempoPreparacion;
    }

    public void setTiempoPreparacion(int tiempoPreparacion) {
        this.tiempoPreparacion = tiempoPreparacion;
    }

    public double getGastosEnvio() {
        return gastosEnvio;
    }

    public void setGastosEnvio(double gastosEnvio) {
        this.gastosEnvio = gastosEnvio;
    }


    @Override
    public String toString() {
        return "Código - '" + codigo + '\'' +
                ", Descripción - '" + descripcion + '\'' +
                ", Precio - " + precio +
                ", Tiempo Preparación - " + tiempoPreparacion +
                ", Gastos de Envio - " + gastosEnvio;
    }
}
