package com.example.classorganizer;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import model.data.User;

public class AttendanceItemFragment extends Fragment {

    private ArrayList<HashMap<String, String>> list;
    private ArrayList<Pair<User, Date>> attendanceList;
    public static final String FIRST_COLUMN = "Column 1";
    public static final String SECOND_COLUMN = "Column 2";
    public static final String THIRD_COLUMN = "Column 3";

    public AttendanceItemFragment(ArrayList<Pair<User, Date>> attendanceList)
    {
        this.attendanceList = attendanceList;
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.attendance_list, container, false);
        ListView listView = view.findViewById(R.id.attendanceListView);
        populateList();
        AttendanceAdapter adapter = new AttendanceAdapter(getActivity(), list);
        listView.setAdapter(adapter);
        return view;
    }

    private void populateList() {
        list = new ArrayList<>();

        HashMap<String, String> rowFirst = new HashMap<>();
        rowFirst.put(FIRST_COLUMN, getResources().getString(R.string.student_id));
        rowFirst.put(SECOND_COLUMN, getResources().getString(R.string.student_name));
        rowFirst.put(THIRD_COLUMN, getResources().getString(R.string.check_in_time));
        list.add(rowFirst);

        for(Pair<User,Date> attendance : attendanceList)
        {
            HashMap<String, String> row = new HashMap<>();
            row.put(FIRST_COLUMN, attendance.first.getEmail().split("@")[0]);
            row.put(SECOND_COLUMN, attendance.first.getFirstName() + " " + attendance.first.getLastName());
            row.put(THIRD_COLUMN, new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(attendance.second));
            list.add(row);
        }
    }
}
