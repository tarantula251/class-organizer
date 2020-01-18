package com.example.classorganizer;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Pair;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Date;

import model.data.Class;
import model.data.User;
import model.data.UserType;
import model.network.Server;
import model.network.ServerException;

public class ViewCoursesAdapter extends RecyclerView.Adapter<ViewCoursesAdapter.CourseViewHolder> {

    private LayoutInflater layoutInflater;
    private Context context;
    private ArrayList<Class> classes;

    static private class AddAttendanceListAsyncTask extends AsyncTask<ArrayList<Pair<User, Date>>, Integer, ServerException>
    {
        Class classObject;
        ViewCoursesActivity context;
        ArrayList<Pair<User, Date>> attendanceList;

        AddAttendanceListAsyncTask(Class classObject, Context context)
        {
            this.classObject = classObject;
            this.context = (ViewCoursesActivity)context;
        }

        @Override
        protected ServerException doInBackground(ArrayList<Pair<User, Date>>... arrayLists)
        {
            Server server = Server.getInstance();
            int classDateId;
            attendanceList = arrayLists[0];
            try
            {
                 classDateId = server.addClassDate(classObject);
                 server.addAttendanceList(attendanceList, classDateId);
            }
            catch(ServerException e)
            {
                return e;
            }

            return new ServerException(0, "Success - ClassDateID: " + classDateId);
        }

        @Override
        protected void onPostExecute(ServerException e)
        {
            context.showAttendanceList(attendanceList);
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    ViewCoursesAdapter(Context context) {
        this.layoutInflater = LayoutInflater.from(context);
        this.context = context;
    }

    void setClasses(ArrayList<Class> classes)
    {
        this.classes = classes;
    }

    @NonNull
    @Override
    public CourseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.course_item, parent, false);
        GridLayout gridLayout = view.findViewById(R.id.courseGrid);
        int currentLastRowIndex = 5;
        int columnIndex = 1;
        TextView classTextView = new TextView(context);
        classTextView.setTag("classesValue" + 0);
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

        CourseViewHolder holder = new CourseViewHolder(view, context, true, null);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull CourseViewHolder holder, int position) {
        Class classObject = classes.get(position);

        holder.classObject = classObject;

        String course = classObject.getCourse().getName();
        holder.textCourseName.setText(course);

        String cycle = classObject.getCourse().getField().getCycle().getName();
        holder.textCycle.setText(cycle);

        String model = classObject.getCourse().getField().getModel().getName();
        holder.textModel.setText(model);

        String faculty = classObject.getCourse().getField().getFaculty().getName();
        holder.textFaculty.setText(faculty);

        String field = classObject.getCourse().getField().getName();
        holder.textField.setText(field);

        String supervisor = classObject.getCourse().getSupervisor().getFirstName();
        supervisor += " ";
        supervisor += classObject.getCourse().getSupervisor().getLastName();
        holder.textSupervisor.setText(supervisor);

        String classType = classObject.getType().getName();
        holder.textClasses.get(0).setText(classType);
    }

    @Override
    public int getItemCount() {
        return classes.size();
    }

    public static class CourseViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView textCourseName, textCycle, textModel, textFaculty, textField, textSupervisor;
        private ArrayList<TextView> textClasses;
        private Class classObject;
        private Context contextInner;

        public CourseViewHolder(@NonNull View itemView, Context context, boolean isModalAllowed, Class classObject)
        {
            super(itemView);
            contextInner = context;
            textCourseName = itemView.findViewById(R.id.courseName);
            textCycle = itemView.findViewById(R.id.cycleValue);
            textModel = itemView.findViewById(R.id.modelValue);
            textFaculty = itemView.findViewById(R.id.facultyValue);
            textField = itemView.findViewById(R.id.fieldValue);
            textSupervisor = itemView.findViewById(R.id.supervisorValue);

            this.classObject = classObject;
            textClasses = new ArrayList<>();
            textClasses.add((TextView) itemView.findViewWithTag("classesValue0"));
            if (isModalAllowed) {
                itemView.setOnClickListener(this);
            }
        }

        @Override
        public void onClick(View v)
        {
            final AlertDialog.Builder builder = new AlertDialog.Builder(contextInner, R.style.modalStyle);
            final AlertDialog alertDialog = builder.create();
            LayoutInflater inflater = ((AppCompatActivity)contextInner).getLayoutInflater();
            final View dialogView = inflater.inflate(R.layout.course_option_menu, null);

            //Manage results button
            final ImageView resultsImageView = dialogView.findViewById(R.id.resultsImageView);
            resultsImageView.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    Intent intent = new Intent(contextInner, ShowAssignedStudentsActivity.class);
                    Bundle extras = new Bundle();
                    extras.putSerializable("class", classObject);
                    intent.putExtras(extras);
                    contextInner.startActivity(intent);
                    alertDialog.cancel();
                }
            });

            //Manage attendance button
            final ImageView attendanceImageView = dialogView.findViewById(R.id.attendanceIimageView);
            attendanceImageView.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    showModal(v);
                    alertDialog.cancel();
                }
            });

            alertDialog.setView(dialogView);

            alertDialog.show();
        }

        void showModal(View view) {
            AlertDialog.Builder builder = new AlertDialog.Builder(contextInner, R.style.modalStyle);

            builder.setMessage(contextInner.getString(R.string.attendance_list_success));
            builder.setTitle(contextInner.getString(R.string.success));
            builder.setPositiveButton(R.string.submit, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    dialog.cancel();
                    AddAttendanceListAsyncTask addAttendanceListAsyncTaks = new AddAttendanceListAsyncTask(classObject, contextInner);
//                    ArrayList<Pair<User, Date>> users = AuthorizedUserBeam.getInstance().getAttendanceList();

                    //mock data
                    ArrayList<Pair<User, Date>> users = new ArrayList<>();
                    User justynaMock = new User(2, "Justyna", "Gąsior", "217097@edu.p.lodz.pl", new UserType(1, "Administrator"));
                    users.add(new Pair<>(Server.getInstance().getAuthorizedUser(), new Date()));
                    users.add(new Pair<>(justynaMock, new Date()));
                    //end mock

                    addAttendanceListAsyncTaks.execute(users);
                }
            });
            builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    dialog.cancel();
                }
            });
            builder.setCancelable(false);

            AlertDialog alertDialog = builder.create();
            alertDialog.show();
            alertDialog.getWindow().setLayout(950, WindowManager.LayoutParams.WRAP_CONTENT);
            TextView textView = alertDialog.findViewById(android.R.id.message);
            textView.setTextSize(26);

            Button positiveButton = alertDialog.getButton(DialogInterface.BUTTON_POSITIVE);
            positiveButton.setBackground(contextInner.getDrawable(R.drawable.default_small_button));
            positiveButton.setX(-450);
            positiveButton.setY(100);

            Button negativeButton = alertDialog.getButton(DialogInterface.BUTTON_NEGATIVE);
            negativeButton.setBackground(contextInner.getDrawable(R.drawable.cancel_small_button));
            negativeButton.setX(0);
            negativeButton.setY(-50);

            Intent intent = new Intent(contextInner, AuthorizedUserBeam.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            contextInner.startActivity(intent);
        }
    }
}