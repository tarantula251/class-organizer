package com.example.classorganizer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ViewCoursesAdapter extends RecyclerView.Adapter<ViewCoursesAdapter.CourseViewHolder> {

    private LayoutInflater layoutInflater;
    private List<String> data;

    public ViewCoursesAdapter(Context context, List<String> data) {
        this.layoutInflater = LayoutInflater.from(context);
        this.data = data;
    }

    @NonNull
    @Override
    public CourseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.course_item, parent, false);
        return new CourseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CourseViewHolder holder, int position) {
        String course = data.get(position);
        holder.textCourseName.setText(course);

        String cycle = data.get(position);
        holder.textCycle.setText(cycle);

        String model = data.get(position);
        holder.textModel.setText(model);

        String faculty = data.get(position);
        holder.textFaculty.setText(faculty);

        String field = data.get(position);
        holder.textField.setText(field);

        String supervisor = data.get(position);
        holder.textSupervisor.setText(supervisor);

        String classes = data.get(position);
        holder.textClasses.setText(classes);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public static class CourseViewHolder extends RecyclerView.ViewHolder {

        TextView textCourseName, textCycle, textModel, textFaculty, textField, textSupervisor, textClasses; //TODO modify textClasses to hold many classes values

        public CourseViewHolder(@NonNull View itemView) {
            super(itemView);
            textCourseName = itemView.findViewById(R.id.courseName);
            textCycle = itemView.findViewById(R.id.cycleValue);
            textModel = itemView.findViewById(R.id.modelValue);
            textFaculty = itemView.findViewById(R.id.facultyValue);
            textField = itemView.findViewById(R.id.fieldValue);
            textSupervisor = itemView.findViewById(R.id.supervisorValue);
            textClasses = itemView.findViewById(R.id.classesValue);
        }
    }
}