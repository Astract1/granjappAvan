package com.example.granjapp;

public class Campesino {
    private String nombre;
    private String apellido;
    private String correo;
    private String nombreGranja;

    public Campesino(String nombre, String apellido, String correo, String nombreGranja) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.correo = correo;
        this.nombreGranja = nombreGranja;
    }

    // Métodos getter para acceder a los datos del campesino
    public String getNombre() {
        return nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public String getCorreo() {
        return correo;
    }

    public String getNombreGranja() {
        return nombreGranja;
    }
}