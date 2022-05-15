package com.example.univents;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.ui.AppBarConfiguration;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.univents.databinding.ActivityProfileBinding;
import com.example.univents.databinding.FragmentProfileBinding;
import com.example.univents.model.Student;
import com.example.univents.viewmodel.StudentViewModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

public class ProfileActivity extends AppCompatActivity {

    private StudentViewModel studentViewModel;
    private ActivityProfileBinding binding;
    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private Student currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // calling the action bar
        ActionBar actionBar = getSupportActionBar();

        // Customize the back button
        actionBar.setHomeAsUpIndicator(R.drawable.backbtn);

        // showing the back button in action bar
        actionBar.setDisplayHomeAsUpEnabled(true);

        binding = ActivityProfileBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        studentViewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication()).create(StudentViewModel.class);
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();

        studentViewModel.getAllStudents().observe(this, new Observer<List<Student>>() {
            @Override
            public void onChanged(List<Student> students) {
                for (Student student : students) {
                    if (user.getEmail().equals(student.getStudentEmailId())) {
                        currentUser = student;
                        binding.firstName.setText(student.getStudentFname());
                        binding.lastName.setText(student.getStudentLname());
                        binding.emailId.setText(student.getStudentEmailId());
                        binding.phone.setText(student.getStudentPhno());
                        binding.university.setText(student.getStudentUniversity());
                    }
                }
            }
        });

        binding.deleteBtn.setOnClickListener(view1 -> {
            studentViewModel.delete(currentUser);
            mAuth.getCurrentUser().delete().addOnCompleteListener(runnable -> {
                Intent intent = new Intent(this, LogInActivity.class);
                startActivity(intent);
            });
        });
    }

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