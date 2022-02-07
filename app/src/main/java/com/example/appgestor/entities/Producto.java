package com.example.appgestor.entities;

public class Producto {

    private int id;
    private int id_pdv;
    private String producto;
    private double costo;
    private double p_mayor;
    private int stock;

    public Producto() {
    }

    public Producto(int id, int id_pdv, String producto, double costo, double p_mayor, int stock) {
        this.id = id;
        this.id_pdv = id_pdv;
        this.producto = producto;
        this.costo = costo;
        this.p_mayor = p_mayor;
        this.stock = stock;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_pdv() {
        return id_pdv;
    }

    public void setId_pdv(int id_pdv) {
        this.id_pdv = id_pdv;
    }

    public String getProducto() {
        return producto;
    }

    public void setProducto(String producto) {
        this.producto = producto;
    }

    public double getCosto() {
        return costo;
    }

    public void setCosto(double costo) {
        this.costo = costo;
    }

    public double getP_mayor() {
        return p_mayor;
    }

    public void setP_mayor(double p_mayor) {
        this.p_mayor = p_mayor;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }
}
