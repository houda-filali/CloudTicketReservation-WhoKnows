package com.example.cloudticketreservationwk.controller;

import com.example.cloudticketreservationwk.R;
import com.example.cloudticketreservationwk.service.InMemoryStore;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

public class MyReservationsActivity extends AppCompatActivity implements ReservationAdapter.Listener {

    private final List<ReservationAdapter.ReservationItem> reservations = new ArrayList<>();
    private ReservationAdapter adapter;
    private View tvEmpty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_reservations);

        tvEmpty = findViewById(R.id.tvEmptyReservations);
        RecyclerView rv = findViewById(R.id.rvMyReservations);
        MaterialButton btnBack = findViewById(R.id.btnBackFromReservations);
        MaterialButton btnLogout = findViewById(R.id.btnLogout);

        if (btnBack != null) btnBack.setOnClickListener(v -> finish());
        if (btnLogout != null) btnLogout.setOnClickListener(v -> logout());

        adapter = new ReservationAdapter(reservations, this);
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setAdapter(adapter);

        reloadFromStore();
    }

    @Override
    protected void onResume() {
        super.onResume();
        reloadFromStore();
    }

    private void reloadFromStore() {
        reservations.clear();

        for (InMemoryStore.ReservationItem r : InMemoryStore.MY_RESERVATIONS) {
            String title = (r.event == null) ? "" : r.event.title;
            String date = (r.event == null) ? "" : r.event.date;

            reservations.add(new ReservationAdapter.ReservationItem(
                    title,
                    date,
                    String.valueOf(r.tickets),
                    r.status
            ));
        }

        if (adapter != null) adapter.notifyDataSetChanged();
        if (tvEmpty != null) tvEmpty.setVisibility(reservations.isEmpty() ? View.VISIBLE : View.GONE);
    }

    @Override
    public void onCancelClicked(ReservationAdapter.ReservationItem r) {
        Snackbar.make(findViewById(android.R.id.content), "tba", Snackbar.LENGTH_SHORT).show();
    }

    private void logout() {
        Intent i = new Intent(this, MainActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(i);
        finish();
    }
}