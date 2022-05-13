package com.example.univents;

import android.os.Bundle;
import android.view.View;
import android.view.Menu;

import com.example.univents.adapter.RecyclerViewAdapter;
import com.example.univents.databinding.ActivityMainBinding;
import com.example.univents.databinding.FragmentHomeBinding;
import com.example.univents.model.Event;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.univents.databinding.ActivityEventScreenBinding;

import java.util.ArrayList;

public class EventScreen extends AppCompatActivity {

    private ActivityEventScreenBinding binding;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerViewAdapter adapter;
    private ArrayList<Event> events;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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
}