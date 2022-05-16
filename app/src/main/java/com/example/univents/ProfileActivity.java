package com.example.univents;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.ui.AppBarConfiguration;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.univents.databinding.ActivityProfileBinding;
import com.example.univents.databinding.FragmentProfileBinding;
import com.example.univents.model.Event;
import com.example.univents.model.Student;
import com.example.univents.viewmodel.EventViewModel;
import com.example.univents.viewmodel.StudentViewModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class ProfileActivity extends AppCompatActivity {


    private EventViewModel eventViewModel;
    private ActivityProfileBinding binding;
    private FirebaseAuth mAuth;
    private Student currentUser;
    private FirebaseUser user;
    private FirebaseFirestore db;
    private StudentViewModel studentViewModel;

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

        binding = ActivityProfileBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        studentViewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication()).create(StudentViewModel.class);
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        db = FirebaseFirestore.getInstance();

        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        CollectionReference collectionReference = db.collection("students");


        studentViewModel.findByIDFuture(user.getEmail()).thenAccept(student -> {
            if (student == null) {
                collectionReference.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        List<DocumentSnapshot> allStudents = queryDocumentSnapshots.getDocuments();
                        db.collection("students").document(user.getEmail()).get().onSuccessTask(documentSnapshot -> {
                            Student student1 = new Student(
                                    documentSnapshot.get("studentFname").toString(),
                                    documentSnapshot.get("studentLname").toString(),
                                    documentSnapshot.get("studentPhno").toString(),
                                    documentSnapshot.get("studentEmailId").toString(),
                                    documentSnapshot.get("studentPassword").toString(),
                                    documentSnapshot.get("studentUniversity").toString()
                            );
                            if (documentSnapshot.get("eventHistory") != null) {
                                student1.setEventHistory((ArrayList<Event>) documentSnapshot.get("eventHistory"));
                            }
                            studentViewModel.insert(student1);
                            Toast.makeText(ProfileActivity.this, "Pushed Data to room", Toast.LENGTH_SHORT).show();
                            view.refreshDrawableState();
                            currentUser = student1;
                            binding.firstName.setText(student1.getStudentFname());
                            binding.lastName.setText(student1.getStudentLname());
                            binding.emailId.setText(student1.getStudentEmailId());
                            binding.phone.setText(student1.getStudentPhno());
                            binding.university.setText(student1.getStudentUniversity());
                            return null;
                        });
                    }
                });
            } else {
                currentUser = student;
                binding.firstName.setText(student.getStudentFname());
                binding.lastName.setText(student.getStudentLname());
                binding.emailId.setText(student.getStudentEmailId());
                binding.phone.setText(student.getStudentPhno());
                binding.university.setText(student.getStudentUniversity());
            }
        });

        binding.deleteBtn.setOnClickListener(view1 -> {
            mAuth.getCurrentUser().delete().addOnCompleteListener(runnable -> {
                studentViewModel.deleteAll();
                mAuth.signOut();
//                studentViewModel.delete(currentUser);
                Intent intent = new Intent(this, LogInActivity.class);
                startActivity(intent);
            });
        });

        binding.backupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                backUpStudent();

                // backupEvents();
            }
        });
    }

    private void backupEvents() {
        for (Event event : eventViewModel.getAllEvents().getValue()) {
            Toast.makeText(ProfileActivity.this, "" + studentViewModel.getAllStudents().getValue().size(), Toast.LENGTH_SHORT).show();
            db.collection("events")
                    .add(event)
                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.w(TAG, "Error adding document", e);
                        }
                    });
        }
    }

    private void backUpStudent() {
        studentViewModel.getAllStudents().observe(this, students -> {
            for (Student student : students) {
                db.collection("students")
                        .document(student.getStudentEmailId())
                        .set(student)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(ProfileActivity.this, "Backed up ID: " + student.getStudentEmailId(), Toast.LENGTH_SHORT).show();
                                Log.d(TAG, "Backed up ID: " + student.getStudentEmailId());
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.w(TAG, "Error adding document", e);
                            }
                        });
            }
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