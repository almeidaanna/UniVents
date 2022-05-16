package com.example.univents;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.univents.databinding.ActivityEventConfirmBinding;
import com.google.type.Date;
import com.google.type.DateTime;
import com.mapbox.geojson.Point;
import com.mapbox.maps.CameraOptions;
import com.mapbox.maps.MapView;
import com.mapbox.maps.Style;
import com.mapbox.maps.ViewAnnotationOptions;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Locale;

public class EventConfirmActivity extends AppCompatActivity {
    private ActivityEventConfirmBinding binding;
    private MapView mapView;

    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // calling the action bar
        ActionBar actionBar = getSupportActionBar();

        // Customize the back button
        actionBar.setHomeAsUpIndicator(R.drawable.backbtn);

        // showing the back button in action bar
        actionBar.setDisplayHomeAsUpEnabled(true);

        binding = ActivityEventConfirmBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        Intent intent = getIntent();
        String eName = intent.getStringExtra("eName");
        String eDate = intent.getStringExtra("eDate");
        String eDetail = intent.getStringExtra("eDetail");
        String eCategory = intent.getStringExtra("eCategory");
        String eId = intent.getStringExtra("eId");
        String eTime = intent.getStringExtra("eTime");

        binding.eventName.setText(eName);
        //from event db get lat and log
        final Point point = Point.fromLngLat(intent.getDoubleExtra("lng", 145.045837), intent.getDoubleExtra("lat", -37.876823));
        mapView = findViewById(R.id.mapView);

        CameraOptions cameraPosition = new CameraOptions.Builder()
                .zoom(13.0)
                .center(point)
                .build();
        mapView.getViewAnnotationManager().addViewAnnotation(R.layout.marker, new ViewAnnotationOptions.Builder().geometry(point).build());
        mapView.getMapboxMap().loadStyleUri(Style.MAPBOX_STREETS);
        mapView.getMapboxMap().setCamera(cameraPosition);
        binding.calendarBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String[] date = eDate.split("/");
                String[] time = eTime.split(" ");
                String[] aTime = time[0].split(":");

                if(time[1].toLowerCase().equals("pm")) {
                    int newHour = (Integer.parseInt(aTime[0]) + 12);
                    aTime[0] = newHour + "";
                }

                Calendar beginTime = Calendar.getInstance();
                beginTime.set(Integer.parseInt(date[2]), Integer.parseInt(date[1]), Integer.parseInt(date[0]), Integer.parseInt(aTime[0]), Integer.parseInt(aTime[1]));

                Calendar endTime = Calendar.getInstance();
                endTime.set(Integer.parseInt(date[2]), Integer.parseInt(date[1]), Integer.parseInt(date[0]), Integer.parseInt(aTime[0]) + 1, Integer.parseInt(aTime[1]));

                Intent i = new Intent(Intent.ACTION_EDIT);
                i.setType("vnd.android.cursor.item/event");
                i.putExtra("beginTime", beginTime.getTimeInMillis());
                i.putExtra("allDay", false);
                i.putExtra("endTime", endTime.getTimeInMillis());
                i.putExtra("title", eName + "-" + eDetail + " (" + eCategory + ")");
                i.putExtra("description", eDetail);
                startActivity(i);
                Toast.makeText(view.getContext(), "Added to calendar", Toast.LENGTH_SHORT).show();
            }
        });
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