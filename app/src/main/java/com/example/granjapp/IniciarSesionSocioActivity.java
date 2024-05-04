package com.example.granjapp;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import androidx.appcompat.widget.AppCompatButton;
import android.widget.Toast;

public class IniciarSesionSocioActivity extends AppCompatActivity {

    private EditText editTextCorreoSocio;
    private EditText editTextContraseñaSocio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_iniciar_sesion_socio);

        // Inicializar EditText
        editTextCorreoSocio = findViewById(R.id.editTextCorreoSocio);
        editTextContraseñaSocio = findViewById(R.id.editTextContraseñaSocio);

        // Obtener referencia al botón de iniciar sesión
        AppCompatButton btnIniciarSesion = findViewById(R.id.btnIngresarSocio);

        // Configurar OnClickListener para el botón de iniciar sesión
        btnIniciarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iniciarSesionSocio();
            }
        });
    }

    private void iniciarSesionSocio() {
        // Obtener los valores de correo y contraseña ingresados por el usuario
        String correo = editTextCorreoSocio.getText().toString().trim();
        String contraseña = editTextContraseñaSocio.getText().toString().trim();

        // Validar credenciales con la base de datos
        dbHelper dbHelper = new dbHelper(this);
        boolean credencialesValidas = dbHelper.validarCredencialesSocio(correo, contraseña);

        if (credencialesValidas) {
            Toast.makeText(this, "Inicio de Sesion Correcto", Toast.LENGTH_SHORT).show();
        } else {
            // Mostrar mensaje de error si las credenciales son inválidas
            Toast.makeText(this, "Correo o contraseña incorrectos", Toast.LENGTH_SHORT).show();
        }
    }
}
