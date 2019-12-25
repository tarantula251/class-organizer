package com.example.classorganizer;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.TextView;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AttendanceActivity extends AppCompatActivity {
    private ExpandableListView listView;
    private FilterCoursesAdapter listAdapter;
    private List<String> listDataHeader;
    private HashMap<String, List<String>> listHash;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance);

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

    public void showModal(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(AttendanceActivity.this, R.style.modalStyle);
        Boolean isAttendaceListValid = true;
        if (isAttendaceListValid) {
            builder.setMessage(getString(R.string.attendance_list_success));
            builder.setTitle(getString(R.string.success));
            builder.setPositiveButton(R.string.submit, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    dialog.cancel();
                    showAttendanceListModal();
                }
            });
            builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    dialog.cancel();
                }
            });
            builder.setCancelable(false);
        } else {
            builder.setMessage(getString(R.string.attendance_list_error));
            builder.setTitle(getString(R.string.error));
            builder.setNeutralButton(R.string.close, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    dialog.cancel();
                }
            });
            builder.setCancelable(true);
        }

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
        alertDialog.getWindow().setLayout(950, WindowManager.LayoutParams.WRAP_CONTENT);
        TextView textView = alertDialog.findViewById(android.R.id.message);
        textView.setTextSize(26);
        //Change style of buttons
        if (isAttendaceListValid) {
            Button positiveButton = alertDialog.getButton(DialogInterface.BUTTON_POSITIVE);
            positiveButton.setBackground(getDrawable(R.drawable.default_small_button));
            positiveButton.setX(-450);
            positiveButton.setY(100);

            Button negativeButton = alertDialog.getButton(DialogInterface.BUTTON_NEGATIVE);
            negativeButton.setBackground(getDrawable(R.drawable.cancel_small_button));
            negativeButton.setX(0);
            negativeButton.setY(-50);
        } else {
            Button neutralButton = alertDialog.getButton(DialogInterface.BUTTON_NEUTRAL);
            neutralButton.setBackground(getDrawable(R.drawable.cancel_small_button));
            neutralButton.setX(-225);
            neutralButton.setY(50);
        }
    }

    private void showAttendanceListModal() {
        AlertDialog.Builder builder = new AlertDialog.Builder(AttendanceActivity.this, R.style.modalStyle);
        builder.setMessage(getString(R.string.attendance_list_closed));
        builder.setTitle(getString(R.string.success));
        builder.setPositiveButton(R.string.show_more, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_right);
                findViewById(R.id.wrapperLayout).setVisibility(View.INVISIBLE);
                transaction.replace(R.id.fragmentContainer, new AttendanceItemFragment());
                transaction.commit();
            }
        });
        builder.setCancelable(true);
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
        alertDialog.getWindow().setLayout(950, WindowManager.LayoutParams.WRAP_CONTENT);
        TextView textView = alertDialog.findViewById(android.R.id.message);
        textView.setTextSize(22);
        Button positiveButton = alertDialog.getButton(DialogInterface.BUTTON_POSITIVE);
        positiveButton.setBackground(getDrawable(R.drawable.default_small_button));
        positiveButton.setX(-225);
        positiveButton.setY(50);
    }

}
