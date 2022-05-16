package com.example.univents;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.example.univents.adapter.HistoryRVAdapter;
import com.example.univents.databinding.ActivityReportBinding;
import com.example.univents.model.Event;
import com.example.univents.model.Student;
import com.example.univents.viewmodel.StudentViewModel;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LegendEntry;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.function.Consumer;

public class ReportActivity extends AppCompatActivity {

    BarChart barChart;
    PieChart pieChart;
    ArrayList<BarChart> barEntries;
    ArrayList<PieChart> pieEntries;

    private ActivityReportBinding binding;
    private RecyclerView.LayoutManager layoutManager;
    private HistoryRVAdapter adapter;
    private ArrayList<Event> events;
    private FirebaseAuth mAuth;

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

        setContentView(R.layout.activity_report);

        barChart = findViewById(R.id.barChartID);
        pieChart = findViewById(R.id.pieChartID);

        ArrayList<BarEntry> barEntries = new ArrayList<>();
        ArrayList<PieEntry> pieEntries = new ArrayList<>();

        binding = ActivityReportBinding.inflate(getLayoutInflater());
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        View view = binding.getRoot();

        events = new ArrayList<Event>();
//        events = Event.getUserEventHistory(mAuth.getCurrentUser().getEmail());
        StudentViewModel studentViewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(this.getApplication()).create(StudentViewModel.class);
        studentViewModel.findByIDFuture(user.getEmail()).thenAccept(new Consumer<Student>() {
            @Override
            public void accept(Student student) {
                events.addAll(student.getEventHistory());
                for (int i = 1; i <= Event.categoryType.size(); i++) {
                    int value = 0;
                    for (Event e : events) {
                        if (e.getEventCategoryID() == i) {
                            value++;
                        }
                    }
                    BarEntry barEntry = new BarEntry(i, value);
                    PieEntry pieEntry = new PieEntry(value, i);
                    barEntries.add(barEntry);
                    pieEntries.add(pieEntry);
                }

                // display data in graphs
                BarDataSet barDataSet = new BarDataSet(barEntries, "Event Categories");
                barDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
                barDataSet.setDrawValues(true);
                barChart.setData(new BarData(barDataSet));
                LegendEntry culturalLegend = new LegendEntry("Cultural", Legend.LegendForm.DEFAULT, Float.NaN, Float.NaN, null, ColorTemplate.COLORFUL_COLORS[0]);
                LegendEntry religiousLegend = new LegendEntry("Religious", Legend.LegendForm.DEFAULT, Float.NaN, Float.NaN, null, ColorTemplate.COLORFUL_COLORS[1]);
                LegendEntry sportLegend = new LegendEntry("Sport", Legend.LegendForm.DEFAULT, Float.NaN, Float.NaN, null, ColorTemplate.COLORFUL_COLORS[2]);
                LegendEntry educationalLegend = new LegendEntry("Education", Legend.LegendForm.DEFAULT, Float.NaN, Float.NaN, null, ColorTemplate.COLORFUL_COLORS[3]);
                LegendEntry[] legendEntries = new LegendEntry[]{culturalLegend, religiousLegend, sportLegend, educationalLegend};
                barChart.getLegend().setCustom(legendEntries);
                barChart.getDescription().setEnabled(false);

                PieDataSet pieDataSet = new PieDataSet(pieEntries, "Event Categories");
                pieDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
                pieDataSet.setDrawValues(true);
                pieChart.setData(new PieData(pieDataSet));
                pieChart.getLegend().setCustom(legendEntries);
                pieChart.getDescription().setEnabled(false);
            }
        });

    }

    // this event will enable the back
    // function to the button on press
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