package domain;

import java.io.Serializable;

// Class Repuesto
public class Repuesto implements Serializable {
    private String nombre;
    private double precio;
    private int cantidad;
    private boolean pedido;

    public Repuesto() {
    }

    public Repuesto(String nombre, double precio, int cantidad, boolean pedido) {
        this.nombre = nombre;
        this.precio = precio;
        this.cantidad = cantidad;
        this.pedido = pedido;
    }

    // Getters
    public String getNombre() { return nombre; }
    public double getPrecio() { return precio; }
    public int getCantidad() { return cantidad; }
    public boolean isPedido() { return pedido; }

    // Setters
    public void setNombre(String nombre) { this.nombre = nombre; }
    public void setPrecio(double precio) { this.precio = precio; }
    public void setCantidad(int cantidad) { this.cantidad = cantidad; }
    public void setPedido(boolean pedido) { this.pedido = pedido; }

    @Override
    public String toString() {
        return "Repuesto{" +
                "nombre='" + nombre + '\'' +
                ", cantidad=" + cantidad +
                ", pedido=" + pedido +
                '}';
    }
}
