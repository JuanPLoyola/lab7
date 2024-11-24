package com.example.lab7;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class BusAdapter extends RecyclerView.Adapter<BusAdapter.BusViewHolder> {
    private List<Bus> busList;
    private OnBusClickListener onBusClickListener;

    public interface OnBusClickListener {
        void onBusClick(Bus bus);
    }

    public BusAdapter(List<Bus> busList, OnBusClickListener onBusClickListener) {
        this.busList = busList;
        this.onBusClickListener = onBusClickListener;
    }

    @NonNull
    @Override
    public BusViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_bus, parent, false);
        return new BusViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BusViewHolder holder, int position) {
        Bus bus = busList.get(position);
        holder.bind(bus);
    }

    @Override
    public int getItemCount() {
        return busList.size();
    }

    class BusViewHolder extends RecyclerView.ViewHolder {
        private TextView busNameTextView;
        private ImageView busImageView;
        private Button detailsButton;

        public BusViewHolder(@NonNull View itemView) {
            super(itemView);
            busNameTextView = itemView.findViewById(R.id.busNameTextView);
            busImageView = itemView.findViewById(R.id.busImageView);
            detailsButton = itemView.findViewById(R.id.detailsButton);
        }

        public void bind(final Bus bus) {
            busNameTextView.setText(bus.getName());
            // Aquí puedes cargar la imagen con Glide o Picasso si tienes URLs
            // Glide.with(itemView.getContext()).load(bus.getImageUrl()).into(busImageView);

            detailsButton.setOnClickListener(v -> {
                if (onBusClickListener != null) {
                    onBusClickListener.onBusClick(bus);
                }
            });
        }
    }
}
