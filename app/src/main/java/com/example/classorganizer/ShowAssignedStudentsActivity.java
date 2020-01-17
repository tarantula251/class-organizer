package com.example.classorganizer;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.InputType;
import android.text.Spanned;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashMap;

import model.data.Class;
import model.data.User;
import model.network.Server;
import model.network.ServerException;

public class ShowAssignedStudentsActivity extends AppCompatActivity {
    private Class classObject;
    private HashMap<String, String> studentData;
    private ArrayList<HashMap<String, String>> list;
    private ListView listView;
    private EditText resultBox;
    private TextView studentIdTextView;
    private TextView studentNameTextView;
    private int maxLengthDescriptionText = 250;
    private int maxLengthTitleText = 45;
    private int maxLengthResultText = 3;
    public static final String FIRST_COLUMN = "Column 1";
    public static final String SECOND_COLUMN = "Column 2";

    static class GetUsersForClassAsyncTask extends AsyncTask<Class, Integer, ArrayList<User>>
    {
        ShowAssignedStudentsActivity showAssignedStudentsActivity;

        GetUsersForClassAsyncTask(ShowAssignedStudentsActivity showAssignedStudentsActivity)
        {
            this.showAssignedStudentsActivity = showAssignedStudentsActivity;
        }

        @Override
        protected ArrayList<User> doInBackground(Class... classes)
        {
            if(classes.length == 0) return null;
            Server server = Server.getInstance();
            return  server.getUsersForClass(classes[0]);
        }

        @Override
        protected void onPostExecute(ArrayList<User> users)
        {
            if(users == null) return;
            showAssignedStudentsActivity.populateList(users);
            AssignedStudentsAdapter adapter = new AssignedStudentsAdapter(showAssignedStudentsActivity, showAssignedStudentsActivity.list);
            showAssignedStudentsActivity.listView.setAdapter(adapter);
        }
    }

    static class AddResultForUserAsyncTask extends AsyncTask<String, Integer, ServerException>
    {
        ShowAssignedStudentsActivity showAssignedStudentsActivity;

        AddResultForUserAsyncTask(ShowAssignedStudentsActivity showAssignedStudentsActivity)
        {
            this.showAssignedStudentsActivity = showAssignedStudentsActivity;
        }

        @Override
        protected ServerException doInBackground(String... strings)
        {
            if(strings.length < 4) return new ServerException(-1, "Invalid or lack of arguments");
            Server server = Server.getInstance();
            try
            {
                Integer id = server.addResultForUserEmailId(strings[0], strings[1], strings[2], Float.parseFloat(strings[3]), showAssignedStudentsActivity.classObject);
                return new ServerException(id, "Success");
            }
            catch(ServerException e)
            {
                return e;
            }
        }

        @Override
        protected void onPostExecute(ServerException serverException)
        {
            if(serverException.getErrorCode() != 0)
            {
                AlertDialog.Builder builder = new AlertDialog.Builder(showAssignedStudentsActivity, R.style.modalStyle);
                builder.setMessage("Add result failed.");
                builder.setMessage(serverException.getMessage());
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assigned_students);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            classObject = (Class) bundle.getSerializable("class");
        }
        fillInfoBox();
        listView = findViewById(R.id.studentsListView);

        studentData = new HashMap<>();
        list = new ArrayList<>();
        GetUsersForClassAsyncTask getUsersForClassAsyncTask = new GetUsersForClassAsyncTask(this);
        getUsersForClassAsyncTask.execute(classObject);
    }

    private void fillInfoBox() {
        String course = classObject.getCourse().getName();
        String model = classObject.getCourse().getField().getModel().getName();
        String classes = classObject.getType().getName();
        TextView courseTextView = findViewById(R.id.course);
        TextView modelTextView = findViewById(R.id.model);
        TextView classesTextView = findViewById(R.id.classes);
        courseTextView.setText(course);
        modelTextView.setText(model);
        classesTextView.setText(classes);
    }

    private void populateList(ArrayList<User> users) {
        HashMap<String, String> rowFirst = new HashMap<String, String>();
        rowFirst.put(FIRST_COLUMN, getResources().getString(R.string.student_id));
        rowFirst.put(SECOND_COLUMN, getResources().getString(R.string.student_name));
        list.add(rowFirst);

        for(User user : users)
        {
            HashMap<String, String> row = new HashMap<String, String>();
            row.put(FIRST_COLUMN, user.getEmail().split("@", 2)[0]);
            row.put(SECOND_COLUMN, String.format("%s %s", user.getFirstName(), user.getLastName()));
            list.add(row);
        }
    }

    public void showAddResultModal(View view) {
        view.startAnimation(AnimationUtils.loadAnimation(this, R.anim.button_click));
        LinearLayout parentRow = (LinearLayout) view.getParent();
        studentIdTextView = (TextView) parentRow.getChildAt(0);
        studentNameTextView = (TextView) parentRow.getChildAt(1);
        if (studentData.isEmpty() ||
                (!studentData.isEmpty() &&
                        !studentData.get(getResources().getString(R.string.student_id)).equalsIgnoreCase(studentIdTextView.getText().toString()) &&
                        !studentData.get(getResources().getString(R.string.student_name)).equalsIgnoreCase(studentNameTextView.getText().toString()))) {
            studentData.put(getResources().getString(R.string.student_id), studentIdTextView.getText().toString());
            studentData.put(getResources().getString(R.string.student_name), studentNameTextView.getText().toString());
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(ShowAssignedStudentsActivity.this, R.style.modalAddResultStyle);
        LinearLayout modalLayout = new LinearLayout(this);
        modalLayout.setPaddingRelative(64,64,64,64);
        modalLayout.setOrientation(LinearLayout.VERTICAL);

        final TextView resultLabel = new TextView(this);
        resultLabel.setText(getResources().getText(R.string.result_colon));
        resultLabel.setTextColor(getResources().getColor(R.color.important_color));
        resultLabel.setTypeface(null, Typeface.BOLD);
        modalLayout.addView(resultLabel);

        resultBox = new EditText(this);
        resultBox.setTag("resultBox");
        resultBox.setHint("Result");
        resultBox.setTextColor(getResources().getColor(R.color.add_result_text_color));
        resultBox.setPadding(0, 15, 10, 25);
        resultBox.setMaxLines(1);
        resultBox.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
        InputFilter lengthInputFilter = new InputFilter.LengthFilter(maxLengthResultText);
        resultBox.setFilters(new InputFilter[] {lengthInputFilter, new MinMaxInputFilter(0.0f, 5.0f)});
        modalLayout.addView(resultBox);

        final TextView titleLabel = new TextView(this);
        titleLabel.setText(getResources().getText(R.string.title_colon));
        titleLabel.setTextColor(getResources().getColor(R.color.important_color));
        titleLabel.setTypeface(null, Typeface.BOLD);
        modalLayout.addView(titleLabel);

        final EditText titleBox = new EditText(this);
        titleBox.setHint("Title");
        titleBox.setTextColor(getResources().getColor(R.color.add_result_text_color));
        titleBox.setPadding(0, 15, 10, 25);
        titleBox.setFilters(new InputFilter[] {new InputFilter.LengthFilter(maxLengthTitleText)});
        modalLayout.addView(titleBox);

        final TextView descriptionLabel = new TextView(this);
        descriptionLabel.setText(getResources().getText(R.string.description_colon));
        descriptionLabel.setTextColor(getResources().getColor(R.color.important_color));
        descriptionLabel.setTypeface(null, Typeface.BOLD);
        modalLayout.addView(descriptionLabel);

        final EditText descriptionBox = new EditText(this);
        descriptionBox.setHint("Description");
        descriptionBox.setTextColor(getResources().getColor(R.color.add_result_text_color));
        descriptionBox.setPadding(0, 15, 10, 25);
        descriptionBox.setFilters(new InputFilter[] {new InputFilter.LengthFilter(maxLengthDescriptionText)});
        modalLayout.addView(descriptionBox);

        builder.setView(modalLayout);

        builder.setNeutralButton(R.string.add_result, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                performAddResult(studentData.get(getResources().getString(R.string.student_id)), resultBox.getText().toString(), titleBox.getText().toString(), descriptionBox.getText().toString());
                dialog.cancel();
            }
        });
        builder.setCancelable(true);
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
        alertDialog.getWindow().setLayout(950, WindowManager.LayoutParams.WRAP_CONTENT);

        Button neutralButton = alertDialog.getButton(DialogInterface.BUTTON_NEUTRAL);
        neutralButton.setBackground(getDrawable(R.drawable.default_small_button));
        neutralButton.setPadding(0, 2, 0, 4);
        neutralButton.setX(-225);
        neutralButton.setY(150);
    }

    private void performAddResult(String studentId, String result, String title, String description)
    {
        AddResultForUserAsyncTask addResultForUserAsyncTask = new AddResultForUserAsyncTask(this);
        addResultForUserAsyncTask.execute(studentId, title, description, result);
    }

    public void showResultsList(View view) {
        LinearLayout parentRow = (LinearLayout) view.getParent();
        studentIdTextView = (TextView) parentRow.getChildAt(0);
        studentNameTextView = (TextView) parentRow.getChildAt(1);
        if (studentData.isEmpty() ||
                (!studentData.isEmpty() &&
                        !studentData.get(getResources().getString(R.string.student_id)).equalsIgnoreCase(studentIdTextView.getText().toString()) &&
                        !studentData.get(getResources().getString(R.string.student_name)).equalsIgnoreCase(studentNameTextView.getText().toString()))) {
            studentData.put(getResources().getString(R.string.student_id), studentIdTextView.getText().toString());
            studentData.put(getResources().getString(R.string.student_name), studentNameTextView.getText().toString());
        }

        Intent intent = new Intent(this, ViewResultsActivity.class);
        Bundle extras = new Bundle();
        extras.putSerializable("class", classObject);
        extras.putSerializable("selectedStudent", studentData);
        intent.putExtras(extras);
        startActivity(intent);
    }

    public class MinMaxInputFilter implements InputFilter {

        private float min, max;

        public MinMaxInputFilter(float min, float max) {
            this.min = min;
            this.max = max;
        }

        public MinMaxInputFilter(String min, String max) {
            this.min = Float.parseFloat(min);
            this.max = Float.parseFloat(max);
        }

        @Override
        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
            try {
                float input = Float.parseFloat(dest.toString() + source.toString());
                if (isInRange(min, max, input))
                    return null;
            } catch (NumberFormatException nfe) { }
            return "";
        }

        private boolean isInRange(float a, float b, float c) {
            return b > a ? c >= a && c <= b : c >= b && c <= a;
        }
    }
}
