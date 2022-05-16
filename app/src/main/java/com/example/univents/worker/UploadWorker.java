package com.example.univents.worker;


import android.app.Application;
import android.content.Context;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.lifecycle.ViewModelProvider;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.example.univents.MainActivity;
import com.example.univents.dao.StudentDAO;
import com.example.univents.database.StudentDatabase;
import com.example.univents.model.Event;
import com.example.univents.model.Student;
import com.example.univents.repository.StudentRepository;
import com.example.univents.viewmodel.StudentViewModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class UploadWorker extends Worker {

    public UploadWorker(
            @NonNull Context context,
            @NonNull WorkerParameters params) {
        super(context, params);
    }

    @Override
    public Result doWork() {

        // Do the work here--in this case, upload the images.
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        String studentFname = getInputData().getString("studentFname");
        String studentLname = getInputData().getString("studentLname");
        String studentPhno = getInputData().getString("studentPhno");
        String studentEmailID = getInputData().getString("studentEmailID");
        String studentPassword = getInputData().getString("studentPassword");
        String studentUniversity = getInputData().getString("studentUniversity");
//        HashMap<String, Object> eHistory = (HashMap<String, Object>) getInputData().getKeyValueMap();
//        ArrayList<Event> eventHistory = (ArrayList<Event>) eHistory.get("eventHistory");
        Log.d("WorkerRunning", "" + studentFname);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//            studentViewModel.findByIDFuture(user.getEmail()).thenAccept(student -> {
//
//            });
//        }

        // Indicate whether the work finished successfully with the Result
        return Result.success();
    }
}

