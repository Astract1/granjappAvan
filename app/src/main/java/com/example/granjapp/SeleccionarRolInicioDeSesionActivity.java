package com.example.granjapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

public class SeleccionarRolInicioDeSesionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seleccionar_rol_inicio_de_sesion);

        // Obtener referencia a los botones
        AppCompatButton btnCompradorSeleccion = findViewById(R.id.compradorSeleccionInicioSesion);
        AppCompatButton btnGranjeroSeleccion = findViewById(R.id.granjeroSeleccionInicioSesion);
        AppCompatButton btnSocioSeleccion = findViewById(R.id.socioSeleccionInicioSesion);

        // Configurar OnClickListener para el botón de comprador
        btnCompradorSeleccion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Crear intent para iniciar IniciarSesionCompradorActivity
                Intent intent = new Intent(SeleccionarRolInicioDeSesionActivity.this, IniciarSesionCompradorActivity.class);
                // Iniciar la actividad
                startActivity(intent);
            }
        });

        // Configurar OnClickListener para el botón de granjero
        btnGranjeroSeleccion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Crear intent para iniciar IniciarSesionCampesinoActivity
                Intent intent = new Intent(SeleccionarRolInicioDeSesionActivity.this, IniciarSesionCampesinoActivity.class);
                // Iniciar la actividad
                startActivity(intent);
            }
        });

        // Configurar OnClickListener para el botón de socio
        btnSocioSeleccion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Crear intent para iniciar IniciarSesionSocioActivity
                Intent intent = new Intent(SeleccionarRolInicioDeSesionActivity.this, IniciarSesionSocioActivity.class);
                // Iniciar la actividad
                startActivity(intent);
            }
        });

        findViewById(R.id.volverSeleccionRolInicioSesion).setOnClickListener( (v) -> {
            Intent intent = new Intent(this, IniciodeAppActivity.class);
            startActivity(intent);
        });
    }


}
