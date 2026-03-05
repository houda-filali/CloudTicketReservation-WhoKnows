package com.example.cloudticketreservationwk.controller;

import com.example.cloudticketreservationwk.R;
import com.example.cloudticketreservationwk.service.InMemoryStore;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;

public class ReservationConfirmationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation_confirmation);

        String eventId = getIntent().getStringExtra("EVENT_ID");
        String tickets = getIntent().getStringExtra("TICKETS");

        InMemoryStore.EventItem e = InMemoryStore.findEventById(eventId);

        TextView tvDetails = findViewById(R.id.tvConfirmDetails);
        MaterialButton btnBack = findViewById(R.id.btnBack);
        MaterialButton btnMy = findViewById(R.id.btnGoMyReservations);
        MaterialButton btnEvents = findViewById(R.id.btnGoEvents);
        MaterialButton btnLogout = findViewById(R.id.btnLogout);

        if (btnLogout != null) btnLogout.setOnClickListener(v -> logout());

        String title = (e == null) ? "Reservation" : e.title;
        String line2 = (tickets == null || tickets.isEmpty()) ? "" : ("\nTickets: " + tickets);
        tvDetails.setText(title + line2);

        btnBack.setOnClickListener(v -> finish());

        btnMy.setOnClickListener(v -> {
            startActivity(new Intent(this, MyReservationsActivity.class));
            finish();
        });

        btnEvents.setOnClickListener(v -> {
            startActivity(new Intent(this, EventListActivity.class));
            finish();
        });
    }

    private void logout() {
        Intent i = new Intent(this, MainActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(i);
        finish();
    }
}