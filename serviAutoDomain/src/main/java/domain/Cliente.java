package domain;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Cliente implements Serializable {
    private String idCliente;
    private String nombre;
    private String primerApellido;
    private String segundoApellido;
    private String telefono;
    private String celular;
    private String direccion;
    private String email;
    private List<Vehiculo> vehiculos; // Relationship with Vehiculo

    public Cliente(String idCliente, String nombre, String primerApellido, String segundoApellido,
                   String telefono, String celular, String direccion, String email) {
        this.idCliente = idCliente;
        this.nombre = nombre;
        this.primerApellido = primerApellido;
        this.segundoApellido = segundoApellido;
        this.telefono = telefono;
        this.celular = celular;
        this.direccion = direccion;
        this.email = email;
        this.vehiculos = new ArrayList<>();
    }

    public Cliente(String idCliente) {
        this.idCliente = idCliente;
        this.vehiculos = new ArrayList<>();
    }

    public Cliente() {
        this.vehiculos = new ArrayList<>();
    }

    // Getters
    public String getIdCliente() { return idCliente; }
    public String getNombre() { return nombre; }
    public String getPrimerApellido() { return primerApellido; }
    public String getSegundoApellido() { return segundoApellido; }
    public String getTelefono() { return telefono; }
    public String getCelular() { return celular; }
    public String getDireccion() { return direccion; }
    public String getEmail() { return email; }
    public List<Vehiculo> getVehiculos() { return vehiculos; }

    // Setters (if needed, though some IDs might not have setters)
    public void setNombre(String nombre) { this.nombre = nombre; }
    public void setPrimerApellido(String primerApellido) { this.primerApellido = primerApellido; }
    public void setSegundoApellido(String segundoApellido) { this.segundoApellido = segundoApellido; }
    public void setTelefono(String telefono) { this.telefono = telefono; }
    public void setCelular(String celular) { this.celular = celular; }
    public void setDireccion(String direccion) { this.direccion = direccion; }
    public void setEmail(String email) { this.email = email; }

    // Method to add a vehicle to the client
    public void addVehiculo(Vehiculo vehiculo) {
        if (vehiculo != null && !this.vehiculos.contains(vehiculo)) {
            this.vehiculos.add(vehiculo);
            vehiculo.setDueno(this); // Set the owner of the vehicle
        }
    }

    @Override
    public String toString() {
        return "Cliente{" +
                "idCliente='" + idCliente + '\'' +
                ", nombre='" + nombre + '\'' +
                ", primerApellido='" + primerApellido + '\'' +
                '}';
    }
}