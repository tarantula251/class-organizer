package com.example.classorganizer;

import android.content.Context;
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

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ViewResultsActivity extends AppCompatActivity {
    private HashMap<String, String> classData;
    private HashMap<String, String> studentData;
    private RecyclerView recyclerView;
    private ViewResultsAdapter adapter;
    private ArrayList<HashMap<String, ArrayList<String>>> resultsList;
    private ConstraintSet constraintSetBeforeEditClick;
    private EditText resultEditText;
    private int maxLengthResultText = 3;
    private boolean saveAllowed = false;
    private boolean editAllowed = true;
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-YYYY HH:mm:ss");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            classData = (HashMap<String, String>) bundle.getSerializable("selectedClass");
            studentData = (HashMap<String, String>) bundle.getSerializable("selectedStudent");
        }
        fillStudentBox();
        resultsList = new ArrayList<>();
        populateDataList();
        recyclerView = findViewById(R.id.resultsRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ViewResultsAdapter(this, resultsList);
        recyclerView.setAdapter(adapter);
    }

    private void fillStudentBox() {
        String studentName = studentData.get(getResources().getString(R.string.student_name));
        String studentId = studentData.get(getResources().getString(R.string.student_id));
        TextView studentNameTextView = findViewById(R.id.resultsStudentName);
        TextView studentIdTextView = findViewById(R.id.resultsStudentId);
        studentNameTextView.setText(studentName);
        studentIdTextView.setText(studentId);
    }

    private void populateDataList() {
        HashMap<String, ArrayList<String>> resultData = new HashMap<>();

        ArrayList<String> result = new ArrayList<>();
        result.add("4.0");
        resultData.put(getString(R.string.result), result);

        ArrayList<String> date = new ArrayList<>();
        date.add(dateFormat.format(new Date()));
        resultData.put(getString(R.string.added_date), date);

        ArrayList<String> description = new ArrayList<>();
        description.add("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nunc et pharetra lorem, in ultricies nunc. Lorem ipsum posuere.");
        resultData.put(getString(R.string.description), description);

        HashMap<String, ArrayList<String>> resultData2 = new HashMap<>();

        ArrayList<String> result2 = new ArrayList<>();
        result2.add("3.5");
        resultData2.put(getString(R.string.result), result2);

        ArrayList<String> date2 = new ArrayList<>();
        date2.add(dateFormat.format(new Date()));
        resultData2.put(getString(R.string.added_date), date2);

        ArrayList<String> description2 = new ArrayList<>();
        description2.add("Quisque a purus et purus commodo ultricies eu pharetra mauris. Cras ac neque ac justo congue nullam.");
        resultData2.put(getString(R.string.description), description2);

        resultsList.add(resultData);
        resultsList.add(resultData2);
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

            performUpdateResult(resultEditText.getText().toString(), date, descriptionEditText.getText().toString());
        }
        editAllowed = true;
        saveAllowed = false;
    }

    private void performUpdateResult(String result, String addedDate, String description) {
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
