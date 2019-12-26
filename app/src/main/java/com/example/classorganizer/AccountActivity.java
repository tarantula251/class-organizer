package com.example.classorganizer;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.InputType;
import android.text.method.PasswordTransformationMethod;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import model.data.User;
import model.data.UserType;

public class AccountActivity extends AppCompatActivity {

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        initData();
        Button showPasswordBtn = findViewById(R.id.showPasswordBtn);
        showPasswordBtn.setOnTouchListener(new View.OnTouchListener() {
            final TextView passwordView = findViewById(R.id.password);

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_UP:
                        passwordView.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                        break;

                    case MotionEvent.ACTION_DOWN:
                        passwordView.setInputType(InputType.TYPE_CLASS_TEXT);
                        break;
                }
                return true;
            }
        });
    }

    private void initData() {
        User currentUser = new User(1, "Karolina", "Wiktorowicz", "k.wiktorowicz@edu.p.lodz.pl", new UserType(1, "student"));
        String password = "alamakota40%";

        TextView nameView = findViewById(R.id.name);
        nameView.setText(currentUser.getFirstName());
        TextView lastnameView = findViewById(R.id.lastname);
        lastnameView.setText(currentUser.getLastName());
        TextView emailView = findViewById(R.id.email);
        emailView.setText(currentUser.getEmail());
        TextView passwordView = findViewById(R.id.password);
        passwordView.setTransformationMethod(PasswordTransformationMethod.getInstance());
        passwordView.setText(password);
    }
}
