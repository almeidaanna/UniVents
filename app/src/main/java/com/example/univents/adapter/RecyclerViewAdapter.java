package com.example.univents.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.univents.databinding.RvEventLayoutBinding;
import com.example.univents.model.Event;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter <RecyclerViewAdapter.ViewHolder> {
    private static ArrayList<Event> eventList;

    public RecyclerViewAdapter(ArrayList<Event> listOfEvents) {
        eventList = listOfEvents;
    }

    //creates a new viewholder that is constructed with a new View, inflated from a layout
    @Override
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RvEventLayoutBinding binding = RvEventLayoutBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new ViewHolder(binding);
    }

    // this method binds the view holder created with data that will be displayed
    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter.ViewHolder viewHolder, int position) {
        final Event event = eventList.get(position);
        viewHolder.binding.eventName.setText(event.getEventName());
        viewHolder.binding.eventDate.setText(event.getEventDate());
        viewHolder.binding.eventDay.setText(event.getEventDay());
        viewHolder.binding.eventDetails.setText(event.getEventDetail());
        viewHolder.binding.eventImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println(event);
                //notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return eventList.size();
    }

    public void addUnits(ArrayList<Event> events) {
        eventList = events;
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private RvEventLayoutBinding binding;
        public ViewHolder(RvEventLayoutBinding binding){
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
