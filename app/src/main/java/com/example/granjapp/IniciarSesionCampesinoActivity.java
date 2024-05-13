package com.example.granjapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import androidx.appcompat.widget.AppCompatButton;
import android.widget.Toast;

public class IniciarSesionCampesinoActivity extends AppCompatActivity {

    private EditText editTextCorreoCampesino;
    private EditText editTextContraseñaCampesino;
    private static final String PREFS_NAME = "UsuarioPrefs";
    private static final String KEY_USUARIO_ID = "usuario_id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_iniciar_sesion_campesino);

        // Inicializar EditText
        editTextCorreoCampesino = findViewById(R.id.editTextCorreoCampesino);
        editTextContraseñaCampesino = findViewById(R.id.editTextContraseñaCampesino);

        // Obtener referencia al botón de iniciar sesión
        AppCompatButton btnIniciarSesion = findViewById(R.id.btnIngresarCampesino);

        // Configurar OnClickListener para el botón de iniciar sesión
        btnIniciarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iniciarSesionCampesino();
            }
        });

        findViewById(R.id.btnvolverIniciarSesion).setOnClickListener( (v) -> {
            Intent intent = new Intent(this, SeleccionarRolInicioDeSesionActivity.class);
            startActivity(intent);
        });
    }

    private void guardarIdUsuarioEnSharedPreferences(int idUsuario) {
        // Obtener una referencia al SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("miSharedPreferences", Context.MODE_PRIVATE);

        // Editar el SharedPreferences para almacenar el ID del usuario
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("idUsuario", idUsuario);
        editor.apply();
    }


    private void iniciarSesionCampesino() {
        // Obtener los valores de correo y contraseña ingresados por el usuario
        String correo = editTextCorreoCampesino.getText().toString().trim().toLowerCase();
        String contraseña = editTextContraseñaCampesino.getText().toString().trim();




        // Validar credenciales con la base de datos
        dbHelper dbHelper = new dbHelper(this);
        boolean credencialesValidas = dbHelper.validarCredencialesCampesino(correo, contraseña);


        Log.d("DEBUG", "Credenciales válidas: " + credencialesValidas);
        if (credencialesValidas) {
            // Obtener el ID del usuario basado en su correo electrónico
            int idUsuario = dbHelper.obtenerIdUsuarioPorCorreo(correo);

            // Si el ID es válido (diferente de -1), puedes usarlo para realizar otras operaciones
            if (idUsuario != -1) {
                // Imprimir el ID obtenido antes de guardarlo en SharedPreferences
                Log.d("DEBUG", "ID de usuario obtenido: " + idUsuario);

                // Guardar el ID del usuario en SharedPreferences
                guardarIdUsuarioEnSharedPreferences(idUsuario);

                // Redirigir a la siguiente actividad
                Intent intent = new Intent(IniciarSesionCampesinoActivity.this, MenuGranjeroActivity.class);
                startActivity(intent);
                Toast.makeText(this, "Inicio de Sesión Correcto", Toast.LENGTH_SHORT).show();
            }
        }
        else {
            // Mostrar mensaje de error si las credenciales son inválidas
            Toast.makeText(this, "Correo o contraseña incorrectos", Toast.LENGTH_SHORT).show();

        }
    }
}
