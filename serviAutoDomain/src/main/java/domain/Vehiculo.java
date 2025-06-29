package domain;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

// Class Vehiculo
public class Vehiculo implements Serializable {
    private String placa;
    private String color;
    private String marca;
    private String estilo;
    private int anio;
    private String vin;
    private double cilindraje;
    private Cliente dueno; // Relationship with Cliente
    private List<OrdenTrabajo> ordenesTrabajo; // Relationship with OrdenTrabajo

    public Vehiculo() {
        this.ordenesTrabajo = new ArrayList<>();
    }

    public Vehiculo(String placa, String color, String marca, String estilo, int anio, String vin, double cilindraje) {
        this.placa = placa;
        this.color = color;
        this.marca = marca;
        this.estilo = estilo;
        this.anio = anio;
        this.vin = vin;
        this.cilindraje = cilindraje;
        this.ordenesTrabajo = new ArrayList<>();
    }

    // Getters
    public String getPlaca() { return placa; }
    public String getColor() { return color; }
    public String getMarca() { return marca; }
    public String getEstilo() { return estilo; }
    public int getAnio() { return anio; }
    public String getVin() { return vin; }
    public double getCilindraje() { return cilindraje; }
    public Cliente getDueno() { return dueno; }
    public List<OrdenTrabajo> getOrdenesTrabajo() { return ordenesTrabajo; }

    // Setters
    public void setColor(String color) { this.color = color; }
    public void setMarca(String marca) { this.marca = marca; }
    public void setEstilo(String estilo) { this.estilo = estilo; }
    public void setAnio(int anio) { this.anio = anio; }
    public void setVin(String vin) { this.vin = vin; }
    public void setCilindraje(double cilindraje) { this.cilindraje = cilindraje; }
    public void setDueno(Cliente dueno) { this.dueno = dueno; }

    // Method to add an OrdenTrabajo to the vehicle
    public void addOrdenTrabajo(OrdenTrabajo orden) {
        if (orden != null && !this.ordenesTrabajo.contains(orden)) {
            this.ordenesTrabajo.add(orden);
            orden.setVehiculo(this); // Set the vehicle for the order
        }
    }

    @Override
    public String toString() {
        return "Vehiculo{" +
                "placa='" + placa + '\'' +
                ", marca='" + marca + '\'' +
                ", anio=" + anio +
                '}';
    }
}
