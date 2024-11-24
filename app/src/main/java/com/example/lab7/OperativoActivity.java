package com.example.lab7;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class OperativoActivity extends AppCompatActivity {
    private FirebaseFirestore db;
    private FirebaseAuth auth;
    private TextView saldoTextView;
    private RecyclerView busRecyclerView;
    private BusAdapter busAdapter; // Adaptador para mostrar las líneas de buses
    private List<Bus> busList = new ArrayList<>(); // Lista de buses

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_operativo);

        // Inicializa Firebase
        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();

        saldoTextView = findViewById(R.id.saldoTextView);
        busRecyclerView = findViewById(R.id.busRecyclerView);

        // Configura RecyclerView
        busRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        busAdapter = new BusAdapter(busList, this::onBusClick);
        busRecyclerView.setAdapter(busAdapter);

        // Carga saldo del usuario
        String userId = auth.getCurrentUser().getUid();
        db.collection("users").document(userId).get().addOnSuccessListener(documentSnapshot -> {
            if (documentSnapshot.exists()) {
                double saldo = documentSnapshot.getDouble("saldo");
                saldoTextView.setText("Saldo: S/ " + saldo);
            } else {
                Toast.makeText(this, "No se pudo cargar el saldo", Toast.LENGTH_SHORT).show();
            }
        });

        // Carga las líneas de buses
        db.collection("buses").get().addOnSuccessListener(queryDocumentSnapshots -> {
            busList.clear();
            busList.addAll(queryDocumentSnapshots.toObjects(Bus.class));
            busAdapter.notifyDataSetChanged();
        });
    }

    private void onBusClick(Bus bus) {
        // Navega a la vista de detalles del bus
        Intent intent = new Intent(this, DetalleBusActivity.class);
        intent.putExtra("busId", bus.getId());
        startActivity(intent);
    }
}
