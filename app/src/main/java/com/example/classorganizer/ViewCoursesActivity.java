package com.example.classorganizer;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ViewCoursesActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ViewCoursesAdapter adapter;
    private ArrayList<String> items;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_courses);

        populateDataList();
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ViewCoursesAdapter(this, items);
        recyclerView.setAdapter(adapter);
    }

    private void populateDataList() {
        items = new ArrayList<>();
        items.add("First Card View Item");
        items.add("Scnd Card View Item");
        items.add("Thrd Card View Item");
        items.add("Frth Card View Item");
        items.add("Fth Card View Item");
    }
}