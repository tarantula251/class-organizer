package com.example.classorganizer;

import android.app.Activity;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

public class AssignedStudentsAdapter extends BaseAdapter {
    public static final String FIRST_COLUMN = "Column 1";
    public static final String SECOND_COLUMN = "Column 2";

    public ArrayList<HashMap<String,String>> list;
    Activity activity;

    public AssignedStudentsAdapter(Activity activity, ArrayList<HashMap<String,String>> list) {
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
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        AssignedStudentsAdapter.ViewHolder holder;
        LayoutInflater inflater = activity.getLayoutInflater();

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.student_item, null);
            holder = new AssignedStudentsAdapter.ViewHolder();
            holder.txtFirst = (TextView) convertView.findViewById(R.id.columnId);
            holder.txtSecond = (TextView) convertView.findViewById(R.id.columnName);
            convertView.setTag(holder);
        } else {
            holder = (AssignedStudentsAdapter.ViewHolder) convertView.getTag();
        }

        HashMap<String, String> map = list.get(position);
        holder.txtFirst.setText(map.get(FIRST_COLUMN));
        holder.txtSecond.setText(map.get(SECOND_COLUMN));

        //add custom background and text size to the header
        if (position == 0) {
            convertView.setBackgroundResource(R.color.important_color);
            convertView.setPadding(5, 0, 0, 0);
            TextView studentIdTextView = convertView.findViewById(R.id.columnId);
            TextView studentNameTextView = convertView.findViewById(R.id.columnName);
            studentIdTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
            studentNameTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
            convertView.setPadding(5, 20, 5, 20);
            TextView addTextView = convertView.findViewById(R.id.columnAdd);
            TextView editextView = convertView.findViewById(R.id.columnEdit);
            addTextView.setVisibility(View.INVISIBLE);
            editextView.setVisibility(View.INVISIBLE);
        }
        return convertView;
    }

    private class ViewHolder {
        TextView txtFirst;
        TextView txtSecond;
    }
}
