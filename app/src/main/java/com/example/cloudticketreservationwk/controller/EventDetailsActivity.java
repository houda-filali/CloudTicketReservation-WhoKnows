package com.example.cloudticketreservationwk.controller;

import com.example.cloudticketreservationwk.R;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;

public class EventDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_details);

        String title = getIntent().getStringExtra("TITLE");
        String date = getIntent().getStringExtra("DATE");
        String location = getIntent().getStringExtra("LOCATION");
        String category = getIntent().getStringExtra("CATEGORY");
        String desc = getIntent().getStringExtra("DESCRIPTION");

        TextView tvTitle = findViewById(R.id.tvDetailsTitle);
        TextView tvDate = findViewById(R.id.tvDetailsDate);
        TextView tvLocation = findViewById(R.id.tvDetailsLocation);
        TextView tvCategory = findViewById(R.id.tvDetailsCategory);
        TextView tvDesc = findViewById(R.id.tvDetailsDescription);

        TextInputEditText etTickets = findViewById(R.id.etTickets);
        MaterialButton btnReserve = findViewById(R.id.btnReserve);
        MaterialButton btnBackToEvents = findViewById(R.id.btnBackToEvents);
        MaterialButton btnLogout = findViewById(R.id.btnLogout);

        if (btnLogout != null) btnLogout.setOnClickListener(v -> logout());
        if (btnBackToEvents != null) btnBackToEvents.setOnClickListener(v -> finish());

        tvTitle.setText(title == null ? "" : title);
        tvDate.setText(date == null ? "" : date);
        tvLocation.setText(location == null ? "" : location);
        tvCategory.setText(category == null ? "" : category);
        tvDesc.setText(desc == null ? "" : desc);

        if (btnReserve != null) {
            btnReserve.setOnClickListener(v ->
                    Snackbar.make(findViewById(android.R.id.content), "tba", Snackbar.LENGTH_SHORT).show()
            );
        }

        if (etTickets != null) etTickets.setEnabled(false);
    }

    private void logout() {
        Intent i = new Intent(this, MainActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(i);
        finish();
    }
}