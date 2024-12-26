package org.example.objetos;

public class Ventas {
    private int idVenta;
    private int idCliente;
    private double costoVenta;

    public Ventas(int idVenta, int idCliente, double costoVenta) {
        this.idVenta = idVenta;
        this.idCliente = idCliente;
        this.costoVenta = costoVenta;
    }

    public int getIdVenta() {
        return idVenta;
    }

    public void setIdVenta(int idVenta) {
        this.idVenta = idVenta;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public double getCostoVenta() {
        return costoVenta;
    }

    public void setCostoVenta(double costoVenta) {
        this.costoVenta = costoVenta;
    }
}
