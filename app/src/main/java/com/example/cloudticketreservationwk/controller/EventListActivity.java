package com.example.cloudticketreservationwk.controller;

import com.example.cloudticketreservationwk.R;
import com.example.cloudticketreservationwk.service.InMemoryStore;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.PopupMenu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

public class EventListActivity extends AppCompatActivity implements EventAdapter.Listener {

    private final List<EventAdapter.EventItem> events = new ArrayList<>();
    private EventAdapter adapter;

    private TextInputEditText etSearch;
    private TextInputLayout tilSearch;

    private View tvEmpty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_list);

        RecyclerView rvEvents = findViewById(R.id.rvEvents);
        tvEmpty = findViewById(R.id.tvEmptyEvents);

        etSearch = findViewById(R.id.etSearch);
        tilSearch = findViewById(R.id.tilSearch);

        MaterialButton btnMyReservations = findViewById(R.id.btnMyReservations);
        MaterialButton btnLogout = findViewById(R.id.btnLogout);

        adapter = new EventAdapter(events, this);
        rvEvents.setLayoutManager(new LinearLayoutManager(this));
        rvEvents.setAdapter(adapter);

        if (btnMyReservations != null) {
            btnMyReservations.setOnClickListener(v ->
                    startActivity(new Intent(this, MyReservationsActivity.class))
            );
        }

        if (btnLogout != null) {
            btnLogout.setOnClickListener(v -> logout());
        }

        if (tilSearch != null) {
            tilSearch.setStartIconOnClickListener(v -> {
                PopupMenu menu = new PopupMenu(EventListActivity.this, v);
                menu.getMenu().add(0, 0, 0, "All");
                menu.getMenu().add(0, 1, 1, "Date");
                menu.getMenu().add(0, 2, 2, "Location");
                menu.getMenu().add(0, 3, 3, "Category");

                menu.setOnMenuItemClickListener(item -> {
                    if (item.getItemId() == 0) {
                        tilSearch.setHint("Search events");
                        if (etSearch != null) etSearch.setText("");
                        return true;
                    }
                    if (item.getItemId() == 1) {
                        tilSearch.setHint("Pick a date");
                        openMaterialDatePicker();
                        return true;
                    }
                    if (item.getItemId() == 2) {
                        tilSearch.setHint("Search location");
                        if (etSearch != null) etSearch.setText("");
                        return true;
                    }
                    if (item.getItemId() == 3) {
                        tilSearch.setHint("Search category");
                        if (etSearch != null) etSearch.setText("");
                        return true;
                    }
                    return false;
                });

                menu.show();
            });
        }

        reloadFromStore();
        if (tvEmpty != null) tvEmpty.setVisibility(events.isEmpty() ? View.VISIBLE : View.GONE);
    }

    @Override
    protected void onResume() {
        super.onResume();
        reloadFromStore();
        if (tvEmpty != null) tvEmpty.setVisibility(events.isEmpty() ? View.VISIBLE : View.GONE);
    }

    private void reloadFromStore() {
        events.clear();
        for (InMemoryStore.EventItem e : InMemoryStore.EVENTS) {
            if (!"Canceled".equals(e.status)) {
                events.add(new EventAdapter.EventItem(
                        e.id,
                        e.title,
                        e.date,
                        e.location,
                        e.category,
                        e.description
                ));
            }
        }
        if (adapter != null) adapter.notifyDataSetChanged();
    }

    private void openMaterialDatePicker() {
        if (getSupportFragmentManager().findFragmentByTag("DATE_PICKER_FILTER") != null) return;

        MaterialDatePicker<Long> picker = MaterialDatePicker.Builder.datePicker()
                .setTitleText("Select date")
                .build();

        picker.addOnPositiveButtonClickListener(selection -> {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
            sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
            if (etSearch != null) etSearch.setText(sdf.format(new Date(selection)));
        });

        picker.show(getSupportFragmentManager(), "DATE_PICKER_FILTER");
    }

    private void logout() {
        Intent i = new Intent(this, MainActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(i);
        finish();
    }

    @Override
    public void onViewClicked(EventAdapter.EventItem event) {
        Intent i = new Intent(this, EventDetailsActivity.class);
        i.putExtra("TITLE", event.title);
        i.putExtra("DATE", event.date);
        i.putExtra("LOCATION", event.location);
        i.putExtra("CATEGORY", event.category);
        i.putExtra("DESCRIPTION", event.description);
        startActivity(i);
    }
}