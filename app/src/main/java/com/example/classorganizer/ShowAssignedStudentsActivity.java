package com.example.classorganizer;

import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashMap;

public class ShowAssignedStudentsActivity extends AppCompatActivity {
    private HashMap<String, String> classData;
    private ArrayList<HashMap<String, String>> list;
    public static final String FIRST_COLUMN = "Column 1";
    public static final String SECOND_COLUMN = "Column 2";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assigned_students);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            classData = (HashMap<String, String>) bundle.getSerializable("selectedClass");
        }
        fillInfoBox();
        ListView listView = findViewById(R.id.studentsListView);
        populateList();
        AssignedStudentsAdapter adapter = new AssignedStudentsAdapter(this, list);
        listView.setAdapter(adapter);
    }

    private void fillInfoBox() {
        String course = classData.get(getString(R.string.course));
        String model = classData.get(getString(R.string.model));
        String classes = classData.get(getString(R.string.classes));
        TextView courseTextView = findViewById(R.id.course);
        TextView modelTextView = findViewById(R.id.model);
        TextView classesTextView = findViewById(R.id.classes);
        courseTextView.setText(course);
        modelTextView.setText(model);
        classesTextView.setText(classes);
    }

    private void populateList() {
        list = new ArrayList<>();

        HashMap<String, String> rowFirst = new HashMap<String, String>();
        rowFirst.put(FIRST_COLUMN, getResources().getString(R.string.student_id));
        rowFirst.put(SECOND_COLUMN, getResources().getString(R.string.student_name));
        list.add(rowFirst);

        HashMap<String, String> rowScnd = new HashMap<String, String>();
        rowScnd.put(FIRST_COLUMN,"217097");
        rowScnd.put(SECOND_COLUMN, "Katarzyna Waszczykowska");
        list.add(rowScnd);

        HashMap<String, String> rowThrd = new HashMap<String, String>();
        rowThrd.put(FIRST_COLUMN,"217086");
        rowThrd.put(SECOND_COLUMN, "Wiktor Kacper Wączyński");
        list.add(rowThrd);
        HashMap<String, String> row1 = new HashMap<String, String>();
        row1.put(FIRST_COLUMN,"217086");
        row1.put(SECOND_COLUMN, "Wiktor Kacper Wączyński");
        list.add(row1);
        HashMap<String, String> row2 = new HashMap<String, String>();
        row2.put(FIRST_COLUMN,"217086");
        row2.put(SECOND_COLUMN, "Wiktor Kacper Wączyński");
        list.add(row2);
        HashMap<String, String> row3 = new HashMap<String, String>();
        row3.put(FIRST_COLUMN,"217086");
        row3.put(SECOND_COLUMN, "Wiktor Kacper Wączyński");
        list.add(row3);
        HashMap<String, String> row4 = new HashMap<String, String>();
        row4.put(FIRST_COLUMN,"217086");
        row4.put(SECOND_COLUMN, "Wiktor Kacper Wączyński");
        list.add(row4);
        HashMap<String, String> row5 = new HashMap<String, String>();
        row5.put(FIRST_COLUMN,"217086");
        row5.put(SECOND_COLUMN, "Wiktor Kacper Wączyński");
        list.add(row5);
        HashMap<String, String> row6 = new HashMap<String, String>();
        row6.put(FIRST_COLUMN,"217086");
        row6.put(SECOND_COLUMN, "Wiktor Kacper Wączyński");
        list.add(row6);
        HashMap<String, String> row7 = new HashMap<String, String>();
        row7.put(FIRST_COLUMN,"217086");
        row7.put(SECOND_COLUMN, "Wiktor Kacper Wączyński");
        list.add(row7);
        HashMap<String, String> row8 = new HashMap<String, String>();
        row8.put(FIRST_COLUMN,"217086");
        row8.put(SECOND_COLUMN, "Wiktor Kacper Wączyński");
        list.add(row8);
        HashMap<String, String> row9 = new HashMap<String, String>();
        row9.put(FIRST_COLUMN,"217086");
        row9.put(SECOND_COLUMN, "Wiktor Kacper Wączyński");
        list.add(row9);
        HashMap<String, String> row10 = new HashMap<String, String>();
        row10.put(FIRST_COLUMN,"217086");
        row10.put(SECOND_COLUMN, "Wiktor Kacper Wączyński");
        list.add(row10);
        HashMap<String, String> row11 = new HashMap<String, String>();
        row11.put(FIRST_COLUMN,"217086");
        row11.put(SECOND_COLUMN, "Wiktor Kacper Wączyński");
        list.add(row11);
        HashMap<String, String> row12 = new HashMap<String, String>();
        row12.put(FIRST_COLUMN,"217086");
        row12.put(SECOND_COLUMN, "Wiktor Kacper Wączyński");
        list.add(row12);
    }
}
