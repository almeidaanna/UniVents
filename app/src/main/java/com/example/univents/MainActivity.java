package com.example.univents;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;
import androidx.work.WorkRequest;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.univents.databinding.ActivityEventScreenBinding;
import com.example.univents.databinding.ActivityMainBinding;
import com.example.univents.model.Student;
import com.example.univents.viewmodel.StudentViewModel;
import com.example.univents.worker.UploadWorker;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.io.Serializable;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private AppBarConfiguration mAppBarConfiguration;
    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private StudentViewModel studentViewModel;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();

        if (user == null) {
            Intent intent = new Intent(this, LogInActivity.class);
            startActivity(intent);
        }

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        setSupportActionBar(binding.appBarEventScreen.toolbar);

        if (user != null) {
            View navHeaderView = binding.navView.getHeaderView(0);
            Menu navMenu = binding.navView.getMenu();
            navMenu.getItem(1).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem menuItem) {
                    Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
                    startActivity(intent);
                    return false;
                }
            });
            navMenu.getItem(4).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem menuItem) {
                    mAuth.signOut();
                    Intent intent = new Intent(MainActivity.this, LogInActivity.class);
                    startActivity(intent);
                    return false;
                }
            });
            TextView displayName = navHeaderView.findViewById(R.id.displayName);
            TextView userEmail = navHeaderView.findViewById(R.id.userEmail);
            displayName.setText(user.getEmail()); // need to fetch from firebase db and replace
            userEmail.setText(user.getEmail());

            studentViewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication()).create(StudentViewModel.class);
            studentViewModel.findByIDFuture(user.getEmail()).thenAccept(student -> {
                Toast.makeText(this, "please remove me", Toast.LENGTH_SHORT).show();
                @SuppressLint("RestrictedApi") Data studentData = new Data.Builder()
                        .putString("studentFname", student.getStudentFname())
                        .putString("studentLname", student.getStudentLname())
                        .putString("studentPhno", student.getStudentPhno())
                        .putString("studentEmailID", student.getStudentEmailId())
                        .putString("studentPassword", student.getStudentPassword())
                        .putString("studentUniversity", student.getStudentUniversity())
                        .build();
                // .put("eventHistory", student.getEventHistory())
                PeriodicWorkRequest periodicUploadWorkRequest =
                        new PeriodicWorkRequest.Builder(UploadWorker.class, 900000 , TimeUnit.MILLISECONDS)
                                .setInputData(new Data.Builder()
                                        .putAll(studentData)
                                        .build())
                                .build();

                WorkRequest uploadWorkRequest =
                        new OneTimeWorkRequest.Builder(UploadWorker.class)
                                .setInputData(new Data.Builder()
                                        .putAll(studentData)
                                        .build())
                                .build();
                WorkManager
                        .getInstance(this)
                        .enqueue(periodicUploadWorkRequest);
            });
        }

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.imageView,
                R.id.displayName,
                R.id.userEmail,
                R.id.navHome,
                R.id.navHistory,
                R.id.navHelp,
                R.id.navLogout,
                R.id.navProfile)
                .setOpenableLayout(binding.drawerLayout)
                .build();
        FragmentManager fragmentManager = getSupportFragmentManager();
        NavHostFragment navHostFragment = (NavHostFragment) fragmentManager.findFragmentById((R.id.nav_host_fragment_content_event_screen));

        NavController navController = navHostFragment.getNavController();
        NavigationUI.setupWithNavController(binding.navView, navController);
        NavigationUI.setupWithNavController(binding.appBarEventScreen.toolbar, navController, mAppBarConfiguration);
    }
}