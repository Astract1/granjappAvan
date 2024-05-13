package com.example.granjapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.List;

public class PerfilCampesinoActivity extends AppCompatActivity {

    private TextView nombreTextView;
    private TextView apellidoTextView;
    private TextView correoTextView;
    private EditText sobreMiEditText;

    private TextView nombreGranjaTextView;

    private int idUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_perfil_campesino);

        // Obtener ID de usuario desde el intent
        idUsuario = obtenerIdUsuarioDesdeSharedPreferences();

        // Inicializar vistas
        nombreTextView = findViewById(R.id.nombrePerfilCampesino);
        apellidoTextView = findViewById(R.id.ApellidoPerfilCampesino);
        correoTextView = findViewById(R.id.CorreoPerfilCampesino);
        nombreGranjaTextView = findViewById(R.id.NombreGranja);
        sobreMiEditText = findViewById(R.id.SobreMi);
        // Cargar datos del perfil del campesino
        cargarPerfilCampesino();

        CargarInformacionSobreMi();

        findViewById(R.id.btnConfirmarInformacion).setOnClickListener(
                (v) -> {
                    GuardarInformacionSobreMi();
                }
        );

        findViewById(R.id.botonRegresarPerfilCampesino).setOnClickListener(
                (v) -> {
                    finish();
                }
        );
    }

    private void cargarPerfilCampesino() {
        // Realizar la consulta a la base de datos para obtener los datos del campesino
        dbHelper db = new dbHelper(this);
        Campesino campesino = db.obtenerDatosCampesino(idUsuario);

        // Verificar si se obtuvieron los datos correctamente
        if (campesino != null) {
            // Establecer los datos en las vistas
            nombreTextView.setText(getString(R.string.nombredecuenta)+ ": " + campesino.getNombre());
            apellidoTextView.setText(getString(R.string.apellidoCrearCuenta)+ ": " +campesino.getApellido());
            correoTextView.setText(getString(R.string.correoCrearCuenta)+ ": "+ campesino.getCorreo());
            nombreGranjaTextView.setText(getString(R.string.nombreGranja)+ ": " +campesino.getNombreGranja());
        } else {
            // Manejar el caso en el que no se encuentren los datos del campesino
            Toast.makeText(this, "Error al cargar el perfil del campesino", Toast.LENGTH_SHORT).show();
        }
    }

    private void CargarInformacionSobreMi() {
        try {
            dbHelper db = new dbHelper(this);
            String sobreMi = db.ObtenerInformacionSobreMi(idUsuario);

            if (sobreMi != null) { // Verificar si se obtuvo información válida
                sobreMiEditText.setText(sobreMi);
            } else {
                Toast.makeText(this, "No se encontró información sobre mí para cargar", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) { // Manejo de excepciones
            Toast.makeText(this, "Error al cargar la información sobre mí", Toast.LENGTH_SHORT).show();
            Log.e("ERROR", "Error al cargar la información sobre mí: " + e.getMessage());
        }
    }
    private void GuardarInformacionSobreMi() {
        String sobreMi = sobreMiEditText.getText().toString().trim();

        if (!sobreMi.isEmpty()) {
            if (sobreMi.length() <= 100) {
                try {
                    dbHelper db = new dbHelper(this);
                    db.guardarInformacionSobreMi(idUsuario, sobreMi);
                    Toast.makeText(this, "Información sobre mí guardada correctamente", Toast.LENGTH_SHORT).show();
                } catch (Exception e) { // Manejo de excepciones
                    Toast.makeText(this, "Error al guardar la información sobre mí", Toast.LENGTH_SHORT).show();
                    Log.e("ERROR", "Error al guardar la información sobre mí: " + e.getMessage());
                }
            } else {
                Toast.makeText(this, "El texto es demasiado largo", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "El campo de información sobre mí está vacío", Toast.LENGTH_SHORT).show();
        }
    }



    private int obtenerIdUsuarioDesdeSharedPreferences() {
        SharedPreferences sharedPreferences = getSharedPreferences("miSharedPreferences", Context.MODE_PRIVATE);
        return sharedPreferences.getInt("idUsuario", -1); // Devuelve -1 si no se encuentra el ID
    }


}
