package com.example.granjapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class CargarProductos extends AppCompatActivity {

    private EditText nombreProductoEditText, precioProductoEditText, cantidadProductoEditText, descripcionProductoEditText;
    private ImageView imagenProductoImageView;
    private Button tomarFotoButton, subirFotoButton, cargarProductoButton;

    private Uri imageUri;

    static final int REQUEST_PERMISSIONS = 100;
    static final int REQUEST_IMAGE_CAPTURE = 101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cargar_productos);

        // Obtener referencias a los elementos de la interfaz de usuario
        nombreProductoEditText = findViewById(R.id.nombreProducto);
        precioProductoEditText = findViewById(R.id.precioProducto);
        cantidadProductoEditText = findViewById(R.id.cantidadProducto);
        descripcionProductoEditText = findViewById(R.id.descripcionProducto);
        imagenProductoImageView = findViewById(R.id.imagenProducto);
        tomarFotoButton = findViewById(R.id.btnTomarFotoProducto);
        subirFotoButton = findViewById(R.id.SubirProducto);
        cargarProductoButton = findViewById(R.id.btnCargarProducto);

        // Configurar el evento de clic para el botón "Tomar Foto"
        tomarFotoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirCamara();
            }
        });

        // Configurar el evento de clic para el botón "Subir Foto"
        subirFotoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirGaleria();
            }
        });

        // Configurar el evento de clic para el botón "Cargar Producto"
        cargarProductoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Validar los valores ingresados en los campos de texto
                if (validarCampos()) {
                    // Si los campos son válidos, obtener los valores
                    String nombreProducto = nombreProductoEditText.getText().toString().trim();
                    double precioProducto = Double.parseDouble(precioProductoEditText.getText().toString().trim());
                    int cantidadProducto = Integer.parseInt(cantidadProductoEditText.getText().toString().trim());
                    String descripcionProducto = descripcionProductoEditText.getText().toString().trim();
                    // Obtener el ID del usuario desde SharedPreferences
                    int idUsuario = obtenerIdUsuarioDesdeSharedPreferences();
                    // Guardar los valores en la base de datos
                    guardarProductoEnBaseDeDatos(idUsuario, nombreProducto, precioProducto, cantidadProducto, descripcionProducto);

                    // Limpiar los campos después de guardar el producto
                    nombreProductoEditText.setText("");
                    precioProductoEditText.setText("");
                    cantidadProductoEditText.setText("");
                    descripcionProductoEditText.setText("");
                    imagenProductoImageView.setImageDrawable(null);
                }
            }
        });

        // Configurar el evento de clic para seleccionar una imagen
        imagenProductoImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirGaleria();
            }
        });
    }

    private boolean validarCampos() {
        // Validar cada campo individualmente
        if (nombreProductoEditText.getText().toString().trim().isEmpty()) {
            mostrarMensaje("Ingrese el nombre del producto");
            return false;
        }
        if (precioProductoEditText.getText().toString().trim().isEmpty()) {
            mostrarMensaje("Ingrese el precio del producto");
            return false;
        }
        if (cantidadProductoEditText.getText().toString().trim().isEmpty()) {
            mostrarMensaje("Ingrese la cantidad del producto");
            return false;
        }
        if (descripcionProductoEditText.getText().toString().trim().isEmpty()) {
            mostrarMensaje("Ingrese la descripción del producto");
            return false;
        }

        if (imageUri == null) {
            mostrarMensaje("Seleccione una imagen para el producto");
            return false;
        }

        return true;
    }

    private void mostrarMensaje(String mensaje) {
        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show();
    }

    private int obtenerIdUsuarioDesdeSharedPreferences() {
        SharedPreferences sharedPreferences = getSharedPreferences("miSharedPreferences", Context.MODE_PRIVATE);
        return sharedPreferences.getInt("idUsuario", -1); // Devuelve -1 si no se encuentra el ID
    }

    private void guardarProductoEnBaseDeDatos(int idUsuario, String nombreProducto, double precioProducto, int cantidadProducto, String descripcionProducto) {
        // Convertir la imagen a un arreglo de bytes
        byte[] imagenBytes = convertirImagenABytes(imageUri);
        // Llamar al método de la base de datos para guardar el producto
        dbHelper db = new dbHelper(this);
        db.agregarProducto(idUsuario, nombreProducto, precioProducto, cantidadProducto, descripcionProducto, imagenBytes);
        mostrarMensaje("Producto guardado correctamente");
    }

    private byte[] convertirImagenABytes(Uri imageUri) {
        try {
            InputStream inputStream = getContentResolver().openInputStream(imageUri);
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                byteArrayOutputStream.write(buffer, 0, bytesRead);
            }
            return byteArrayOutputStream.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private final ActivityResultLauncher<Void> tomarFotoLauncher = registerForActivityResult(
            new ActivityResultContracts.TakePicturePreview(), result -> {
                if (result != null) {
                    imagenProductoImageView.setImageBitmap(result);
                    // Guardar la imagen en un archivo temporal y obtener su URI
                    File outputDir = getApplicationContext().getCacheDir();
                    File outputFile = null;
                    try {
                        outputFile = File.createTempFile("tmp", ".jpg", outputDir);
                        FileOutputStream out = new FileOutputStream(outputFile);
                        result.compress(Bitmap.CompressFormat.JPEG, 100, out);
                        out.flush();
                        out.close();
                        imageUri = Uri.fromFile(outputFile);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(this, "No se pudo tomar la foto", Toast.LENGTH_SHORT).show();
                }
            }
    );

    private void abrirCamara() {
        tomarFotoLauncher.launch(null);
    }


    private void abrirGaleria() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        resultadoGaleriaLauncher.launch(intent);
    }

    private final ActivityResultLauncher<Intent> resultadoGaleriaLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK) {
                    Intent data = result.getData();
                    if (data != null) {
                        imageUri = data.getData();
                        try {
                            Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
                            imagenProductoImageView.setImageBitmap(bitmap);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
    );
}
