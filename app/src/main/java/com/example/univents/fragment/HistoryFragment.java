package com.example.univents.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class HistoryFragment extends Fragment {

    private FragmentHistoryBinding binding;
    private RecyclerView.LayoutManager layoutManager;
    private HistoryRVAdapter adapter;
    private ArrayList<Event> events;
    private FirebaseAuth mAuth;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHistoryBinding.inflate(inflater, container, false);
        mAuth = FirebaseAuth.getInstance();
        View view = binding.getRoot();

        events = new ArrayList<Event>();
        events = Event.createEventList(); // replace with below function
//        events = Event.getUserEventHistory(mAuth.getCurrentUser().getEmail());
        adapter = new HistoryRVAdapter(events);
        binding.eventList.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));
        binding.eventList.setAdapter(adapter);
        layoutManager = new LinearLayoutManager(getContext());
        binding.eventList.setLayoutManager(layoutManager);
        String reward = String.valueOf(adapter.getItemCount());
        binding.rewardText.setText(reward);

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