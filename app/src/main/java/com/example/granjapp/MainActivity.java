package com.example.granjapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {
    private EditText edtNombre, edtApellido, edtCorreo, edtContrasena, edtConfirmarContrasena;
    private Button btnConfirmar;

    private dbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new dbHelper(this);

        edtNombre = findViewById(R.id.nombreCrearCuenta);
        edtApellido = findViewById(R.id.apellidoCrearCuenta);
        edtCorreo = findViewById(R.id.correoCrearcuenta);
        edtContrasena = findViewById(R.id.contrasenaCrearCuenta);
        edtConfirmarContrasena = findViewById(R.id.confirmarcontrasenaCrearCuenta);
        btnConfirmar = findViewById(R.id.btnConfirmar);

        btnConfirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nombre = edtNombre.getText().toString().trim();
                String apellido = edtApellido.getText().toString().trim();
                String correo = edtCorreo.getText().toString().trim();
                String contrasena = edtContrasena.getText().toString();
                String confirmarContrasena = edtConfirmarContrasena.getText().toString();

                if (nombre.isEmpty() || apellido.isEmpty() || correo.isEmpty() || contrasena.isEmpty() || confirmarContrasena.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Verificar que las contraseñas coincidan
                if (!contrasena.equals(confirmarContrasena)) {
                    Toast.makeText(MainActivity.this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Validar el formato de la contraseña
                if (!validarContrasena(contrasena)) {
                    Toast.makeText(MainActivity.this, "La contraseña debe tener al menos 8 caracteres, incluyendo números, letras y caracteres especiales", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Validar el formato del correo electrónico
                if (!validarCorreo(correo)) {
                    Toast.makeText(MainActivity.this, "Ingrese un correo electrónico válido", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Verificar si el correo electrónico ya está registrado
                if (dbHelper.existeCorreo(correo)) {
                    Toast.makeText(MainActivity.this, "El correo electrónico ya está registrado en otra cuenta", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Insertar usuario en la base de datos
                long resultado = dbHelper.insertarUsuario(nombre, apellido, correo, contrasena);

                if (resultado != -1) {
                    Toast.makeText(MainActivity.this, "Usuario registrado correctamente", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "Error al registrar usuario", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private boolean validarContrasena(String contrasena) {
        // Verificar que la contraseña tenga al menos 8 caracteres, incluyendo números, letras y caracteres especiales
        String patronContrasena = "^(?=.*[0-9])(?=.*[a-zA-Z])(?=.*[@#$%^&+=!\\-_])(?=\\S+$).{8,}$";
        return Pattern.matches(patronContrasena, contrasena);
    }


    private boolean validarCorreo(String correo) {
        // Patrón para validar un correo electrónico
        String patronCorreo = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        return Pattern.matches(patronCorreo, correo);
    }
}
