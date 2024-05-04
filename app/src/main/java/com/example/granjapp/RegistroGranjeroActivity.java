package com.example.granjapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.regex.Pattern;

public class RegistroGranjeroActivity extends AppCompatActivity {
    private EditText nombreEditText, apellidoEditText, correoEditText, contrasenaEditText, confirmarContrasenaEditText, nombreGranjaEditText;
    private Button btnConfirmarCampesino;

    private dbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_granjero);

        dbHelper = new dbHelper(this);

        nombreEditText = findViewById(R.id.nombreCrearCuentaCampesino);
        apellidoEditText = findViewById(R.id.apellidoCrearCuentaCampesino);
        correoEditText = findViewById(R.id.correoCrearcuentaCampesino);
        contrasenaEditText = findViewById(R.id.contrase単aCrearCueentaCampesino);
        confirmarContrasenaEditText = findViewById(R.id.Confirmarcontrase単aCrearCueentaCampesino);
        nombreGranjaEditText = findViewById(R.id.nombreGranjaCampesino);
        btnConfirmarCampesino = findViewById(R.id.btnConfirmarCampesino);

        btnConfirmarCampesino.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registrarCampesino();
            }
        });
    }

    private void registrarCampesino() {
        String nombre = nombreEditText.getText().toString().trim();
        String apellido = apellidoEditText.getText().toString().trim();
        String correo = correoEditText.getText().toString().trim();
        String contrasena = contrasenaEditText.getText().toString();
        String confirmarContrasena = confirmarContrasenaEditText.getText().toString();
        String nombreGranja = nombreGranjaEditText.getText().toString().trim();

        if (nombre.isEmpty() || apellido.isEmpty() || correo.isEmpty() || contrasena.isEmpty() || confirmarContrasena.isEmpty() || nombreGranja.isEmpty()) {
            Toast.makeText(this, "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!contrasena.equals(confirmarContrasena)) {
            Toast.makeText(this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!validarContrasena(contrasena)) {
            Toast.makeText(this, "La contraseña debe tener al menos 8 caracteres, incluyendo números, letras y caracteres especiales", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!validarCorreo(correo)) {
            Toast.makeText(this, "Ingrese un correo electrónico válido", Toast.LENGTH_SHORT).show();
            return;
        }


        if (dbHelper.existeCorreo(correo)) {
            Toast.makeText(this, "El correo electrónico ya está registrado en otra cuenta", Toast.LENGTH_SHORT).show();
            return;
        }

        long resultado = dbHelper.insertarCampesino(nombre, apellido, correo, contrasena, nombreGranja);
        if (resultado != -1) {
            Toast.makeText(this, "Campesino registrado correctamente", Toast.LENGTH_SHORT).show();
            finish(); // Finalizar la actividad actual
        } else {
            Toast.makeText(this, "Error al registrar el campesino", Toast.LENGTH_SHORT).show();
        }
    }



    private boolean validarContrasena(String contrasena) {
        // Verificar que la contraseña tenga al menos 8 caracteres, incluyendo números, letras y caracteres especiales
        String patronContrasena = "^(?=.*[0-9])(?=.*[a-zA-Z])(?=.*[@#$%^&+=!\\-_])(?=\\S+$).{8,}$";
        return Pattern.matches(patronContrasena, contrasena);
    }


    private boolean validarCorreo(String correo) {
        String patronCorreo = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        return Pattern.matches(patronCorreo, correo);
    }
}
