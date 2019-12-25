package com.example.classorganizer;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;

public class AttendanceItemFragment extends Fragment {

    private ArrayList<HashMap<String, String>> list;
    public static final String FIRST_COLUMN = "Column 1";
    public static final String SECOND_COLUMN = "Column 2";
    public static final String THIRD_COLUMN = "Column 3";

    public AttendanceItemFragment() {
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
        list = new ArrayList<HashMap<String, String>>();

        HashMap<String, String> rowFirst = new HashMap<String, String>();
        rowFirst.put(FIRST_COLUMN, getResources().getString(R.string.student_id));
        rowFirst.put(SECOND_COLUMN, getResources().getString(R.string.student_name));
        rowFirst.put(THIRD_COLUMN, getResources().getString(R.string.check_in_time));
        list.add(rowFirst);

        HashMap<String, String> rowScnd = new HashMap<String, String>();
        rowScnd.put(FIRST_COLUMN,"217097");
        rowScnd.put(SECOND_COLUMN, "Katarzyna Waszczykowska");
        rowScnd.put(THIRD_COLUMN, "11:02:36");
        list.add(rowScnd);

        HashMap<String, String> rowThrd = new HashMap<String, String>();
        rowThrd.put(FIRST_COLUMN,"217086");
        rowThrd.put(SECOND_COLUMN, "Wiktor Kacper Wączyński");
        rowThrd.put(THIRD_COLUMN, "11:04:39");
        list.add(rowThrd);
        HashMap<String, String> row1 = new HashMap<String, String>();
        row1.put(FIRST_COLUMN,"217086");
        row1.put(SECOND_COLUMN, "Wiktor Kacper Wączyński");
        row1.put(THIRD_COLUMN, "11:04:39");
        list.add(row1);
        HashMap<String, String> row2 = new HashMap<String, String>();
        row2.put(FIRST_COLUMN,"217086");
        row2.put(SECOND_COLUMN, "Wiktor Kacper Wączyński");
        row2.put(THIRD_COLUMN, "11:04:39");
        list.add(row2);
        HashMap<String, String> row3 = new HashMap<String, String>();
        row3.put(FIRST_COLUMN,"217086");
        row3.put(SECOND_COLUMN, "Wiktor Kacper Wączyński");
        row3.put(THIRD_COLUMN, "11:04:39");
        list.add(row3);
        HashMap<String, String> row4 = new HashMap<String, String>();
        row4.put(FIRST_COLUMN,"217086");
        row4.put(SECOND_COLUMN, "Wiktor Kacper Wączyński");
        row4.put(THIRD_COLUMN, "11:04:39");
        list.add(row4);
        HashMap<String, String> row5 = new HashMap<String, String>();
        row5.put(FIRST_COLUMN,"217086");
        row5.put(SECOND_COLUMN, "Wiktor Kacper Wączyński");
        row5.put(THIRD_COLUMN, "11:04:39");
        list.add(row5);
        HashMap<String, String> row6 = new HashMap<String, String>();
        row6.put(FIRST_COLUMN,"217086");
        row6.put(SECOND_COLUMN, "Wiktor Kacper Wączyński");
        row6.put(THIRD_COLUMN, "11:04:39");
        list.add(row6);
        HashMap<String, String> row7 = new HashMap<String, String>();
        row7.put(FIRST_COLUMN,"217086");
        row7.put(SECOND_COLUMN, "Wiktor Kacper Wączyński");
        row7.put(THIRD_COLUMN, "11:04:39");
        list.add(row7);
        HashMap<String, String> row8 = new HashMap<String, String>();
        row8.put(FIRST_COLUMN,"217086");
        row8.put(SECOND_COLUMN, "Wiktor Kacper Wączyński");
        row8.put(THIRD_COLUMN, "11:04:39");
        list.add(row8);
        HashMap<String, String> row9 = new HashMap<String, String>();
        row9.put(FIRST_COLUMN,"217086");
        row9.put(SECOND_COLUMN, "Wiktor Kacper Wączyński");
        row9.put(THIRD_COLUMN, "11:04:39");
        list.add(row9);
        HashMap<String, String> row10 = new HashMap<String, String>();
        row10.put(FIRST_COLUMN,"217086");
        row10.put(SECOND_COLUMN, "Wiktor Kacper Wączyński");
        row10.put(THIRD_COLUMN, "11:04:39");
        list.add(row10);
        HashMap<String, String> row11 = new HashMap<String, String>();
        row11.put(FIRST_COLUMN,"217086");
        row11.put(SECOND_COLUMN, "Wiktor Kacper Wączyński");
        row11.put(THIRD_COLUMN, "11:04:39");
        list.add(row11);
        HashMap<String, String> row12 = new HashMap<String, String>();
        row12.put(FIRST_COLUMN,"217086");
        row12.put(SECOND_COLUMN, "Wiktor Kacper Wączyński");
        row12.put(THIRD_COLUMN, "11:04:39");
        list.add(row12);
    }
}
