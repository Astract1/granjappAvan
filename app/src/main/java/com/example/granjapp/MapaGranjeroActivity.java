    package com.example.granjapp;

    import android.Manifest;
    import android.content.Context;
    import android.content.Intent;
    import android.content.SharedPreferences;
    import android.content.pm.PackageManager;
    import android.location.Address;
    import android.location.Geocoder;
    import android.location.Location;
    import android.os.Bundle;
    import android.util.Log;
    import android.view.View;
    import android.widget.Toast;
    import androidx.annotation.NonNull;
    import androidx.appcompat.app.AppCompatActivity;
    import androidx.core.app.ActivityCompat;
    import com.google.android.gms.common.api.Status;
    import com.google.android.gms.location.FusedLocationProviderClient;
    import com.google.android.gms.location.LocationServices;
    import com.google.android.gms.maps.CameraUpdateFactory;
    import com.google.android.gms.maps.GoogleMap;
    import com.google.android.gms.maps.OnMapReadyCallback;
    import com.google.android.gms.maps.SupportMapFragment;
    import com.google.android.gms.maps.model.LatLng;
    import com.google.android.gms.maps.model.Marker;
    import com.google.android.gms.maps.model.MarkerOptions;
    import com.google.android.libraries.places.api.Places;
    import com.google.android.libraries.places.api.model.Place;
    import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
    import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;
    import android.widget.EditText;


    import java.io.IOException;
    import java.util.Arrays;
    import java.util.List;
    import java.util.Locale;

    public class MapaGranjeroActivity extends AppCompatActivity implements OnMapReadyCallback {

        private GoogleMap gMap;
        private AutocompleteSupportFragment autocompleteFragment;
        private Marker currentMarker;

        private String direccionObtenida;
        private String direccionActual = "";






        private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_mapa_granjero);


            // Obtener el fragmento del mapa y registrar el callback
            SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.map);
            mapFragment.getMapAsync(this);

            if (!Places.isInitialized()) {
                Places.initialize(getApplicationContext(), "APIKEY");
            }

            AutocompleteSupportFragment autocompleteFragment = (AutocompleteSupportFragment)
                    getSupportFragmentManager().findFragmentById(R.id.autocomplete_fragment);
            if (autocompleteFragment != null) {
                autocompleteFragment.setPlaceFields(Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG));
            }

            autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {

                @Override
                public void onPlaceSelected(@NonNull Place place) {
                    // Eliminar el marcador actual si existe
                    if (currentMarker != null) {
                        currentMarker.remove();
                    }

                    // Añadir un nuevo marcador en la ubicación seleccionada
                    MarkerOptions markerOptions = new MarkerOptions()
                            .position(place.getLatLng())
                            .title(place.getName());
                    currentMarker = gMap.addMarker(markerOptions);

                    // Mover la cámara del mapa a la ubicación seleccionada
                    gMap.moveCamera(CameraUpdateFactory.newLatLng(place.getLatLng()));
                    gMap.animateCamera(CameraUpdateFactory.zoomTo(15));

                    // Enviar la dirección actual a la actividad para actualizar el campo de ubicación manual
                    updateManualLocationField(place.getAddress());
                }


                @Override
                public void onError(@NonNull Status status) {
                    Toast.makeText(MapaGranjeroActivity.this, "Error al seleccionar lugar", Toast.LENGTH_SHORT).show();
                }
            });

            // Verificar y solicitar permisos de ubicación si es necesario
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_PERMISSION_REQUEST_CODE);
            }

            findViewById(R.id.botonRegresarMapa).setOnClickListener(
                    (v) -> {
                        finish();
                    }
            );

            findViewById(R.id.btnUbiacionActual).setOnClickListener(
                    (v) -> {
                        obtenerUbicacionActual();
                        updateManualLocationField(direccionObtenida);
                        Toast.makeText(this, "Ubicación agregada ", Toast.LENGTH_SHORT).show();
                    }
            );


            findViewById(R.id.btnBuscarPuntosComprador).setOnClickListener((v) -> {
                try {
                    // Verificar si autocompleteFragment es null
                    if (autocompleteFragment == null) {
                        Toast.makeText(this, "autocompleteFragment es null", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    // Crear una instancia de dbHelper
                    dbHelper dbHelperInstance = new dbHelper(this);

                    // Verificar si dbHelperInstance es null
                    if (dbHelperInstance == null) {
                        Toast.makeText(this, "dbHelperInstance es null", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    View fragmentView = autocompleteFragment.getView();

                    // Verificar si fragmentView es null
                    if (fragmentView == null) {
                        Toast.makeText(this, "fragmentView es null", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    EditText editText = fragmentView.findViewById(com.google.android.libraries.places.R.id.places_autocomplete_search_input);

                    // Verificar si editText es null
                    if (editText == null) {
                        Toast.makeText(this, "editText es null", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    // Obtener el texto actual en el campo de autocompletado
                    direccionActual = editText.getText().toString();

                    // Obtener la latitud y longitud desde la dirección ingresada
                    Geocoder geocoder = new Geocoder(this, Locale.getDefault());
                    List<Address> addresses = geocoder.getFromLocationName(direccionActual, 1);
                    if (addresses != null && !addresses.isEmpty()) {
                        double latitud = addresses.get(0).getLatitude();
                        double longitud = addresses.get(0).getLongitude();

                        // Obtener el ID del usuario desde SharedPreferences
                        int idUsuario;
                        try {
                            idUsuario = obtenerIdUsuarioDesdeSharedPreferences();
                        } catch (Exception e) {
                            Toast.makeText(this, "Error al obtener el ID del usuario", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        // Verificar si la dirección no está vacía y el ID del usuario es válido
                        if (!direccionActual.isEmpty() && idUsuario != -1) {
                            // Si no hay ningún punto de venta existente o el estado no es "Activo", guardar la nueva dirección
                            Toast.makeText(this, "Dirección guardada correctamente", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(this, CalendarioCampesino.class);
                            intent.putExtra("direccion", direccionActual);
                            intent.putExtra("latitud", latitud);
                            intent.putExtra("longitud", longitud);
                            Log.d("latitud", String.valueOf(latitud));
                            Log.d("longitud", String.valueOf(longitud));
                            Log.d("direccion", direccionActual);
                            startActivity(intent);
                        } else {
                            Toast.makeText(this, "Por favor, ingresa una dirección válida", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(this, "No se encontraron coordenadas para la dirección ingresada", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });

        }

            private void updateManualLocationField(String address) {
            // Obtener el fragmento de autocompletado por su ID
            AutocompleteSupportFragment autocompleteFragment = (AutocompleteSupportFragment)
                    getSupportFragmentManager().findFragmentById(R.id.autocomplete_fragment);

            // Obtener el EditText del fragmento de autocompletado
            View fragmentView = autocompleteFragment.getView();
            if (fragmentView != null) {
                EditText editText = fragmentView.findViewById(com.google.android.libraries.places.R.id.places_autocomplete_search_input);
                if (editText != null) {
                    // Obtener el texto actual en el campo de autocompletado
                    String currentText = editText.getText().toString();

                    // Si no hay texto o el texto actual es diferente de la dirección, establecer la dirección
                    if (currentText.isEmpty() || !currentText.equals(address)) {
                        editText.setText(address);
                    }
                }
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
            currentMarker = null;

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

        // Método para obtener la ubicación actual del dispositivo

        private String obtenerDireccionDesdeUbicacionActual(Location location) {
            Geocoder geocoder = new Geocoder(this, Locale.getDefault());
            try {
                List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                if (!addresses.isEmpty()) {
                    Address address = addresses.get(0);
                    return address.getAddressLine(0); // Devuelve la primera línea de la dirección
                } else {
                    return "No se encontraron direcciones cercanas";
                }
            } catch (IOException e) {
                e.printStackTrace();
                return "Error al obtener la dirección: " + e.getMessage();
            }
        }

        private void obtenerUbicacionActual() {
            FusedLocationProviderClient fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            fusedLocationClient.getLastLocation().addOnSuccessListener(this, location -> {
                if (location != null) {
                    // Obtener la dirección actual
                    String addressLine = obtenerDireccionDesdeUbicacionActual(location);

                    // Almacenar la dirección en una variable
                    direccionObtenida = addressLine;

                    // Eliminar el marcador actual si existe
                    if (currentMarker != null) {
                        currentMarker.remove();
                    }

                    // Mover la cámara del mapa a la ubicación actual
                    LatLng currentLocation = new LatLng(location.getLatitude(), location.getLongitude());
                    gMap.moveCamera(CameraUpdateFactory.newLatLng(currentLocation));
                    gMap.animateCamera(CameraUpdateFactory.zoomTo(15)); // Zoom a un nivel específico

                    // Añadir un marcador en la ubicación actual con la dirección
                    currentMarker = gMap.addMarker(new MarkerOptions().position(currentLocation).title("Ubicación actual").snippet(addressLine));

                    // Actualizar el campo de ubicación manual con la dirección
                    updateManualLocationField(addressLine);

                    Toast.makeText(this, "Ubicación actual: " + addressLine, Toast.LENGTH_SHORT).show();
                }
            });
        }

        private int obtenerIdUsuarioDesdeSharedPreferences() {
            SharedPreferences sharedPreferences = getSharedPreferences("miSharedPreferences", Context.MODE_PRIVATE);
            return sharedPreferences.getInt("idUsuario", -1); // Devuelve -1 si no se encuentra el ID
        }

    }
