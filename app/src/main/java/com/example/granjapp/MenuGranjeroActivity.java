package com.example.granjapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MenuGranjeroActivity extends AppCompatActivity {

    private int idUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_granjero);

        idUsuario = obtenerIdUsuarioDesdeSharedPreferences();

        findViewById(R.id.botonRegresarCodigoPrincipalGranjero).setOnClickListener(
                (v) -> {
                    Intent intent = new Intent(this,  IniciarSesionCampesinoActivity.class);
                    startActivity(intent);
                }
        );

        findViewById(R.id.PerfilGranjeroC).setOnClickListener(
                (v) -> {
                    Intent intent = new Intent(this, PerfilCampesinoActivity.class);
                    intent.putExtra("ID_USUARIO", idUsuario);
                    startActivity(intent);
                }
        );

        findViewById(R.id.proveedorGranjero).setOnClickListener(
                (v) -> {
                    Intent intent = new Intent(this, CargarProductos.class);
                    intent.putExtra("ID_USUARIO", idUsuario);
                    startActivity(intent);
                }
        );
    }

    public void abrirMapaGranjero(View view) {
        Intent intent = new Intent(this, MapaGranjeroActivity.class);

        // Agregar el ID del usuario como extra en el intent
        intent.putExtra("ID_USUARIO", idUsuario);


        startActivity(intent);
    }


    public void abrirHistorialPuntosdeVenta(View view) {
        Intent intent = new Intent(this, HistorialPuntosdeVenta.class);

        // Agregar el ID del usuario como extra en el intent
        intent.putExtra("ID_USUARIO", idUsuario);

        startActivity(intent);
    }

    // Método para obtener el ID del usuario desde SharedPreferences
    private int obtenerIdUsuarioDesdeSharedPreferences() {
        SharedPreferences sharedPreferences = getSharedPreferences("prefs", MODE_PRIVATE);
        return sharedPreferences.getInt("idUsuario", -1); // Valor predeterminado -1 si no se encuentra el ID
    }
}

