package com.example.univents.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.univents.databinding.RvEventLayoutBinding;
import com.example.univents.model.Event;

import java.util.ArrayList;

public class HistoryRVAdapter extends RecyclerView.Adapter <HistoryRVAdapter.ViewHolder>{
    private static ArrayList<Event> eventList;

    public HistoryRVAdapter(ArrayList<Event> listOfEvents) {
        eventList = listOfEvents;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RvEventLayoutBinding binding = RvEventLayoutBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        final Event event = eventList.get(position);
        viewHolder.binding.eventName.setText(event.getEventName());
        viewHolder.binding.eventDate.setText(event.getEventDate());
        viewHolder.binding.eventDay.setText(event.getEventDay());
        viewHolder.binding.eventDetails.setText(event.getEventDetail());
    }

    @Override
    public int getItemCount() {
        return eventList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private RvEventLayoutBinding binding;
        private final Context context;

        public ViewHolder(RvEventLayoutBinding binding){
            super(binding.getRoot());
            View view = binding.getRoot();
            context = view.getContext();
            this.binding = binding;
            binding.eventCard.setClickable(false);
        }
    }
}