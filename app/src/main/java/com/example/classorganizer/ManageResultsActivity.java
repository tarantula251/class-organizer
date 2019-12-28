package com.example.classorganizer;

import android.os.Bundle;
import android.widget.TextView;

public class ManageResultsActivity extends ViewCoursesActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        customizeLayout();
    }

    private void customizeLayout() {
        TextView infoTextView = findViewById(R.id.viewCoursesInfo);
        infoTextView.setText(R.string.tap_course_info);
    }
}
