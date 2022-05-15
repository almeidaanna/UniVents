package com.example.univents;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

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
import java.util.Calendar;

public class EventScreen extends AppCompatActivity {

    private ActivityEventScreenBinding binding;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerViewAdapter adapter;
    private ArrayList<Event> events;
    private DatePicker datePicker;
    private Calendar calendar;
    private TextView dateView;
    private int year, month, day;

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
        showDate(year, month+1, day);

        Intent intent = getIntent();
        String category = intent.getStringExtra("Category");
        events = new ArrayList<Event>();
        //events = Event.createEventList();
        for(Event event : Event.createEventList())
        {
            if (event.getEventCategory().equals(category))
            {
//                String[] dateSplit = event.getEventDate().split("/");
//                int dd = Integer.parseInt(dateSplit[0]);
//
//                int mm = Integer.parseInt(dateSplit[1]);
//
//                int yyyy = Integer.parseInt(dateSplit[2]);
//
//                if (dd == day && mm == month && yyyy == year) {
//                    Toast.makeText(this,dd+"/"+mm+"/"+yyyy, Toast.LENGTH_SHORT).show();
                    events.add(event);
                //}
            }
        }


        adapter = new RecyclerViewAdapter(events);
        binding.eventList.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        binding.eventList.setAdapter(adapter);
        layoutManager = new LinearLayoutManager(this);
        binding.eventList.setLayoutManager(layoutManager);
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
                }
            };

    private void showDate(int year, int month, int day) {
        dateView.setText(new StringBuilder().append(day).append("/")
                .append(month).append("/").append(year));
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