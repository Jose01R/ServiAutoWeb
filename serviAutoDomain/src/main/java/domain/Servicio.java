package domain;

// Class Servicio
public class Servicio {
    private String nombre;
    private double precio;
    private double costoManoObra;

    public Servicio(String nombre, double precio, double costoManoObra) {
        this.nombre = nombre;
        this.precio = precio;
        this.costoManoObra = costoManoObra;
    }

    public Servicio() {
    }

    // Getters
    public String getNombre() { return nombre; }
    public double getPrecio() { return precio; }
    public double getCostoManoObra() { return costoManoObra; }

    // Setters
    public void setNombre(String nombre) { this.nombre = nombre; }
    public void setPrecio(double precio) { this.precio = precio; }
    public void setCostoManoObra(double costoManoObra) { this.costoManoObra = costoManoObra; }

    @Override
    public String toString() {
        return "Servicio{" +
                "nombre='" + nombre + '\'' +
                ", precio=" + precio +
                '}';
    }
}
