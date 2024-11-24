package com.example.lab7;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class DetalleBusActivity extends AppCompatActivity {
    private FirebaseFirestore db;
    private FirebaseAuth auth;
    private TextView precioUnitarioTextView, precioSuscripcionTextView;
    private Button suscribirButton;
    private String busId;
    private double saldo, precioSuscripcion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_bus);

        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();

        precioUnitarioTextView = findViewById(R.id.precioUnitarioTextView);
        precioSuscripcionTextView = findViewById(R.id.precioSuscripcionTextView);
        suscribirButton = findViewById(R.id.suscribirButton);

        // Obtén el ID del bus
        busId = getIntent().getStringExtra("busId");

        // Carga detalles del bus
        db.collection("buses").document(busId).get().addOnSuccessListener(documentSnapshot -> {
            if (documentSnapshot.exists()) {
                precioSuscripcion = documentSnapshot.getDouble("precioSuscripcion");
                precioSuscripcionTextView.setText("Suscripción mensual: S/ " + precioSuscripcion);
            }
        });

        // Carga el saldo del usuario
        String userId = auth.getCurrentUser().getUid();
        db.collection("users").document(userId).get().addOnSuccessListener(documentSnapshot -> {
            if (documentSnapshot.exists()) {
                saldo = documentSnapshot.getDouble("saldo");
            }
        });

        // Maneja la suscripción
        suscribirButton.setOnClickListener(v -> {
            if (saldo >= precioSuscripcion) {
                saldo -= precioSuscripcion;

                // Actualiza el saldo del usuario
                db.collection("users").document(userId).update("saldo", saldo).addOnSuccessListener(aVoid -> {
                    Toast.makeText(this, "Suscripción realizada con éxito", Toast.LENGTH_SHORT).show();
                });
            } else {
                Toast.makeText(this, "Saldo insuficiente para suscribirse", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
