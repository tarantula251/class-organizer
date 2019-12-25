package com.example.classorganizer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class MenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
    }

    public void showFilterCoursesAttendance(View view) {
        Intent intent = new Intent(this, AttendanceActivity.class);
        startActivity(intent);
    }

    public void showViewCourses(View view) {
        Intent intent = new Intent(this, ViewCoursesActivity.class);
        startActivity(intent);
    }
}
