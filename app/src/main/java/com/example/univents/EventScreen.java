package com.example.univents;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.univents.adapter.RecyclerViewAdapter;
import com.example.univents.databinding.ActivityEventScreenBinding;
import com.example.univents.model.Event;

import java.util.ArrayList;

public class EventScreen extends AppCompatActivity {

    private ActivityEventScreenBinding binding;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerViewAdapter adapter;
    private ArrayList<Event> events;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // calling the action bar
        ActionBar actionBar = getSupportActionBar();

        // Customize the back button
        actionBar.setHomeAsUpIndicator(R.drawable.backbtn);

        // showing the back button in action bar
        actionBar.setDisplayHomeAsUpEnabled(true);

        binding = ActivityEventScreenBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        events = new ArrayList<Event>();
        events = Event.createEventList();
        adapter = new RecyclerViewAdapter(events);
        binding.eventList.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        binding.eventList.setAdapter(adapter);
        layoutManager = new LinearLayoutManager(this);
        binding.eventList.setLayoutManager(layoutManager);
    }
    // this event will enable the back
    // function to the button on press
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}