package com.example.granjapp;


import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.material.bottomsheet.BottomSheetBehavior;

import java.util.Arrays;
import java.util.List;

public class CargarPuntosVentas extends AppCompatActivity implements OnMapReadyCallback {

    private RecyclerView recyclerView;
    private CargarPuntosAdapter adapter;
    private List<PuntoVenta> puntosVenta;
    private GoogleMap gMap;
    private View emptyView;
    private View bottomSheet;
    private BottomSheetBehavior bottomSheetBehavior;
    private String direccion = "";
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cargar_puntos_ventas);

        direccion = getIntent().getStringExtra("direccion");
        double latitud = getIntent().getDoubleExtra("latitud", 0.0);
        double longitud = getIntent().getDoubleExtra("longitud", 0.0);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.mapaCargarPuntosVentas);
        mapFragment.getMapAsync(this);

        if (!Places.isInitialized()) {
            Places.initialize(getApplicationContext(), "APIKEY");
        }

        AutocompleteSupportFragment autocompleteFragment = (AutocompleteSupportFragment)
                getSupportFragmentManager().findFragmentById(R.id.autocomplete_fragment);
        if (autocompleteFragment != null) {
            autocompleteFragment.setPlaceFields(Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG));
        }




        recyclerView = findViewById(R.id.recyclerHistorialC);
        emptyView = findViewById(R.id.textNoHayElementosC);
        bottomSheet = findViewById(R.id.sheet_Bottom);
        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet);
        bottomSheetBehavior.setPeekHeight(200);
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);

        bottomSheetBehavior.addBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(View bottomSheet, int newState) {
                if(newState == BottomSheetBehavior.STATE_COLLAPSED){
                    bottomSheetBehavior.setPeekHeight(200);
                }
            }

            @Override
            public void onSlide(View bottomSheet, float slideOffset) {

            }
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        puntosVenta = obtenerDatosDeLaBaseDeDatos();

        if (puntosVenta.isEmpty()) {
            recyclerView.setVisibility(View.GONE);
            emptyView.setVisibility(View.VISIBLE);
        } else {
            recyclerView.setVisibility(View.VISIBLE);
            emptyView.setVisibility(View.GONE);
            cargarMarcadoresDePuntosDeVenta(obtenerDatosDeLaBaseDeDatos());
            adapter = new CargarPuntosAdapter( puntosVenta, this, latitud, longitud);
            recyclerView.setAdapter(adapter);
        }


    }

    public void onMapReady(@NonNull GoogleMap googleMap) {
        gMap = googleMap;

        gMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        gMap.getUiSettings().setZoomControlsEnabled(true);

        // Mostrar ubicación actual si los permisos están concedidos
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            gMap.setMyLocationEnabled(true);
        } else {
            Toast.makeText(this, "Permisos de ubicación no concedidos", Toast.LENGTH_SHORT).show();
        }
        cargarMarcadoresDePuntosDeVenta(puntosVenta);



    }

    // Método para manejar el resultado de la solicitud de permisos
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults); // Llamada al método de la clase base
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permiso concedido, actualizar el mapa para mostrar la ubicación actual
                if (gMap != null) {
                    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                        return;
                    }
                    gMap.setMyLocationEnabled(true);
                }
            } else {
                // Permiso denegado, mostrar un mensaje al usuario o deshabilitar la funcionalidad relacionada con la ubicación
                Toast.makeText(this, "Permiso de ubicación denegado", Toast.LENGTH_SHORT).show();
            }
        }
    }




    private dbHelper dbHelper = new dbHelper(this);

    private List<PuntoVenta> obtenerDatosDeLaBaseDeDatos() {
        return dbHelper.obtenerPuntosVentaActivos();
    }


    private void cargarMarcadoresDePuntosDeVenta(List<PuntoVenta> puntosVenta) {
        if (gMap != null) {
            for (PuntoVenta puntoVenta : puntosVenta) {
                LatLng latLng = new LatLng(puntoVenta.getLatitud(), puntoVenta.getLongitud());
                Log.d("Punto de venta", "Nombre: " + puntoVenta.getNombre() + ", Latitud: " + puntoVenta.getLatitud() + ", Longitud: " + puntoVenta.getLongitud());
                MarkerOptions markerOptions = new MarkerOptions().position(latLng).title(puntoVenta.getNombre());
                gMap.addMarker(markerOptions);
            }
        }
    }

    public void moverCamaraMapa(double latitud, double longitud) {
        if (gMap != null) {
            LatLng coordenadas = new LatLng(latitud, longitud);
            gMap.animateCamera(CameraUpdateFactory.newLatLngZoom(coordenadas, 15));
        }
    }
}





