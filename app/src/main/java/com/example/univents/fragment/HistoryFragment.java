package com.example.univents.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.univents.R;
import com.example.univents.ReportActivity;
import com.example.univents.adapter.HistoryRVAdapter;
import com.example.univents.adapter.RecyclerViewAdapter;
import com.example.univents.databinding.ActivityEventScreenBinding;
import com.example.univents.databinding.FragmentHistoryBinding;
import com.example.univents.databinding.RvEventLayoutBinding;
import com.example.univents.model.Event;
import com.example.univents.model.Student;
import com.example.univents.viewmodel.StudentViewModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class HistoryFragment extends Fragment {

    private FragmentHistoryBinding binding;
    private RecyclerView.LayoutManager layoutManager;
    private HistoryRVAdapter adapter;
    private ArrayList<Event> events;
    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private FirebaseFirestore db;
    private StudentViewModel studentViewModel;
    private ArrayList<Event> mEventHistory;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHistoryBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        studentViewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(getActivity().getApplication()).create(StudentViewModel.class);
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        db = FirebaseFirestore.getInstance();

        events = new ArrayList<Event>();
        mEventHistory = new ArrayList<Event>();

        studentViewModel.getAllStudents().observe(getViewLifecycleOwner(), students -> {
            db.collection("students").document(user.getEmail()).get().onSuccessTask(documentSnapshot -> {
                mEventHistory = (ArrayList<Event>) documentSnapshot.get("eventHistory");
                for (Student student : students) {
                    if (student.getStudentEmailId().equals(user.getEmail())) {
                        student.setEventHistory(mEventHistory);
                        Toast.makeText(getContext(), "" + events.size(), Toast.LENGTH_SHORT).show();
                    }
                }
                return null;
            });
            for (Student student : students) {
                if (student.getStudentEmailId().equals(user.getEmail())) {
                    events.clear();
                    events.addAll(student.getEventHistory());

                    adapter = new HistoryRVAdapter(events);
                    binding.eventList.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));
                    binding.eventList.setAdapter(adapter);
                    layoutManager = new LinearLayoutManager(getContext());
                    binding.eventList.setLayoutManager(layoutManager);
                    String reward = String.valueOf(adapter.getItemCount());
                    binding.rewardText.setText(reward);
                    view.refreshDrawableState();
                }
            }
        });

        binding.reportsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), ReportActivity.class);
                startActivity(intent);
            }
        });
        // Inflate the layout for this fragment
        return view;
    }
}