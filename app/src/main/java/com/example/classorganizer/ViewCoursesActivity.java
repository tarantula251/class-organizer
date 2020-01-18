package com.example.classorganizer;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Date;

import model.data.Course;
import model.data.Class;
import model.data.Cycle;
import model.data.Faculty;
import model.data.Field;
import model.data.Model;
import model.data.User;
import model.network.Server;

public class ViewCoursesActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ViewCoursesAdapter adapter;
    private ProgressBar progressBar;

    private static class GetMyClassesAsyncTask extends AsyncTask<Void, Integer, ArrayList<Class>>
    {
        ViewCoursesActivity viewCoursesActivity;
        Server server;

        GetMyClassesAsyncTask(ViewCoursesActivity viewCoursesActivity, Server server)
        {
            this.viewCoursesActivity = viewCoursesActivity;
            this.server = server;
        }

        @Override
        protected ArrayList<Class> doInBackground(Void... voids)
        {
            ArrayList<User> users = server.getUsers();
            ArrayList<Faculty> faculties = server.getFaculties();
            ArrayList<Model> models = server.getModels();
            ArrayList<Cycle> cycles = server.getCycles();
            ArrayList<Field> fields = server.getFields(faculties, models, cycles);
            ArrayList<Course> courses = server.getCourses(users, fields);
            return server.getClassesForTeacher(courses, server.getAuthorizedUser());
        }

        @Override
        protected void onPostExecute(ArrayList<Class> classes)
        {
            if(classes != null)
            {
                viewCoursesActivity.recyclerView = viewCoursesActivity.findViewById(R.id.recyclerView);
                viewCoursesActivity.recyclerView.setLayoutManager(new LinearLayoutManager(viewCoursesActivity));
                viewCoursesActivity.adapter = new ViewCoursesAdapter(viewCoursesActivity);
                viewCoursesActivity.adapter.setClasses(classes);
                viewCoursesActivity.recyclerView.setAdapter(viewCoursesActivity.adapter);
            }
            viewCoursesActivity.progressBar.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_courses);
        progressBar = findViewById(R.id.progressBar);
        GetMyClassesAsyncTask getMyClassesAsyncTask = new GetMyClassesAsyncTask(this, Server.getInstance());
        getMyClassesAsyncTask.execute();
    }

    public void showAttendanceList(final ArrayList<Pair<User, Date>> attendanceList)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.modalStyle);
        builder.setMessage(getString(R.string.attendance_list_closed));
        builder.setTitle(getString(R.string.success));
        builder.setPositiveButton(R.string.show_more, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_right);
                transaction.replace(R.id.fragmentContainer, new AttendanceItemFragment(attendanceList));
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