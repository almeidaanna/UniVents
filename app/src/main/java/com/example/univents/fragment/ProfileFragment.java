package com.example.univents.fragment;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.univents.MainActivity;
import com.example.univents.R;
import com.example.univents.databinding.FragmentProfileBinding;
import com.example.univents.model.Student;
import com.example.univents.viewmodel.StudentViewModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class ProfileFragment extends Fragment {

    private StudentViewModel studentViewModel;
    private FragmentProfileBinding binding;
    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private Student student;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentProfileBinding.inflate(inflater, container, false);
        studentViewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(getActivity().getApplication()).create(StudentViewModel.class);
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();

        studentViewModel.getAllStudents().observe(getViewLifecycleOwner(), new Observer<List<Student>>() {
            @Override
            public void onChanged(List<Student> students) {
                for (Student student: students){
                    if(user.getEmail().equals(student.getStudentEmailId())){
                        Toast.makeText(getContext(), student.getStudentFname(), Toast.LENGTH_SHORT).show();
                        binding.firstName.setText(student.getStudentFname());
                        binding.lastName.setText(student.getStudentLname());
                        binding.emailId.setText(student.getStudentEmailId());
                        binding.phone.setText(student.getStudentPhno());
                        binding.university.setText(student.getStudentUniversity());
                    }
                }
            }
        });

        return inflater.inflate(R.layout.fragment_profile, container, false);
    }
}
