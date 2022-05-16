package com.example.univents;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.univents.databinding.ActivityEventRegisterBinding;
import com.example.univents.model.Event;
import com.example.univents.model.Student;
import com.example.univents.viewmodel.StudentViewModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;
import java.util.function.Consumer;

public class EventRegisterActivity extends AppCompatActivity {
    private ActivityEventRegisterBinding binding;

    private StudentViewModel studentViewModel;
    private FirebaseAuth mAuth;
    private FirebaseUser user;

    @RequiresApi(api = Build.VERSION_CODES.N)
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

        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        studentViewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(this.getApplication()).create(StudentViewModel.class);

        Bundle bundle = getIntent().getExtras();
        Event event = bundle.getParcelable("Event");
        binding.eventName.setText(event.getEventName());
        binding.dateText.setText(event.getEventDate());
        binding.timeText.setText(event.getEventTime());
        binding.dayText.setText(event.getEventDay());
        binding.detailInfo.setText(event.getEventDetail());

        studentViewModel.findByIDFuture(user.getEmail()).thenAccept(new Consumer<Student>() {
            @Override
            public void accept(Student student) {
                for (Event e : student.getEventHistory()) {
                    if (e.getEventId() == event.getEventId()) {
                        binding.registerButton.setText("Already Registered");
                        binding.registerButton.setEnabled(false);
                    }
                }
            }
        });

        binding.registerButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {

                studentViewModel.findByIDFuture(user.getEmail()).thenAccept(new Consumer<Student>() {
                    @Override
                    public void accept(Student student) {
                        student.addToHistory(event);
                        studentViewModel.update(student);
                    }
                });

                Intent intent = new Intent(getApplicationContext(), EventConfirmActivity.class);
                intent.putExtra("Event", event.getEventName());
                intent.putExtra("lat", event.getLatitude());
                intent.putExtra("lng", event.getLongitude());
                startActivity(intent); //
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