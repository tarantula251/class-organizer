package com.example.classorganizer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
    }

    public void showFilterCoursesAttendance(View view) {
        Intent intent = new Intent(this, FilterCoursesAttendanceActivity.class);
        startActivity(intent);
    }
}
