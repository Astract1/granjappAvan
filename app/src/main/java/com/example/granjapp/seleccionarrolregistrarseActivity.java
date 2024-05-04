package com.example.granjapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

public class seleccionarrolregistrarseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seleccionarrolregistrarse);

        // Obtener referencia a los botones
        AppCompatButton btnGranjeroSeleccion = findViewById(R.id.granjeroSeleccion);
        AppCompatButton btnCompradorSeleccion = findViewById(R.id.compradorSeleccion);
        AppCompatButton btnSocioSeleccion = findViewById(R.id.socioSeleccion);

        // Configurar OnClickListener para el botón de granjero
        btnGranjeroSeleccion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Crear intent para iniciar RegistroGranjeroActivity
                Intent intent = new Intent(seleccionarrolregistrarseActivity.this, RegistroGranjeroActivity.class);
                // Iniciar la actividad
                startActivity(intent);
            }
        });

        // Configurar OnClickListener para el botón de comprador
        btnCompradorSeleccion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Crear intent para iniciar MainActivity
                Intent intent = new Intent(seleccionarrolregistrarseActivity.this, MainActivity.class);
                // Iniciar la actividad
                startActivity(intent);
            }
        });

        // Configurar OnClickListener para el botón de socio
        btnSocioSeleccion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Crear intent para iniciar RegistroSocioActivity
                Intent intent = new Intent(seleccionarrolregistrarseActivity.this, RegistroSocioActivity.class);
                // Iniciar la actividad
                startActivity(intent);
            }
        });
    }
}
