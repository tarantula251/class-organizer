package com.example.classorganizer;

import android.content.DialogInterface;
import android.graphics.Typeface;
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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashMap;

public class ShowAssignedStudentsActivity extends AppCompatActivity {
    private HashMap<String, String> classData;
    private ArrayList<HashMap<String, String>> list;
    private ListView listView;
    private int maxLengthDescriptionText = 250;
    public static final String FIRST_COLUMN = "Column 1";
    public static final String SECOND_COLUMN = "Column 2";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assigned_students);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            classData = (HashMap<String, String>) bundle.getSerializable("selectedClass");
        }
        fillInfoBox();
        listView = findViewById(R.id.studentsListView);
        populateList();
        AssignedStudentsAdapter adapter = new AssignedStudentsAdapter(this, list);
        listView.setAdapter(adapter);
    }

    private void fillInfoBox() {
        String course = classData.get(getString(R.string.course));
        String model = classData.get(getString(R.string.model));
        String classes = classData.get(getString(R.string.classes));
        TextView courseTextView = findViewById(R.id.course);
        TextView modelTextView = findViewById(R.id.model);
        TextView classesTextView = findViewById(R.id.classes);
        courseTextView.setText(course);
        modelTextView.setText(model);
        classesTextView.setText(classes);
    }

    private void populateList() {
        list = new ArrayList<>();

        HashMap<String, String> rowFirst = new HashMap<String, String>();
        rowFirst.put(FIRST_COLUMN, getResources().getString(R.string.student_id));
        rowFirst.put(SECOND_COLUMN, getResources().getString(R.string.student_name));
        list.add(rowFirst);

        HashMap<String, String> rowScnd = new HashMap<String, String>();
        rowScnd.put(FIRST_COLUMN,"217097");
        rowScnd.put(SECOND_COLUMN, "Katarzyna Waszczykowska");
        list.add(rowScnd);

        HashMap<String, String> rowThrd = new HashMap<String, String>();
        rowThrd.put(FIRST_COLUMN,"217086");
        rowThrd.put(SECOND_COLUMN, "Wiktor Kacper Wączyński");
        list.add(rowThrd);
        HashMap<String, String> row1 = new HashMap<String, String>();
        row1.put(FIRST_COLUMN,"217086");
        row1.put(SECOND_COLUMN, "Wiktor Kacper Wączyński");
        list.add(row1);
        HashMap<String, String> row2 = new HashMap<String, String>();
        row2.put(FIRST_COLUMN,"217086");
        row2.put(SECOND_COLUMN, "Wiktor Kacper Wączyński");
        list.add(row2);
        HashMap<String, String> row3 = new HashMap<String, String>();
        row3.put(FIRST_COLUMN,"217086");
        row3.put(SECOND_COLUMN, "Wiktor Kacper Wączyński");
        list.add(row3);
        HashMap<String, String> row4 = new HashMap<String, String>();
        row4.put(FIRST_COLUMN,"217086");
        row4.put(SECOND_COLUMN, "Wiktor Kacper Wączyński");
        list.add(row4);
        HashMap<String, String> row5 = new HashMap<String, String>();
        row5.put(FIRST_COLUMN,"217086");
        row5.put(SECOND_COLUMN, "Wiktor Kacper Wączyński");
        list.add(row5);
        HashMap<String, String> row6 = new HashMap<String, String>();
        row6.put(FIRST_COLUMN,"217086");
        row6.put(SECOND_COLUMN, "Wiktor Kacper Wączyński");
        list.add(row6);
        HashMap<String, String> row7 = new HashMap<String, String>();
        row7.put(FIRST_COLUMN,"217086");
        row7.put(SECOND_COLUMN, "Wiktor Kacper Wączyński");
        list.add(row7);
        HashMap<String, String> row8 = new HashMap<String, String>();
        row8.put(FIRST_COLUMN,"217086");
        row8.put(SECOND_COLUMN, "Wiktor Kacper Wączyński");
        list.add(row8);
        HashMap<String, String> row9 = new HashMap<String, String>();
        row9.put(FIRST_COLUMN,"217086");
        row9.put(SECOND_COLUMN, "Wiktor Kacper Wączyński");
        list.add(row9);
        HashMap<String, String> row10 = new HashMap<String, String>();
        row10.put(FIRST_COLUMN,"217086");
        row10.put(SECOND_COLUMN, "Wiktor Kacper Wączyński");
        list.add(row10);
        HashMap<String, String> row11 = new HashMap<String, String>();
        row11.put(FIRST_COLUMN,"217086");
        row11.put(SECOND_COLUMN, "Wiktor Kacper Wączyński");
        list.add(row11);
        HashMap<String, String> row12 = new HashMap<String, String>();
        row12.put(FIRST_COLUMN,"217086");
        row12.put(SECOND_COLUMN, "Wiktor Kacper Wączyński");
        list.add(row12);
    }

    public void showAddResultModal(View view) {
        view.startAnimation(AnimationUtils.loadAnimation(this, R.anim.button_click));
        LinearLayout parentRow = (LinearLayout) view.getParent();
        final TextView studentIdTextView = (TextView) parentRow.getChildAt(0);

        AlertDialog.Builder builder = new AlertDialog.Builder(ShowAssignedStudentsActivity.this, R.style.modalAddResultStyle);
        LinearLayout modalLayout = new LinearLayout(this);
        modalLayout.setPaddingRelative(64,64,64,64);
        modalLayout.setOrientation(LinearLayout.VERTICAL);

        final TextView resultLabel = new TextView(this);
        resultLabel.setText(getResources().getText(R.string.result));
        resultLabel.setTextColor(getResources().getColor(R.color.important_color));
        resultLabel.setTypeface(null, Typeface.BOLD);
        modalLayout.addView(resultLabel);

        final EditText resultBox = new EditText(this);
        resultBox.setHint("Result");
        resultBox.setTextColor(getResources().getColor(R.color.add_result_text_color));
        resultBox.setPadding(0, 15, 10, 25);
        resultBox.setMaxLines(1);
        resultBox.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL);
        resultBox.setFilters(new InputFilter[] {new DecimalDigitsInputFilter(1)});
        modalLayout.addView(resultBox);

        final TextView descriptionLabel = new TextView(this);
        descriptionLabel.setText(getResources().getText(R.string.description));
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
                performAddResult(studentIdTextView.getText().toString(), resultBox.getText().toString(), descriptionBox.getText().toString());
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

    private void performAddResult(String studentId, String result, String description) {

    }

    private class DecimalDigitsInputFilter implements InputFilter {
        Pattern mPattern;

        public DecimalDigitsInputFilter(int digitsAfterZero) {
            mPattern = Pattern.compile("[1-6]{1}+((\\.[0-9]{0," + (digitsAfterZero-1) + "})?)||(\\.)?");
        }

        @Override
        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
            Matcher matcherDot = mPattern.matcher(dest);
            if(!matcherDot.matches())
                return "";
            return null;
        }
    }
}
