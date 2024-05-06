package com.example.granjapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;


public class PuntosVentaAdapter extends RecyclerView.Adapter<PuntosVentaAdapter.ViewHolder> {

    private List<PuntoVenta> puntosVenta;
    private LayoutInflater layoutInflater;
    private Context context;

    public PuntosVentaAdapter(List<PuntoVenta> puntosVenta, Context context) {
        this.puntosVenta = puntosVenta;
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
    }

    public int getItemCount() {
        return puntosVenta.size();
    }

    @Override

    public PuntosVentaAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.list_element, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final PuntosVentaAdapter.ViewHolder holder, final int position) {
        holder.bindData(puntosVenta.get(position));
    }

    public void setItems(List<PuntoVenta> items) {
        puntosVenta = items;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView img;
        TextView direccion, horaEntrada, horaSalida, fecha, estado;

        ViewHolder(View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.imagenHistorial);
            direccion = itemView.findViewById(R.id.DireccionPuntoG);
            horaEntrada = itemView.findViewById(R.id.HoraIncioG);
            horaSalida = itemView.findViewById(R.id.HoraFinG);
            fecha = itemView.findViewById(R.id.fechaPuntoG);
            estado = itemView.findViewById(R.id.estadoPuntoG);
        }

        void bindData(final PuntoVenta item) {
            img.setImageResource(R.drawable.update_fill0_wght400_grad0_opsz24);
            direccion.setText(item.getDireccion());
            horaEntrada.setText(item.getHoraEntrada() + " Hora de Inicio");
            horaSalida.setText(item.getHoraSalida() + " Hora de Salida");
            fecha.setText(item.getFecha());
            if (item.getEstado().equals("true")) {
                estado.setText("Activo");
                estado.setTextColor(ContextCompat.getColor(context, R.color.verde));
            } else {
                estado.setText("Inactivo");
                estado.setTextColor(ContextCompat.getColor(context, R.color.rojo));
            }
        }

    }

}











