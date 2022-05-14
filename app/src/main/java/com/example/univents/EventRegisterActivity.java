package com.example.univents;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.example.univents.databinding.ActivityEventRegisterBinding;

public class EventRegisterActivity extends AppCompatActivity {
    private ActivityEventRegisterBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // calling the action bar
        ActionBar actionBar = getSupportActionBar();

        // Customize the back button
        actionBar.setHomeAsUpIndicator(R.drawable.backbtn);

        // showing the back button in action bar
        actionBar.setDisplayHomeAsUpEnabled(true);

        binding = ActivityEventRegisterBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        binding.registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =  new Intent(getApplicationContext(), EventConfirmActivity.class);
                startActivity(intent);
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