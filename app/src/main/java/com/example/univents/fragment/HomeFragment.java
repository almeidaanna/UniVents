package com.example.univents.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.univents.EventScreen;
import com.example.univents.adapter.RecyclerViewAdapter;
import com.example.univents.databinding.FragmentHomeBinding;
import com.example.univents.model.Event;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    private RecyclerView.LayoutManager layoutManager;
    private FragmentHomeBinding binding;
    private RecyclerViewAdapter adapter;
    private ArrayList<Event> events;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        events = new ArrayList<Event>();
        events = Event.createEventList();
        adapter = new RecyclerViewAdapter(events);
        binding.eventList.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));
        binding.eventList.setAdapter(adapter);
        layoutManager = new LinearLayoutManager(getContext());
        binding.eventList.setLayoutManager(layoutManager);

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}