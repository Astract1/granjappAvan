package com.example.granjapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.regex.Pattern;

public class RegistroSocioActivity extends AppCompatActivity {
    private EditText nombreEditText, apellidoEditText, correoEditText, contrasenaEditText, confirmarContrasenaEditText, nombreOrganizacionEditText;
    private Button btnConfirmarSocio;
    private dbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_socio);

        dbHelper = new dbHelper(this);

        nombreEditText = findViewById(R.id.nombreCrearCuentaSocio);
        apellidoEditText = findViewById(R.id.apellidoCrearCuentaSocio);
        correoEditText = findViewById(R.id.correoCrearcuentaSocio);
        contrasenaEditText = findViewById(R.id.contrase単aCrearCueentaSocio);
        confirmarContrasenaEditText = findViewById(R.id.Confirmarcontrase単aCrearCueentaSocio);
        nombreOrganizacionEditText = findViewById(R.id.nombreOrganizacion);
        btnConfirmarSocio = findViewById(R.id.btnConfirmarSocio);

        btnConfirmarSocio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registrarSocio();
            }
        });
    }

    private void registrarSocio() {
        String nombre = nombreEditText.getText().toString().trim();
        String apellido = apellidoEditText.getText().toString().trim();
        String correo = correoEditText.getText().toString().trim();
        String contrasena = contrasenaEditText.getText().toString().trim();
        String confirmarContrasena = confirmarContrasenaEditText.getText().toString().trim();
        String nombreOrganizacion = nombreOrganizacionEditText.getText().toString().trim();

        if (nombre.isEmpty() || apellido.isEmpty() || correo.isEmpty() || contrasena.isEmpty() || confirmarContrasena.isEmpty() || nombreOrganizacion.isEmpty()) {
            Toast.makeText(this, "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }

        // Verificar que las contraseñas coincidan
        if (!contrasena.equals(confirmarContrasena)) {
            Toast.makeText(this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show();
            return;
        }

        // Validar el formato de la contraseña
        if (!validarContrasena(contrasena)) {
            Toast.makeText(this, "La contraseña debe tener al menos 8 caracteres, incluyendo números, letras y caracteres especiales", Toast.LENGTH_SHORT).show();
            return;
        }

        // Validar el formato del correo electrónico
        if (!validarCorreo(correo)) {
            Toast.makeText(this, "Ingrese un correo electrónico válido", Toast.LENGTH_SHORT).show();
            return;
        }

        // Verificar si el correo electrónico ya está registrado en otra cuenta
        if (dbHelper.existeCorreo(correo)) {
            Toast.makeText(this, "El correo electrónico ya está registrado en otra cuenta", Toast.LENGTH_SHORT).show();
            return;
        }

        // Insertar socio en la base de datos
        long resultado = dbHelper.insertarSocio(nombre, apellido, correo, contrasena, nombreOrganizacion);

        if (resultado != -1) {
            Toast.makeText(this, "Socio registrado correctamente", Toast.LENGTH_SHORT).show();
            // Aquí puedes redirigir a otra actividad o realizar alguna acción adicional
            finish(); // Finalizar la actividad actual
        } else {
            Toast.makeText(this, "Error al registrar el socio", Toast.LENGTH_SHORT).show();
        }
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
