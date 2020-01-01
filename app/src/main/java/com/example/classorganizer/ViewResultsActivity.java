package com.example.classorganizer;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class ViewResultsActivity extends AppCompatActivity {
    private HashMap<String, String> classData;
    private HashMap<String, String> studentData;
    private RecyclerView recyclerView;
    private ViewResultsAdapter adapter;
    private ArrayList<HashMap<String, ArrayList<String>>> resultsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            classData = (HashMap<String, String>) bundle.getSerializable("selectedClass");
            studentData = (HashMap<String, String>) bundle.getSerializable("selectedStudent");
        }
        fillStudentBox();
        resultsList = new ArrayList<>();
        populateDataList();
        recyclerView = findViewById(R.id.resultsRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ViewResultsAdapter(this, resultsList);
        recyclerView.setAdapter(adapter);
    }

    private void fillStudentBox() {
        String studentName = studentData.get(getResources().getString(R.string.student_name));
        String studentId = studentData.get(getResources().getString(R.string.student_id));
        TextView studentNameTextView = findViewById(R.id.resultsStudentName);
        TextView studentIdTextView = findViewById(R.id.resultsStudentId);
        studentNameTextView.setText(studentName);
        studentIdTextView.setText(studentId);
    }

    private void populateDataList() {
        HashMap<String, ArrayList<String>> resultData = new HashMap<>();

        ArrayList<String> result = new ArrayList<>();
        result.add("4.5");
        resultData.put(getString(R.string.result), result);

        ArrayList<String> date = new ArrayList<>();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-YYYY HH:mm:ss");
        date.add(dateFormat.format(new Date()));
        resultData.put(getString(R.string.added_date), date);

        ArrayList<String> description = new ArrayList<>();
        description.add("Very well.");
        resultData.put(getString(R.string.description), description);

        /*-----------------------------------------------------*/

        HashMap<String, ArrayList<String>> resultData2 = new HashMap<>();

        ArrayList<String> result2 = new ArrayList<>();
        result2.add("3.5");
        resultData2.put(getString(R.string.result), result2);

        ArrayList<String> date2 = new ArrayList<>();
        date2.add(dateFormat.format(new Date()));
        resultData2.put(getString(R.string.added_date), date2);

        ArrayList<String> description2 = new ArrayList<>();
        description2.add("Awesome.");
        resultData2.put(getString(R.string.description), description2);

        resultsList.add(resultData);
        resultsList.add(resultData2);
    }
}
