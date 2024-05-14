package com.example.granjapp;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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

import org.imaginativeworld.whynotimagecarousel.ImageCarousel;
import org.imaginativeworld.whynotimagecarousel.model.CarouselItem;

import java.util.ArrayList;
import java.util.List;

public class VerPerfilCampesino extends AppCompatActivity {


    private TextView nombreTextView;
    private TextView apellidoTextView;
    private TextView correoTextView;
    private TextView sobreMiEditText;

    private TextView nombreGranjaTextView;

    private int idUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_ver_perfil_campesino);
        dbHelper db = new dbHelper(this);

        idUsuario = getIntent().getIntExtra("id", -1);

        nombreTextView = findViewById(R.id.nombrePerfilCampesinoC);
        apellidoTextView = findViewById(R.id.ApellidoPerfilCampesinoC);
        correoTextView = findViewById(R.id.CorreoPerfilCampesinoC);
        nombreGranjaTextView = findViewById(R.id.NombreGranjaC);
        sobreMiEditText = findViewById(R.id.SobremiC);

        List<String> rutasImagenes = db.obtenerImagenesProductos(idUsuario);
        List<String> descripciones = db.ObtenerDescripciones(idUsuario);

        List<CarouselItem> list = new ArrayList<>();
        for (int i = 0; i < rutasImagenes.size(); i++) {
            String rutaImagen = rutasImagenes.get(i);
            String descripcion = descripciones.get(i);
            list.add(new CarouselItem(rutaImagen, descripcion)); // Utiliza la ruta de la imagen y la descripción para crear el CarouselItem
        }

        ImageCarousel carousel = findViewById(R.id.carousel);
        carousel.registerLifecycle(getLifecycle());
        carousel.setData(list);



        Log.d("VerPerfilCampesino", "ID de usuario: " + idUsuario);

        // Verificar que idUsuario es válido
        if (idUsuario != -1) {
            cargarPerfilCampesino();
        } else {
            Toast.makeText(this, "ID de usuario no válido", Toast.LENGTH_SHORT).show();
        }

    }



    private void cargarPerfilCampesino() {
        // Realizar la consulta a la base de datos para obtener los datos del campesino
        dbHelper db = new dbHelper(this);
        Campesino campesino = db.obtenerDatosCampesino(idUsuario);

        // Verificar si se obtuvieron los datos correctamente
        if (campesino != null) {
            // Establecer los datos en las vistas
            nombreTextView.setText(getString(R.string.nombredecuenta) + ": " + campesino.getNombre());
            apellidoTextView.setText(getString(R.string.apellidoCrearCuenta) + ": " + campesino.getApellido());
            correoTextView.setText(getString(R.string.correoCrearCuenta) + ": " + campesino.getCorreo());
            nombreGranjaTextView.setText(getString(R.string.nombreGranja) + ": " + campesino.getNombreGranja());

            // Verificar si el campo "sobre mí" es nulo o está vacío
            String sobreMi = campesino.getSobreMi();
            if (sobreMi != null && !sobreMi.isEmpty()) {
                // Si no es nulo ni está vacío, establecerlo en el EditText
                sobreMiEditText.setText(getString(R.string.sobreMi) + ": " + sobreMi);
            } else {
                // Si es nulo o está vacío, establecer un mensaje predeterminado
                sobreMiEditText.setText(getString(R.string.sobreMi) + ": "+getString(R.string.sobreMi_default));
            }
        } else {
            // Manejar el caso en el que no se encuentren los datos del campesino
            Toast.makeText(this, "Error al cargar el perfil del campesino", Toast.LENGTH_SHORT).show();
        }
    }
}