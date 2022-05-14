package com.example.univents;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.univents.databinding.ActivityEventConfirmBinding;

public class EventConfirmActivity extends AppCompatActivity {
    private ActivityEventConfirmBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityEventConfirmBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        binding.calendarBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                Toast.makeText(view.getContext(), "Added to calendar", Toast.LENGTH_SHORT).show();
            }
        });
    }
}