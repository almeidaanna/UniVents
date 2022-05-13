package com.example.univents;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

public class LogInActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
    }

    @Override
    public void onBackPressed() {
        //add functionality to exit the app
        Toast.makeText(this, "Exit App", Toast.LENGTH_SHORT).show();
    }
}