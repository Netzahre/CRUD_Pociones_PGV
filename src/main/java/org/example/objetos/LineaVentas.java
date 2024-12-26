package org.example.objetos;

public class LineaVentas {
    private int idVenta;
    private int idPocion;
    private int cantidad;
    private double precioTotal;

    public LineaVentas(int idVenta, int idPocion, int cantidad, double precioPocion) {
        this.idVenta = idVenta;
        this.idPocion = idPocion;
        this.cantidad = cantidad;
        this.precioTotal = cantidad * precioPocion;
    }

    public int getIdVenta() {
        return idVenta;
    }

    public void setIdVenta(int idVenta) {
        this.idVenta = idVenta;
    }

    public int getIdPocion() {
        return idPocion;
    }

    public void setIdPocion(int idPocion) {
        this.idPocion = idPocion;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public double getPrecioTotal() {
        return precioTotal;
    }

    public void setPrecioTotal(double precioTotal) {
        this.precioTotal = precioTotal;
    }
}
