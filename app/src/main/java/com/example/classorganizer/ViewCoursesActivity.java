package com.example.classorganizer;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

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
}