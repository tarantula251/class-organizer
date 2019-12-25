package com.example.classorganizer;

import android.app.Activity;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.core.content.res.ResourcesCompat;

import java.util.ArrayList;
import java.util.HashMap;


public class AttendanceAdapter extends BaseAdapter {
    public static final String FIRST_COLUMN = "Column 1";
    public static final String SECOND_COLUMN = "Column 2";
    public static final String THIRD_COLUMN = "Column 3";

    public ArrayList<HashMap<String,String>> list;
    Activity activity;

    public AttendanceAdapter(Activity activity, ArrayList<HashMap<String,String>> list) {
        super();
        this.activity = activity;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        LayoutInflater inflater =  activity.getLayoutInflater();

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.attendance_item, null);
            holder = new ViewHolder();
            holder.txtFirst = (TextView) convertView.findViewById(R.id.columnStudentId);
            holder.txtSecond = (TextView) convertView.findViewById(R.id.columnStudentName);
            holder.txtThird = (TextView) convertView.findViewById(R.id.columnTime);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        HashMap<String, String> map = list.get(position);
        holder.txtFirst.setText(map.get(FIRST_COLUMN));
        holder.txtSecond.setText(map.get(SECOND_COLUMN));
        holder.txtThird.setText(map.get(THIRD_COLUMN));

        //add custom background and text size to the header
        if (position == 0) {
            convertView.setBackgroundResource(R.color.important_color);
            convertView.setPadding(0, 0, 0, 0);
            TextView studentIdTextView = convertView.findViewById(R.id.columnStudentId);
            TextView studentNameTextView = convertView.findViewById(R.id.columnStudentName);
            TextView studentCheckInTextView = convertView.findViewById(R.id.columnTime);
            studentIdTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
            studentNameTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
            studentCheckInTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
            convertView.setPadding(5, 20, 5, 20);

        }

        return convertView;
    }

    private class ViewHolder {
        TextView txtFirst;
        TextView txtSecond;
        TextView txtThird;
    }
}
