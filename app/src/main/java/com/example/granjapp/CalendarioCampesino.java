package com.example.granjapp;

import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CalendarView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class CalendarioCampesino extends AppCompatActivity {

    private CalendarView calendarView;

    TextView HorarioSalida;
    TextView HorarioEntrada;
    private String direccion;
    private double latitud;
    private double longitud;
    private Calendar selectedCalendar;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendario_campesino);

        Intent intent = getIntent();
        direccion = intent.getStringExtra("direccion");
        latitud = intent.getDoubleExtra("latitud", 0.0);
         longitud = intent.getDoubleExtra("longitud", 0.0);
        Log.d("DEBUG", "Dirección: " + direccion);
        Log.d("DEBUG", "Latitud: " + latitud);
        Log.d("DEBUG", "Longitud: " + longitud);


        EdgeToEdge.enable(this);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        HorarioSalida = findViewById(R.id.HoraCampesinosSalida);
        HorarioEntrada = findViewById(R.id.HoraCampesinos);

        // Referenciar los componentes de la interfaz
        calendarView = findViewById(R.id.calendarView);
        selectedCalendar = Calendar.getInstance();

        // Establecer la fecha mínima como la fecha actual
        calendarView.setMinDate(System.currentTimeMillis());

        Calendar maxDateCalendar = Calendar.getInstance();
        maxDateCalendar.add(Calendar.YEAR, 1);
        calendarView.setMaxDate(maxDateCalendar.getTimeInMillis());

        // Manejar el evento de selección de fecha
        calendarView.setOnDateChangeListener((view, year, month, dayOfMonth) -> selectedCalendar.set(year, month, dayOfMonth));

        findViewById(R.id.btnConfirmardiayhora).setOnClickListener(v -> {
            mostrarFechaYHora();
        });
    }

    public void mostrarFechaYHora() {
        // Obtener la fecha seleccionada del calendario
        long selectedDateMillis = selectedCalendar.getTimeInMillis();
        Date selectedDate = new Date(selectedDateMillis);

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        String fechaSeleccionada = dateFormat.format(selectedDate);

        String horaEntrada = HorarioEntrada.getText().toString();
        String horaSalida = HorarioSalida.getText().toString();

        // Verificar que la hora de entrada no sea menor que la hora de salida
        try {
            SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm a", Locale.getDefault());
            Date horaEntradaDate = timeFormat.parse(horaEntrada);
            Date horaSalidaDate = timeFormat.parse(horaSalida);

            Calendar entradaCalendar = Calendar.getInstance();
            Calendar salidaCalendar = Calendar.getInstance();
            entradaCalendar.setTime(horaEntradaDate);
            salidaCalendar.setTime(horaSalidaDate);


            if (entradaCalendar.before(salidaCalendar)) {
                dbHelper dbHelperInstance = new dbHelper(CalendarioCampesino.this);
                // Obtener el ID del usuario desde SharedPreferences
                int idUsuario = obtenerIdUsuarioDesdeSharedPreferences();

                long idTabla = dbHelperInstance.obtenerUltimoIdInsertado(idUsuario);
                // Obtener el ID de la tabla
                String estadoPuntoVenta;
                try {
                    estadoPuntoVenta = dbHelperInstance.obtenerEstadoPuntoVenta(idUsuario);
                } catch (Exception e) {
                    Toast.makeText(this, "Error al verificar el estado del punto de venta", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Verificar si la entrada ya existe
                if (estadoPuntoVenta != null && estadoPuntoVenta.equals("true")) {
                    // Si existe, actualizar el estado de la entrada existente a 'false'
                    dbHelperInstance.actualizarEstadoEntrada(idUsuario, idTabla, "false");
                }
                // Guardar la nueva entrada con el estado 'true'
                guardarEntrada(fechaSeleccionada, horaEntrada, horaSalida, direccion, "true", latitud, longitud);

                Intent newIntent = new Intent(CalendarioCampesino.this, MenuGranjeroActivity.class);
                startActivity(newIntent);
            } else {
                Toast.makeText(this, "La hora de entrada debe ser anterior a la hora de salida", Toast.LENGTH_SHORT).show();
            }
        } catch (ParseException e) {
            e.printStackTrace();
            Toast.makeText(this, "Error al analizar las horas", Toast.LENGTH_SHORT).show();
        }
    }




    public void HoraSalida(View view) {
        Calendar c = Calendar.getInstance();
        int hora = c.get(Calendar.HOUR_OF_DAY);
        int minuto = c.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(CalendarioCampesino.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                // Obtener el indicador AM o PM
                String amPm;
                if (hourOfDay < 12) {
                    amPm = "AM";
                } else {
                    amPm = "PM";
                    // Convertir la hora de formato de 24 horas a formato de 12 horas
                    if (hourOfDay > 12) {
                        hourOfDay -= 12;
                    }
                }
                // Actualizar el TextView con la hora y el indicador AM/PM
                HorarioSalida.setText(String.format(Locale.getDefault(), "%02d:%02d %s", hourOfDay, minute, amPm));
            }
        }, hora, minuto, false);
        timePickerDialog.show();
    }

    public void HorarioEntrada(View view) {
        Calendar c = Calendar.getInstance();
        int hora = c.get(Calendar.HOUR_OF_DAY);
        int minuto = c.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(CalendarioCampesino.this, (view1, hourOfDay, minute) -> {
            // Obtener el indicador AM o PM
            String amPm;
            if (hourOfDay < 12) {
                amPm = "AM";
            } else {
                amPm = "PM";
                // Convertir la hora de formato de 24 horas a formato de 12 horas
                if (hourOfDay > 12) {
                    hourOfDay -= 12;
                }
            }
            // Actualizar el TextView con la hora y el indicador AM/PM
            HorarioEntrada.setText(String.format(Locale.getDefault(), "%02d:%02d %s", hourOfDay, minute, amPm));
        }, hora, minuto, false);
        timePickerDialog.show();
    }

    private void guardarEntrada(String fecha, String horaEntrada, String horaSalida, String direccion, String estado, double latitud, double longitud) {
        dbHelper dbHelperInstance = null;
        try {
            dbHelperInstance = new dbHelper(this);
            int idUsuario = obtenerIdUsuarioDesdeSharedPreferences();
            if (idUsuario == -1) {
                Toast.makeText(this, "Error al obtener el ID del usuario", Toast.LENGTH_SHORT).show();
                return;
            }

            String estadoAnterior = dbHelperInstance.obtenerEstadoPuntoVenta(idUsuario);
            Log.d("DEBUG", "Estado anterior: " + estadoAnterior);
            Log.d("DEBUG", fecha + " " + horaEntrada + " " + horaSalida + " " + direccion + " " + estado);
            long idTabla = dbHelperInstance.obtenerUltimoIdInsertado(idUsuario);

            if (estadoAnterior != null && estadoAnterior.equals("true")) {
                boolean actualizacionExitosa = dbHelperInstance.actualizarEstadoEntrada(idUsuario,idTabla, "false");
                if (actualizacionExitosa) {
                    Log.d("DEBUG", "Actualización exitosa");
                } else {
                    Log.d("DEBUG", "Error al actualizar la entrada");
                }
            }
            Log.d("DEBUG", "Insertando nueva entrada");
            Log.d("DEBUG", "Latitud: " + latitud);
            Log.d("DEBUG", "Longitud: " + longitud);
            long resultado = dbHelperInstance.insertarEntrada(idUsuario, direccion, horaEntrada, horaSalida, fecha, estado, latitud, longitud);
            Log.d("DEBUG", "Resultado de la inserción: " + resultado);
            if (resultado != -1) {
                Toast.makeText(this, "Entrada guardada correctamente", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Error al guardar la entrada", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            Log.e("ERROR", "Error al guardar la entrada: ", e);
            Toast.makeText(this, "Error al guardar la entrada: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        } finally {
            if (dbHelperInstance != null) {
                dbHelperInstance.close();
            }
        }
    }


    private int obtenerIdUsuarioDesdeSharedPreferences() {
        SharedPreferences sharedPreferences = getSharedPreferences("miSharedPreferences", Context.MODE_PRIVATE);
        return sharedPreferences.getInt("idUsuario", -1); // Devuelve -1 si no se encuentra el ID
    }

}