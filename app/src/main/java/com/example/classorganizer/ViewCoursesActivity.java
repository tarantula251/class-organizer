package com.example.classorganizer;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;

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
    private ArrayList<HashMap<String, ArrayList<String>>> classesList;
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
                viewCoursesActivity.classesList = new ArrayList<>();

                for(Class classObject : classes) viewCoursesActivity.addClass(classObject);

                viewCoursesActivity.recyclerView = viewCoursesActivity.findViewById(R.id.recyclerView);
                viewCoursesActivity.recyclerView.setLayoutManager(new LinearLayoutManager(viewCoursesActivity));
                viewCoursesActivity.adapter = new ViewCoursesAdapter(viewCoursesActivity, viewCoursesActivity.classesList);
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

    private void addClass(Class classObject) {
        HashMap<String, ArrayList<String>> classData = new HashMap<>();

        ArrayList<String> course = new ArrayList<>();
        course.add(classObject.getCourse().getName());
        classData.put(getString(R.string.course), course);

        ArrayList<String> cycle = new ArrayList<>();
        cycle.add(classObject.getCourse().getField().getCycle().getName());
        classData.put(getString(R.string.cycle), cycle);

        ArrayList<String> model = new ArrayList<>();
        model.add(classObject.getCourse().getField().getModel().getName());
        classData.put(getString(R.string.model), model);

        ArrayList<String> faculty = new ArrayList<>();
        faculty.add(classObject.getCourse().getField().getFaculty().getName());
        classData.put(getString(R.string.faculty), faculty);

        ArrayList<String> field = new ArrayList<>();
        field.add(classObject.getCourse().getField().getName());
        classData.put(getString(R.string.field), field);

        ArrayList<String> supervisor = new ArrayList<>();
        supervisor.add(classObject.getCourse().getSupervisor().getFirstName() + " " + classObject.getCourse().getSupervisor().getLastName());
        classData.put(getString(R.string.supervisor), supervisor);

        ArrayList<String> classes = new ArrayList<>();
        classes.add(classObject.getType().getName());
        classData.put(getString(R.string.classes), classes);

        classesList.add(classData);
    }
}