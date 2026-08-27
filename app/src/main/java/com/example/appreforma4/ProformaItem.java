package com.example.appreforma4;

public class ProformaItem {
    private String codigo;
    private String producto;
    private double precio;
    private int cantidad;
    private double total;

    public ProformaItem(String codigo, String producto, double precio, int cantidad, double total) {
        this.codigo = codigo;
        this.producto = producto;
        this.precio = precio;
        this.cantidad = cantidad;
        this.total = total;
    }

    // Getters
    public String getCodigo() { return codigo; }
    public String getProducto() { return producto; }
    public double getPrecio() { return precio; }
    public int getCantidad() { return cantidad; }
    public double getTotal() { return total; }
}
