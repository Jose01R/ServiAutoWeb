package domain;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

// Class OrdenTrabajo
public class OrdenTrabajo implements Serializable {
    private String idOrdenTrabajo;
    private String descripcionSolicitud;
    private Date fechaIngreso;
    private String estado;
    private Date fechaDevolucion; // Can be null
    private Vehiculo vehiculo; // Relationship with Vehiculo
    private List<DetalleOrden> detallesOrden; // Relationship with DetalleOrden

    public OrdenTrabajo(String idOrdenTrabajo) {
        this.idOrdenTrabajo = idOrdenTrabajo;
        this.detallesOrden = new ArrayList<>();
    }

    public OrdenTrabajo() {
        this.detallesOrden = new ArrayList<>();
    }

    public OrdenTrabajo(String idOrdenTrabajo, String descripcionSolicitud, Date fechaIngreso, String estado) {
        this.idOrdenTrabajo = idOrdenTrabajo;
        this.descripcionSolicitud = descripcionSolicitud;
        this.fechaIngreso = fechaIngreso;
        this.estado = estado;
        this.detallesOrden = new ArrayList<>();
    }

    // Getters
    public String getIdOrdenTrabajo() { return idOrdenTrabajo; }
    public String getDescripcionSolicitud() { return descripcionSolicitud; }
    public Date getFechaIngreso() { return fechaIngreso; }
    public String getEstado() { return estado; }
    public Date getFechaDevolucion() { return fechaDevolucion; }
    public Vehiculo getVehiculo() { return vehiculo; }
    public List<DetalleOrden> getDetallesOrden() { return detallesOrden; }

    // Setters
    public void setDescripcionSolicitud(String descripcionSolicitud) { this.descripcionSolicitud = descripcionSolicitud; }
    public void setEstado(String estado) { this.estado = estado; }
    public void setFechaDevolucion(Date fechaDevolucion) { this.fechaDevolucion = fechaDevolucion; }
    public void setVehiculo(Vehiculo vehiculo) { this.vehiculo = vehiculo; }

    // Method to add a DetalleOrden to the order
    public void addDetalleOrden(DetalleOrden detalle) {
        if (detalle != null && !this.detallesOrden.contains(detalle)) {
            this.detallesOrden.add(detalle);
            detalle.setOrdenTrabajo(this); // Set the order for the detail
        }
    }

    @Override
    public String toString() {
        return "OrdenTrabajo{" +
                "idOrdenTrabajo='" + idOrdenTrabajo + '\'' +
                ", estado='" + estado + '\'' +
                ", fechaIngreso=" + fechaIngreso +
                '}';
    }
}
