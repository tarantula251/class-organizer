package com.example.classorganizer;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

public class MenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
    }

    public void showFilterCoursesAttendance(View view) {
        Intent intent = new Intent(this, AttendanceActivity.class);
        startActivity(intent);
    }

    public void showViewCourses(View view) {
        Intent intent = new Intent(this, ViewCoursesActivity.class);
        startActivity(intent);
    }

    public void showAccount(View view) {
        Intent intent = new Intent(this, AccountActivity.class);
        startActivity(intent);
    }

    public void showLogoutModal(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(MenuActivity.this, R.style.modalStyle);
        builder.setMessage(getString(R.string.logout_question));
        builder.setTitle(getString(R.string.empty_string));
        builder.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });
        builder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                performLogout();
                dialog.cancel();
            }
        });
        builder.setCancelable(false);

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
        alertDialog.getWindow().setLayout(950, WindowManager.LayoutParams.WRAP_CONTENT);
        TextView textView = alertDialog.findViewById(android.R.id.message);
        textView.setTextSize(26);
        textView.setPadding(30, 80, 30, 0);
        //Change style of buttons
        Button positiveButton = alertDialog.getButton(DialogInterface.BUTTON_POSITIVE);
        positiveButton.setBackground(getDrawable(R.drawable.default_small_button));
        positiveButton.setX(-450);
        positiveButton.setY(100);

        Button negativeButton = alertDialog.getButton(DialogInterface.BUTTON_NEGATIVE);
        negativeButton.setBackground(getDrawable(R.drawable.cancel_small_button));
        negativeButton.setX(0);
        negativeButton.setY(-50);
    }

    private void performLogout() {
        Intent intent = new Intent(MenuActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}
