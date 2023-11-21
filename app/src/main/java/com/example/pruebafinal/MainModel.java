package com.example.pruebafinal;
public class MainModel {
    String Nombre, Tipo, Marca, Codigo;
    Long Cantidad;

    public MainModel() {
    }

    public MainModel(String nombre, String tipo, String marca, String codigo, Long cantidad) {
        Nombre = nombre;
        Tipo = tipo;
        Marca = marca;
        Codigo = codigo;
        Cantidad = cantidad;
    }

    public String getCodigo() {
        return Codigo;
    }

    public void setCodigo(String codigo) {
        Codigo = codigo;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public String getTipo() {
        return Tipo;
    }

    public void setTipo(String tipo) {
        Tipo = tipo;
    }

    public Long getCantidad() {
        return Cantidad;
    }

    public void setCantidad(Long cantidad) {
        Cantidad = cantidad;
    }

    public String getMarca() {
        return Marca;
    }

    public void setMarca(String marca) {
        Marca = marca;
    }
}
