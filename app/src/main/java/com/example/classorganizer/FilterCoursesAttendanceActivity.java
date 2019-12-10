package com.example.classorganizer;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ExpandableListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class FilterCoursesAttendanceActivity extends AppCompatActivity {
    private ExpandableListView listView;
    private FilterCoursesAdapter listAdapter;
    private List<String> listDataHeader;
    private HashMap<String, List<String>> listHash;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter_courses_attendance);

        listView = findViewById(R.id.lvExp);
        initListData();
        listAdapter = new FilterCoursesAdapter(this, listDataHeader, listHash);
        listView.setAdapter(listAdapter);
    }

    private void initListData() {
        listDataHeader = new ArrayList<>();
        listHash = new HashMap<>();

        listDataHeader.add(getString(R.string.cycle));
        listDataHeader.add(getString(R.string.model));
        listDataHeader.add(getString(R.string.faculty));
        listDataHeader.add(getString(R.string.field));
        listDataHeader.add(getString(R.string.course));
        listDataHeader.add(getString(R.string.type_of_class));

        List<String> cycles = new ArrayList<>();
        cycles.add("Test Cycle 1");
        cycles.add("Test Cycle 2");
        cycles.add("Test Cycle 3");

        List<String> models = new ArrayList<>();
        models.add("Test Model 1");
        models.add("Test Model 2");

        List<String> faculties = new ArrayList<>();
        faculties.add("Test Faculty 1");

        List<String> fields = new ArrayList<>();
        fields.add("Test Field 1");

        List<String> courses = new ArrayList<>();
        courses.add("Test Course 1");
        courses.add("Test Course 2");
        courses.add("Test Course 3");
        courses.add("Test Course 4");

        List<String> typesOfClass = new ArrayList<>();
        typesOfClass.add("Type 1");
        typesOfClass.add("Type 2");
        typesOfClass.add("Type 3");

        listHash.put(listDataHeader.get(0), cycles);
        listHash.put(listDataHeader.get(1), models);
        listHash.put(listDataHeader.get(2), faculties);
        listHash.put(listDataHeader.get(3), fields);
        listHash.put(listDataHeader.get(4), courses);
        listHash.put(listDataHeader.get(5), typesOfClass);

    }
}
