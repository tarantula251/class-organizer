package com.example.classorganizer;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;

public class ViewCoursesActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ViewCoursesAdapter adapter;
    private ArrayList<HashMap<String, ArrayList<String>>> courseList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_courses);
        courseList = new ArrayList<>();
        populateDataList();
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ViewCoursesAdapter(this, courseList);
        recyclerView.setAdapter(adapter);
    }

    private void populateDataList() {
        HashMap<String, ArrayList<String>> courseData = new HashMap<>();

        ArrayList<String> course = new ArrayList<>();
        course.add("Image Processing");
        courseData.put(getString(R.string.course), course);

        ArrayList<String> cycle = new ArrayList<>();
        cycle.add("2nd");
        courseData.put(getString(R.string.cycle), cycle);

        ArrayList<String> model = new ArrayList<>();
        model.add("part-time");
        courseData.put(getString(R.string.model), model);

        ArrayList<String> faculty = new ArrayList<>();
        faculty.add("FTIMS");
        courseData.put(getString(R.string.faculty), faculty);

        ArrayList<String> field = new ArrayList<>();
        field.add("Information Technology");
        courseData.put(getString(R.string.field), field);

        ArrayList<String> supervisor = new ArrayList<>();
        supervisor.add("dr inż. Michał Masecki");
        courseData.put(getString(R.string.supervisor), supervisor);

        ArrayList<String> classes = new ArrayList<>();
        classes.add("Laboratory Saturday 11.00 - 12.30");
        classes.add("Lecture Sunday 9.00 - 10.30");
        courseData.put(getString(R.string.classes), classes);

        /*-----------------------------------------------------*/

        HashMap<String, ArrayList<String>> courseData2 = new HashMap<>();

        ArrayList<String> course2 = new ArrayList<>();
        course2.add("Database Administration");
        courseData2.put(getString(R.string.course), course2);

        ArrayList<String> cycle2 = new ArrayList<>();
        cycle2.add("1nd");
        courseData2.put(getString(R.string.cycle), cycle2);

        ArrayList<String> model2 = new ArrayList<>();
        model2.add("full-time");
        courseData2.put(getString(R.string.model), model2);

        ArrayList<String> faculty2 = new ArrayList<>();
        faculty2.add("FTIMS");
        courseData2.put(getString(R.string.faculty), faculty2);

        ArrayList<String> field2 = new ArrayList<>();
        field2.add("Information Technology");
        courseData2.put(getString(R.string.field), field2);

        ArrayList<String> supervisor2 = new ArrayList<>();
        supervisor2.add("dr inż. Kacper Morawski");
        courseData2.put(getString(R.string.supervisor), supervisor2);

        ArrayList<String> classes2 = new ArrayList<>();
        classes2.add("Laboratory Saturday 12.00 - 13.30");
        classes2.add("Lecture Sunday 17.00 - 18.30");
        courseData2.put(getString(R.string.classes), classes2);

        courseList.add(courseData);
        courseList.add(courseData2);
    }
}