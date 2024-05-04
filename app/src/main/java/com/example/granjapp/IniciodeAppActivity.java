package com.example.granjapp;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import androidx.appcompat.widget.AppCompatButton;

public class IniciodeAppActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_iniciode_app);
        // Obtener referencia al botón de registro
        AppCompatButton btnRegistrarse = findViewById(R.id.btnRegistrarse);
        // Obtener referencia al botón de iniciar sesión
        AppCompatButton btnIniciarSesion = findViewById(R.id.btnIniciarSesion);

        // Configurar OnClickListener para el botón de registro
        btnRegistrarse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Crear intent para iniciar SeleccionarRolRegistrarseActivity
                Intent intent = new Intent(IniciodeAppActivity.this, seleccionarrolregistrarseActivity.class);
                // Iniciar la actividad
                startActivity(intent);
            }
        });

        // Configurar OnClickListener para el botón de iniciar sesión
        btnIniciarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Crear intent para iniciar SeleccionarRolInicioDeSesionActivity
                Intent intent = new Intent(IniciodeAppActivity.this, SeleccionarRolInicioDeSesionActivity.class);
                // Iniciar la actividad
                startActivity(intent);
            }
        });
    }
}
