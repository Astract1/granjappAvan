package com.example.granjapp;

import android.content.Context;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.logging.Handler;

public class CargarPuntosAdapter extends  RecyclerView.Adapter<CargarPuntosAdapter.ViewHolder>{

    private List<PuntoVenta> puntosVenta;
    private LayoutInflater layoutInflater;
    private Context context;
    private double latitudActual;
    private double longitudActual;

    public CargarPuntosAdapter(List<PuntoVenta> puntosVenta, Context context, double latitudActual, double longitudActual) {
        this.puntosVenta = puntosVenta;
        this.context = context;
        this.latitudActual = latitudActual;
        this.longitudActual = longitudActual;
        layoutInflater = LayoutInflater.from(context);
    }


    public int getItemCount() {return puntosVenta.size();}


    public CargarPuntosAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.bottomsheetlyout, parent, false);
        return new ViewHolder(view);
    }


    public void onBindViewHolder(final CargarPuntosAdapter.ViewHolder holder, final int position) {
        holder.bindData(puntosVenta.get(position));
    }


    public void setItems(List<PuntoVenta> items) {
        puntosVenta = items;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView img;
        TextView direccion, horaServicio, nombre, distancia;

        ViewHolder(View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.ImagenPuntoVenta);
            direccion = itemView.findViewById(R.id.DireccionPuntoVentaC);
            horaServicio = itemView.findViewById(R.id.HorarioPuntoVentaC);
            nombre = itemView.findViewById(R.id.NombrePuntoVentaC);
            distancia = itemView.findViewById(R.id.DistanciaPuntoVentaC);
        }

        void bindData(PuntoVenta puntoVenta) {
            img.setImageResource(R.drawable._986701_online_shop_store_store_icon_112278);
            direccion.setText(puntoVenta.getDireccion());
            horaServicio.setText(puntoVenta.getHoraEntrada() + " - " + puntoVenta.getHoraSalida());
            nombre.setText(puntoVenta.getNombre());
            double distanciaEnKm = calcularDistanciaEnKm(latitudActual, longitudActual, puntoVenta.getLatitud(), puntoVenta.getLongitud());
            distancia.setText(String.format("%.2f km", distanciaEnKm));

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Obtener el color de fondo actual
                    int colorFondoOriginal = itemView.getSolidColor();

                    // Cambiar el color de fondo cuando se hace clic
                    itemView.setBackgroundColor(ContextCompat.getColor(itemView.getContext(), R.color.verde));

                    // Usar el Handler del hilo principal de la interfaz de usuario para manejar el cambio de color
                    new android.os.Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            itemView.setBackgroundColor(colorFondoOriginal);
                        }
                    }, 200); // Cambia este valor si deseas ajustar la duración del cambio de color

                    // Tu código adicional aquí, como mover la cámara del mapa
                    double latitud = puntoVenta.getLatitud();
                    double longitud = puntoVenta.getLongitud();
                    ((CargarPuntosVentas) context).moverCamaraMapa(latitud, longitud);
                }
            });

        }




        private double calcularDistanciaEnKm(double latitudPunto1, double longitudPunto1, double latitudPunto2, double longitudPunto2) {
            // Radio de la Tierra en kilómetros
            final double RADIO_TIERRA_KM = 6371.0;

            // Convertir las coordenadas de grados a radianes
            double latitudPunto1Rad = Math.toRadians(latitudPunto1);
            double longitudPunto1Rad = Math.toRadians(longitudPunto1);
            double latitudPunto2Rad = Math.toRadians(latitudPunto2);
            double longitudPunto2Rad = Math.toRadians(longitudPunto2);

            // Calcular la diferencia de latitud y longitud
            double diferenciaLatitud = latitudPunto2Rad - latitudPunto1Rad;
            double diferenciaLongitud = longitudPunto2Rad - longitudPunto1Rad;

            // Calcular la distancia utilizando la fórmula de Haversine
            double a = Math.pow(Math.sin(diferenciaLatitud / 2), 2) +
                    Math.cos(latitudPunto1Rad) * Math.cos(latitudPunto2Rad) *
                            Math.pow(Math.sin(diferenciaLongitud / 2), 2);
            double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
            double distancia = RADIO_TIERRA_KM * c;

            return distancia;
        }


    }



}
