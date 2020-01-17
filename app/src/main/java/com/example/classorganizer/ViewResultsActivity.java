package com.example.classorganizer;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.InputType;
import android.text.Spanned;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import model.data.Class;
import model.data.Result;
import model.data.User;
import model.network.Server;
import model.network.ServerException;

public class ViewResultsActivity extends AppCompatActivity {
    private Class classObject;
    private HashMap<String, String> studentData;
    private RecyclerView recyclerView;
    private ViewResultsAdapter adapter;
    private ConstraintSet constraintSetBeforeEditClick;
    private EditText resultEditText;
    private int maxLengthResultText = 3;
    private boolean saveAllowed = false;
    private boolean editAllowed = true;
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-YYYY HH:mm");

    static private class GetResultsAsyncTask extends AsyncTask<String, Integer, ArrayList<Result>>
    {
        ViewResultsActivity viewResultsActivity;

        public GetResultsAsyncTask(ViewResultsActivity viewResultsActivity)
        {
            this.viewResultsActivity = viewResultsActivity;
        }

        @Override
        protected ArrayList<Result> doInBackground(String... strings)
        {
            if(strings.length == 0) return null;
            Server server = Server.getInstance();
            User user = server.getUserForEmailId(strings[0]);
            return server.getResultsForUserClass(user, viewResultsActivity.classObject);
        }

        @Override
        protected void onPostExecute(ArrayList<Result> results)
        {
            if(results == null) return;
            viewResultsActivity.adapter = new ViewResultsAdapter(results, viewResultsActivity);
            viewResultsActivity.recyclerView.setAdapter(viewResultsActivity.adapter);
        }
    }

    static private class UpdateResultAsyncTask extends AsyncTask<String, Integer, ServerException>
    {
        ViewResultsActivity viewResultsActivity;
        private int id;
        private float score;

        public UpdateResultAsyncTask(ViewResultsActivity viewResultsActivity, int id, float score)
        {
            this.viewResultsActivity = viewResultsActivity;
            this.id = id;
            this.score = score;
        }

        @Override
        protected ServerException doInBackground(String... strings)
        {
            if(strings.length != 2) return new ServerException(-1, "Invalid or lack of arguments");
            Server server = Server.getInstance();
            try
            {
                server.updateResult(id, strings[0], strings[1], score);
            }
            catch(ServerException e)
            {
                return e;
            }
            return null;
        }

        @Override
        protected void onPostExecute(ServerException e)
        {
            if(e != null)
            {
                AlertDialog.Builder builder = new AlertDialog.Builder(viewResultsActivity, R.style.modalStyle);
                builder.setMessage("Update result failed.");
                builder.setMessage(e.getMessage());
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            classObject = (Class) bundle.getSerializable("class");
            studentData = (HashMap<String, String>) bundle.getSerializable("selectedStudent");
        }
        fillStudentBox();
        recyclerView = findViewById(R.id.resultsRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        GetResultsAsyncTask getResultsAsyncTask = new GetResultsAsyncTask(this);
        getResultsAsyncTask.execute(studentData.get(getResources().getString(R.string.student_id)));
    }

    private void fillStudentBox() {
        String studentName = studentData.get(getResources().getString(R.string.student_name));
        String studentId = studentData.get(getResources().getString(R.string.student_id));
        TextView studentNameTextView = findViewById(R.id.resultsStudentName);
        TextView studentIdTextView = findViewById(R.id.resultsStudentId);
        studentNameTextView.setText(studentName);
        studentIdTextView.setText(studentId);
    }

    public void performEditResult(View view) {
        if (editAllowed && !saveAllowed) {
            ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) view.getLayoutParams();
            params.setMarginStart(16);
            params.setMarginEnd(16);
            ConstraintLayout constraintLayout = (ConstraintLayout) view.getParent();
            GridLayout gridLayout = constraintLayout.findViewById(R.id.resultGrid);

            constraintSetBeforeEditClick = new ConstraintSet();
            constraintSetBeforeEditClick.clone(constraintLayout);

            ConstraintSet constraintSet = new ConstraintSet();
            constraintSet.clone(constraintLayout);
            constraintSet.connect(R.id.edit_button,ConstraintSet.END,R.id.save_button,ConstraintSet.START,16);
            constraintSet.connect(R.id.save_button,ConstraintSet.START,R.id.edit_button,ConstraintSet.END,16);
            constraintSet.applyTo(constraintLayout);
            view.setLayoutParams(params);

            Button saveBtn = constraintLayout.findViewById(R.id.save_button);
            if (saveBtn.getVisibility() == View.GONE) {
                saveBtn.setVisibility(View.VISIBLE);
            }

            resultEditText = gridLayout.findViewById(R.id.resultValue);
            resultEditText.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL);
            resultEditText.setFocusable(true);
            resultEditText.setFocusableInTouchMode(true);
            resultEditText.setClickable(true);
            resultEditText.setCursorVisible(true);
            resultEditText.setSelection(resultEditText.getText().length());
            resultEditText.requestFocus();
            //add filters
            InputFilter lengthInputFilter = new InputFilter.LengthFilter(maxLengthResultText);
            resultEditText.setFilters(new InputFilter[] {lengthInputFilter, new DecimalDigitsInputFilter()});

            EditText titleEditText = gridLayout.findViewById(R.id.titleValue);
            titleEditText.setInputType(InputType.TYPE_TEXT_FLAG_CAP_SENTENCES);
            titleEditText.setFocusable(true);
            titleEditText.setFocusableInTouchMode(true);
            titleEditText.setClickable(true);
            titleEditText.setCursorVisible(true);
            titleEditText.setSelection(titleEditText.getText().length());

            EditText dateEditText = gridLayout.findViewById(R.id.dateValue);
            dateEditText.getText().clear();

            EditText descriptionEditText = gridLayout.findViewById(R.id.descriptionValue);
            descriptionEditText.setInputType(InputType.TYPE_TEXT_FLAG_CAP_SENTENCES | InputType.TYPE_TEXT_FLAG_MULTI_LINE);
            descriptionEditText.setFocusable(true);
            descriptionEditText.setFocusableInTouchMode(true);
            descriptionEditText.setClickable(true);
            descriptionEditText.setCursorVisible(true);
            descriptionEditText.setSelection(descriptionEditText.getText().length());
            descriptionEditText.setMaxLines(3);
            descriptionEditText.setHorizontallyScrolling(false);

        }
        editAllowed = false;
        saveAllowed = true;
    }

    public void performSaveResult(View view) {
        if (saveAllowed && !editAllowed) {
            //hide soft keyboard
            InputMethodManager inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.hideSoftInputFromWindow((null == getCurrentFocus()) ? null : getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

            ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) view.getLayoutParams();
            ConstraintLayout constraintLayout = (ConstraintLayout) view.getParent();
            GridLayout gridLayout = constraintLayout.findViewById(R.id.resultGrid);

            EditText titleEditText = gridLayout.findViewById(R.id.titleValue);
            titleEditText.setInputType(InputType.TYPE_NULL);
            titleEditText.setFocusable(false);
            titleEditText.setFocusableInTouchMode(false);
            titleEditText.setClickable(false);
            titleEditText.setCursorVisible(false);

            EditText resultIdEditText = constraintLayout.findViewById(R.id.resultId);

            Button saveBtn = constraintLayout.findViewById(R.id.save_button);
            if (saveBtn.getVisibility() == View.VISIBLE) {
                saveBtn.setVisibility(View.GONE);
            }
            ConstraintSet constraintSet = constraintSetBeforeEditClick;
            constraintSet.clear(R.id.save_button,ConstraintSet.START);
            constraintSet.clear(R.id.edit_button,ConstraintSet.END);
            constraintSet.connect(R.id.edit_button,ConstraintSet.END,R.id.resultConstraintLayout,ConstraintSet.END,234);
            constraintSet.connect(R.id.edit_button,ConstraintSet.START,R.id.resultConstraintLayout,ConstraintSet.START,234);
            constraintSet.applyTo(constraintLayout);
            view.setLayoutParams(params);

            resultEditText = gridLayout.findViewById(R.id.resultValue);
            resultEditText.setInputType(InputType.TYPE_NULL);
            resultEditText.setFocusable(false);
            resultEditText.setFocusableInTouchMode(false);
            resultEditText.setClickable(false);
            resultEditText.setCursorVisible(false);
            resultEditText.setMaxLines(3);
            resultEditText.setMovementMethod(new ScrollingMovementMethod());
            resultEditText.refreshDrawableState();

            EditText dateEditText = gridLayout.findViewById(R.id.dateValue);
            String date = dateFormat.format(new Date());
            dateEditText.setText(date);

            EditText descriptionEditText = gridLayout.findViewById(R.id.descriptionValue);
            descriptionEditText.setInputType(InputType.TYPE_NULL);
            descriptionEditText.setFocusable(false);
            descriptionEditText.setFocusableInTouchMode(false);
            descriptionEditText.setClickable(false);
            descriptionEditText.setCursorVisible(false);

            performUpdateResult(Integer.parseInt(resultIdEditText.getText().toString()), titleEditText.getText().toString(),  descriptionEditText.getText().toString(), Float.parseFloat(resultEditText.getText().toString()));
        }
        editAllowed = true;
        saveAllowed = false;
    }

    private void performUpdateResult(int resultId, String title, String description, float score)
    {
        UpdateResultAsyncTask updateResultAsyncTask = new UpdateResultAsyncTask(this, resultId, score);
        updateResultAsyncTask.execute(title, description);
    }

    private class DecimalDigitsInputFilter implements InputFilter {
        Pattern mPattern;

        public DecimalDigitsInputFilter() {
            mPattern = Pattern.compile("[1-6]?(\\.)?[0|5]?");
        }

        @Override
        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
            Matcher matcherDot = mPattern.matcher(dest);
            if(!matcherDot.matches()) {
                resultEditText.setTextColor(getResources().getColor(R.color.error));
                return "";
            }
            resultEditText.setTextColor(getResources().getColor(R.color.add_result_text_color));
            return null;
        }
    }
}
