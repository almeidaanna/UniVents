package com.example.univents.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter <RecyclerViewAdapter.ViewHolder> {
    private static List<CourseResult> courseResults;

    public RecyclerViewAdapter(List<CourseResult> courseResults) {
        this.courseResults = courseResults;
    }

    //creates a new viewholder that is constructed with a new View, inflated from a layout
    @Override
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RvLayoutBinding binding= RvLayoutBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    // this method binds the view holder created with data that will be displayed
    @Override public void onBindViewHolder(@NonNull RecyclerViewAdapter.ViewHolder viewHolder, int position) {
        final CourseResult unit = courseResults.get(position);
        viewHolder.binding.tvRvunit.setText(unit.getUnit());
        viewHolder.binding.tvRvmark.setText((Integer.toString(unit.getMark())));
        viewHolder.binding.ivItemDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                courseResults.remove(unit);
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return courseResults.size();
    }

    public void addUnits(List<CourseResult> results) {
        courseResults = results; notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private RvLayoutBinding binding;
        public ViewHolder(RvLayoutBinding binding){
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
