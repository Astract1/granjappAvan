package com.example.granjapp;

public class PuntoVenta {
    private int id;
    private String direccion;
    private String horaEntrada;
    private String horaSalida;
    private String fecha;
    private String estado;

    public PuntoVenta(int id, String direccion, String horaEntrada, String horaSalida, String fecha, String estado) {
        this.id = id;
        this.direccion = direccion;
        this.horaEntrada = horaEntrada;
        this.horaSalida = horaSalida;
        this.fecha = fecha;
        this.estado = estado;
    }

    // Getters y Setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getHoraEntrada() {
        return horaEntrada;
    }

    public void setHoraEntrada(String horaEntrada) {
        this.horaEntrada = horaEntrada;
    }

    public String getHoraSalida() {
        return horaSalida;
    }

    public void setHoraSalida(String horaSalida) {
        this.horaSalida = horaSalida;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}