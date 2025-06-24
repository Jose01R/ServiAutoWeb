package domain;

// Class DetalleOrden
public class DetalleOrden {
    private String idDetalleOrden;
    private int cantidad;
    private String observaciones;
    private String tipoDetalle;
    private String idEstado;
    private OrdenTrabajo ordenTrabajo; // Relationship with OrdenTrabajo
    private Servicio servicio; // Relationship with Servicio (0..1)
    private Repuesto repuesto; // Relationship with Repuesto (0..1)

    public DetalleOrden(String idDetalleOrden) {
        this.idDetalleOrden = idDetalleOrden;
    }

    public DetalleOrden() {
    }

    public DetalleOrden(String idDetalleOrden, int cantidad, String observaciones, String tipoDetalle, String idEstado) {
        this.idDetalleOrden = idDetalleOrden;
        this.cantidad = cantidad;
        this.observaciones = observaciones;
        this.tipoDetalle = tipoDetalle;
        this.idEstado = idEstado;
    }

    // Getters
    public String getIdDetalleOrden() { return idDetalleOrden; }
    public int getCantidad() { return cantidad; }
    public String getObservaciones() { return observaciones; }
    public String getTipoDetalle() { return tipoDetalle; }
    public String getIdEstado() { return idEstado; }
    public OrdenTrabajo getOrdenTrabajo() { return ordenTrabajo; }
    public Servicio getServicio() { return servicio; }
    public Repuesto getRepuesto() { return repuesto; }

    // Setters
    public void setCantidad(int cantidad) { this.cantidad = cantidad; }
    public void setObservaciones(String observaciones) { this.observaciones = observaciones; }
    public void setTipoDetalle(String tipoDetalle) { this.tipoDetalle = tipoDetalle; }
    public void setIdEstado(String idEstado) { this.idEstado = idEstado; }
    public void setOrdenTrabajo(OrdenTrabajo ordenTrabajo) { this.ordenTrabajo = ordenTrabajo; }

    // Special setters for Servicio and Repuesto due to 0..1 relationship and mutual exclusivity
    public void setServicio(Servicio servicio) {
        if (this.repuesto != null) {
            throw new IllegalStateException("A DetalleOrden cannot have both a Servicio and a Repuesto.");
        }
        this.servicio = servicio;
    }

    public void setRepuesto(Repuesto repuesto) {
        if (this.servicio != null) {
            throw new IllegalStateException("A DetalleOrden cannot have both a Servicio and a Repuesto.");
        }
        this.repuesto = repuesto;
    }

    @Override
    public String toString() {
        return "DetalleOrden{" +
                "idDetalleOrden='" + idDetalleOrden + '\'' +
                ", cantidad=" + cantidad +
                ", tipoDetalle='" + tipoDetalle + '\'' +
                '}';
    }
}
