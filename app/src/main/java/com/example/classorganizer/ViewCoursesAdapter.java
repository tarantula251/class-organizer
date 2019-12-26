package com.example.classorganizer;

import android.content.Context;
import android.graphics.Typeface;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;

public class ViewCoursesAdapter extends RecyclerView.Adapter<ViewCoursesAdapter.CourseViewHolder> {

    private LayoutInflater layoutInflater;
    private ArrayList<HashMap<String, ArrayList<String>>> data;
    private Context context;

    public ViewCoursesAdapter(Context context, ArrayList<HashMap<String, ArrayList<String>>> data) {
        this.layoutInflater = LayoutInflater.from(context);
        this.data = data;
        this.context = context;
    }

    @NonNull
    @Override
    public CourseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.course_item, parent, false);
        GridLayout gridLayout = view.findViewById(R.id.courseGrid);

        if (!data.isEmpty()) {
            for (HashMap<String, ArrayList<String>> courseMap : data) {
                ArrayList<String> classes = courseMap.get(context.getResources().getString(R.string.classes));

                if (classes.size() > 0) {
                    int currentLastRowIndex = 5;
                    int columnIndex = 1;
                    for (String classToAppend : classes) {
                        TextView classTextView = new TextView(context);
                        classTextView.setTag("classesValue" + classes.indexOf(classToAppend));
                        classTextView.setTextColor(context.getResources().getColor(R.color.list_item_background_color));
                        classTextView.setTypeface(null, Typeface.BOLD);
                        classTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
                        classTextView.setEllipsize(TextUtils.TruncateAt.END);

                        GridLayout.LayoutParams layoutParams = new GridLayout.LayoutParams(
                                GridLayout.spec(currentLastRowIndex, GridLayout.LEFT),
                                GridLayout.spec(columnIndex, GridLayout.LEFT)
                        );
                        layoutParams.width = GridLayout.LayoutParams.WRAP_CONTENT;
                        layoutParams.height = GridLayout.LayoutParams.WRAP_CONTENT;

                        gridLayout.addView(classTextView, layoutParams);
                        currentLastRowIndex++;
                    }
                }
            }
        }

        final int childCount = gridLayout.getChildCount();
        int classesCount = 0;

        for (int i = 0; i < childCount; i++) {
            final View child = gridLayout.getChildAt(i);
            if (child instanceof TextView && String.valueOf(child.getTag()).contains("classesValue")) {
                classesCount++;
            }
        }

        CourseViewHolder holder = new CourseViewHolder(view, classesCount);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull CourseViewHolder holder, int position) {
        HashMap<String, ArrayList<String>> courseData = data.get(position);

        String course = courseData.get(context.getResources().getString(R.string.course)).get(0);
        holder.textCourseName.setText(course);

        String cycle = courseData.get(context.getResources().getString(R.string.cycle)).get(0);
        holder.textCycle.setText(cycle);

        String model = courseData.get(context.getResources().getString(R.string.model)).get(0);
        holder.textModel.setText(model);

        String faculty = courseData.get(context.getResources().getString(R.string.faculty)).get(0);
        holder.textFaculty.setText(faculty);

        String field = courseData.get(context.getResources().getString(R.string.field)).get(0);
        holder.textField.setText(field);

        String supervisor = courseData.get(context.getResources().getString(R.string.supervisor)).get(0);
        holder.textSupervisor.setText(supervisor);

        ArrayList<String> classes = courseData.get(context.getResources().getString(R.string.classes));
        holder.classesSize = classes.size();

        if (holder.classesSize > 0) {
            for (int i = 0; i < holder.classesSize; i++) {
                String classVal = classes.get(i);
                holder.textClasses.get(i).setText(classVal);
            }
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public static class CourseViewHolder extends RecyclerView.ViewHolder {
        TextView textCourseName, textCycle, textModel, textFaculty, textField, textSupervisor;
        ArrayList<TextView> textClasses;
        int classesSize;

        public CourseViewHolder(@NonNull View itemView, int classesSize) {
            super(itemView);
            textCourseName = itemView.findViewById(R.id.courseName);
            textCycle = itemView.findViewById(R.id.cycleValue);
            textModel = itemView.findViewById(R.id.modelValue);
            textFaculty = itemView.findViewById(R.id.facultyValue);
            textField = itemView.findViewById(R.id.fieldValue);
            textSupervisor = itemView.findViewById(R.id.supervisorValue);

            this.classesSize = classesSize;
            textClasses = new ArrayList<>();
            if (classesSize > 0) {
                for (int i = 0; i < classesSize; i++) {
                    textClasses.add((TextView) itemView.findViewWithTag("classesValue" + i));
                }
            }
        }
    }
}