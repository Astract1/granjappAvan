package com.example.granjapp;


import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import java.util.List;

public class HistorialPuntosdeVenta extends AppCompatActivity {

    private RecyclerView recyclerView;
    private PuntosVentaAdapter adapter;
    private List<PuntoVenta> puntosVenta;
    private View emptyView;
    private Button cancelarPuntosVentaButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historial_puntosde_venta);

        recyclerView = findViewById(R.id.recyclerHistorial);
        emptyView = findViewById(R.id.textNoHayElementos);
        cancelarPuntosVentaButton = findViewById(R.id.CancelarPuntosVenta);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Obtener puntos de venta desde la base de datos
        puntosVenta = obtenerPuntosDeVentaDesdeBD();

        // Verificar si la lista está vacía y mostrar la vista de elementos vacíos si es necesario
        if (puntosVenta.isEmpty()) {
            recyclerView.setVisibility(View.GONE);
            emptyView.setVisibility(View.VISIBLE);
            cancelarPuntosVentaButton.setVisibility(View.GONE); // Ocultar el botón si la lista está vacía o todos los puntos están inactivos
        } else {
            recyclerView.setVisibility(View.VISIBLE);
            emptyView.setVisibility(View.GONE);

            // Crear y configurar el adaptador
            adapter = new PuntosVentaAdapter(puntosVenta, this);

            // Configurar el RecyclerView con el adaptador
            recyclerView.setAdapter(adapter);

            // Agregar un OnClickListener al botón de cancelar puntos de venta
            cancelarPuntosVentaButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Obtener el ID del usuario
                    int idUsuario = obtenerIdUsuarioDesdeSharedPreferences();

                    // Verificar si todos los puntos de venta asociados al usuario están inactivos
                    if (!todosLosPuntosDeVentaEstanInactivos()) {
                        // Obtener el ID del punto de venta activo
                        long idPuntoVentaActivo = dbHelper.obtenerIdPuntoVentaActivo(idUsuario);

                        // Verificar si se encontró un punto de venta activo
                        if (idPuntoVentaActivo != -1) {
                            // Actualizar el estado del punto de venta a inactivo
                            dbHelper.actualizarEstadoEntrada(idUsuario, idPuntoVentaActivo, "false");
                            puntosVenta = obtenerPuntosDeVentaDesdeBD();
                            adapter.setItems(puntosVenta);
                            adapter.notifyDataSetChanged();

                            // Mostrar un mensaje de éxito o realizar otras acciones necesarias
                            Toast.makeText(getApplicationContext(), "Punto de venta cancelado correctamente", Toast.LENGTH_SHORT).show();
                        } else {
                            // Mostrar un mensaje de error si no se encontró un punto de venta activo
                            Toast.makeText(getApplicationContext(), "No hay puntos de venta activos para cancelar", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        // Mostrar un mensaje de error si todos los puntos de venta ya están inactivos
                        Toast.makeText(getApplicationContext(), "No hay puntos de venta activos para cancelar", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

        findViewById(R.id.botonRegrasarPuntosVentasH).setOnClickListener(
                (v) -> {
                    finish();
                }
        );
    }

    private dbHelper dbHelper = new dbHelper(this);

    private List<PuntoVenta> obtenerPuntosDeVentaDesdeBD() {
        int idUsuario = obtenerIdUsuarioDesdeSharedPreferences();
        return dbHelper.obtenerTodosLosPuntosVenta(idUsuario, false);
    }

    private int obtenerIdUsuarioDesdeSharedPreferences() {
        SharedPreferences sharedPreferences = getSharedPreferences("miSharedPreferences", Context.MODE_PRIVATE);
        return sharedPreferences.getInt("idUsuario", -1); // Devuelve -1 si no se encuentra el ID
    }

    // Método para verificar si todos los puntos de venta están inactivos
    private boolean todosLosPuntosDeVentaEstanInactivos() {
        // Iterar sobre la lista de puntos de venta y verificar si todos están inactivos
        for (PuntoVenta puntoVenta : puntosVenta) {
            if (!puntoVenta.getEstado().equals("false")) {
                return false;
            }
        }
        return true;
    }
}