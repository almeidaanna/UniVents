package com.example.univents;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.univents.adapter.RecyclerViewAdapter;
import com.example.univents.databinding.ActivityEventScreenBinding;
import com.example.univents.model.Event;
import com.example.univents.model.Student;
import com.example.univents.viewmodel.EventViewModel;
import com.example.univents.viewmodel.StudentViewModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.function.Consumer;

public class EventScreen extends AppCompatActivity {

    private ActivityEventScreenBinding binding;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerViewAdapter adapter;
    private ArrayList<Event> events;
    private ArrayList<Event> filteredEvents;
    private DatePicker datePicker;
    private Calendar calendar;
    private TextView dateView;
    private int year, month, day;
    private EventViewModel eventViewModel;
    private boolean addNewEvents = false;
    private FirebaseAuth mAuth;
    private FirebaseUser user;

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

        dateView = (TextView) findViewById(R.id.dateText);
        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        binding.monthYearBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setDate(view);
            }
        });

        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        //showDate(year, month+1, day);
        clearDate();
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();

        Intent intent = getIntent();
        int category = intent.getIntExtra("Category", 2);
        events = new ArrayList<Event>();
        filteredEvents = new ArrayList<Event>();
        switch (category){
            case 1:binding.homeText.setText("Cultural"); break;
            case 2:binding.homeText.setText("Religious"); break;
            case 3:binding.homeText.setText("Sport"); break;
            case 4:binding.homeText.setText("Educational"); break;
        }

        eventViewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication()).create(EventViewModel.class);
        eventViewModel.getAllEventsByCategory(category).observe(this, new Observer<List<Event>>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onChanged(List<Event> eventList) {
                if (eventList.size() == 0){
                    Toast.makeText(EventScreen.this, "Populating events.", Toast.LENGTH_SHORT).show();
                    addNewEvents = true;
                    if(addNewEvents){
                        for (Event e: Event.createEventList()) {
                            if(e.getEventCategoryID() == category){
                                eventViewModel.insert(e);
                            }
                        }
                        addNewEvents = false;
                    }
                } else if(!addNewEvents) {
//                    eventViewModel.deleteAll();
                    events.addAll(eventList);
                    events.sort((event, t1) -> event.getEventName().compareTo(t1.getEventName()));

                    adapter = new RecyclerViewAdapter(events);
                    binding.eventList.addItemDecoration(new DividerItemDecoration(EventScreen.this, LinearLayoutManager.VERTICAL));
                    binding.eventList.setAdapter(adapter);
                    layoutManager = new LinearLayoutManager(EventScreen.this);
                    binding.eventList.setLayoutManager(layoutManager);

                }
            }
        });


        binding.clearBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clearDate();
                binding.clearBtn.setVisibility(View.GONE);
                adapter = new RecyclerViewAdapter(events);
                binding.eventList.setAdapter(adapter);
                binding.eventList.refreshDrawableState();
            }
        });
    }

    @SuppressWarnings("deprecation")
    public void setDate(View view) {
        showDialog(999);
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        // TODO Auto-generated method stub
        if (id == 999) {
            return new DatePickerDialog(this,
                    myDateListener, year, month, day);
        }
        return null;
    }

    private DatePickerDialog.OnDateSetListener myDateListener = new
            DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker arg0, int arg1, int arg2, int arg3) {
                    showDate(arg1, arg2+1, arg3);
                    Toast.makeText(EventScreen.this, dateView.getText(), Toast.LENGTH_SHORT).show();
                    filteredEvents.clear();
                    binding.clearBtn.setVisibility(View.VISIBLE);
                    for(Event e : events) {
                        if (dateView.getText().equals(e.getEventDate()))
                            filteredEvents.add(e);
                    }
                    adapter = new RecyclerViewAdapter(filteredEvents);
                    binding.eventList.setAdapter(adapter);
                    binding.eventList.refreshDrawableState();
                }
            };

    private void showDate(int year, int month, int day) {
        dateView.setText(new StringBuilder().append(day).append("/")
                .append(month).append("/").append(year));
        dateView.setVisibility(View.VISIBLE);
    }

    private void clearDate() {
        dateView.setText("");
        dateView.setVisibility(View.GONE);
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