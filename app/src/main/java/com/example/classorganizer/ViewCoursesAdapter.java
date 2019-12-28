package com.example.classorganizer;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

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

        boolean allowClassPickerModal = false;
        if (this.context.toString().contains("ManageResultsActivity")) {
            allowClassPickerModal = true;
        }
        CourseViewHolder holder = new CourseViewHolder(view, context, classesCount, allowClassPickerModal);
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

    public static class CourseViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView textCourseName, textCycle, textModel, textFaculty, textField, textSupervisor;
        private ArrayList<TextView> textClasses;
        private int classesSize;
        private Context contextInner;

        public CourseViewHolder(@NonNull View itemView, Context context, int classesSize, boolean isModalAllowed) {
            super(itemView);
            contextInner = context;
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
            if (isModalAllowed) {
                itemView.setOnClickListener(this);
            }
        }

        @Override
        public void onClick(View v) {
            //prepare data to pass on modal submit
            final HashMap<String, String> selectedClass = new HashMap<>();
            TextView courseView = v.findViewById(R.id.courseName);
            TextView cycleView = v.findViewById(R.id.cycleValue);
            TextView modelView = v.findViewById(R.id.modelValue);
            TextView facultyView = v.findViewById(R.id.facultyValue);
            TextView fieldView = v.findViewById(R.id.fieldValue);
            TextView supervisorView = v.findViewById(R.id.supervisorValue);
            selectedClass.put(contextInner.getString(R.string.course), (String) courseView.getText());
            selectedClass.put(contextInner.getString(R.string.cycle), (String) cycleView.getText());
            selectedClass.put(contextInner.getString(R.string.model), (String) modelView.getText());
            selectedClass.put(contextInner.getString(R.string.faculty), (String) facultyView.getText());
            selectedClass.put(contextInner.getString(R.string.field), (String) fieldView.getText());
            selectedClass.put(contextInner.getString(R.string.supervisor), (String) supervisorView.getText());

            //prepare classes list for modal
            ArrayList<TextView> classesTextViews = new ArrayList<>();
            if (classesSize > 0) {
                for (int i = 0; i < classesSize; i++) {
                    classesTextViews.add((TextView) v.findViewWithTag("classesValue" + i));
                }
            }
            ArrayList<String> classesValues = new ArrayList<>();
            for (TextView textView : classesTextViews) {
                classesValues.add((String) textView.getText());
            }
            String[] singleChoiceItems = new String[classesValues.size()];
            singleChoiceItems = classesValues.toArray(singleChoiceItems);
            final String[] classesArray = singleChoiceItems;
            final int itemSelected = 0;
            //save initial class item in case user won't click anything
            selectedClass.put(contextInner.getString(R.string.classes), classesArray[itemSelected]);

            AlertDialog.Builder builder = new AlertDialog.Builder(contextInner, R.style.modalPickerStyle);
            builder.setTitle(contextInner.getString(R.string.pick_classes_modal));
            builder.setSingleChoiceItems(singleChoiceItems, itemSelected, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int selectedIndex) {
                    selectedClass.put(contextInner.getString(R.string.classes), classesArray[selectedIndex]);
                }
            });
            builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    dialog.cancel();
                }
            });
            builder.setPositiveButton(R.string.submit, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    Intent intent = new Intent(contextInner, ShowAssignedStudentsActivity.class);
                    Bundle extras = new Bundle();
                    extras.putSerializable("selectedClass", selectedClass);
                    intent.putExtras(extras);
                    contextInner.startActivity(intent);
                    dialog.cancel();
                }
            });
            builder.setCancelable(true);

            AlertDialog alertDialog = builder.create();
            alertDialog.show();
            alertDialog.getWindow().setLayout(950, WindowManager.LayoutParams.WRAP_CONTENT);

            //Change style of buttons
            Button positiveButton = alertDialog.getButton(DialogInterface.BUTTON_POSITIVE);
            positiveButton.setBackground(contextInner.getDrawable(R.drawable.default_small_button));
            positiveButton.setX(-450);
            positiveButton.setY(100);

            Button negativeButton = alertDialog.getButton(DialogInterface.BUTTON_NEGATIVE);
            negativeButton.setBackground(contextInner.getDrawable(R.drawable.cancel_small_button));
            negativeButton.setX(0);
            negativeButton.setY(-50);

        }
    }
}