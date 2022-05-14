package com.example.univents;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.univents.databinding.ActivityEventConfirmBinding;
import com.mapbox.geojson.Point;
import com.mapbox.maps.CameraOptions;
import com.mapbox.maps.MapView;
import com.mapbox.maps.Style;

public class EventConfirmActivity extends AppCompatActivity {
    private ActivityEventConfirmBinding binding;
    private MapView mapView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityEventConfirmBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        final Point point = Point.fromLngLat(145.045837, -37.876823 );
        mapView = findViewById(R.id.mapView);
        CameraOptions cameraPosition = new CameraOptions.Builder()
                .zoom(13.0)
                .center(point)
                .build();
        mapView.getMapboxMap().loadStyleUri(Style.MAPBOX_STREETS);
        mapView.getMapboxMap().setCamera(cameraPosition);
        binding.calendarBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                Toast.makeText(view.getContext(), "Added to calendar", Toast.LENGTH_SHORT).show();
            }
        });
    }
}