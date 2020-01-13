package com.example.classorganizer;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

public class RegisterAttendanceActivity extends AppCompatActivity {
    ProgressBar progressBar;
    ConstraintLayout registerMessageLayout;
    TextView registerConfirmationMessage;
    ImageView registerConfirmationIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_attendance);
        progressBar = findViewById(R.id.progressBar);
        registerMessageLayout = findViewById(R.id.registerMessageLayout);
        registerConfirmationMessage = findViewById(R.id.registerConfirmationMessage);
        registerConfirmationIcon = findViewById(R.id.registerConfirmationIcon);
        //temporary solution
        final boolean isRegistrationSuccess = true;
        new android.os.Handler().postDelayed(
            new Runnable() {
                public void run() {
                    if (isRegistrationSuccess) {
                        showRegistrationSuccess();
                    } else {
                        showRegistrationError();
                    }
                }
            },500);
    }

    private void showRegistrationSuccess() {
        progressBar.setVisibility(View.INVISIBLE);
        registerMessageLayout.setVisibility(View.VISIBLE);
        registerConfirmationMessage.setText(R.string.register_success_message);
        registerConfirmationIcon.setBackground(getDrawable(R.mipmap.tick_icon_round));

    }

    private void showRegistrationError() {
        progressBar.setVisibility(View.INVISIBLE);
        registerMessageLayout.setVisibility(View.VISIBLE);
        registerConfirmationMessage.setText(R.string.register_error_message);
        registerConfirmationIcon.setBackground(getDrawable(R.mipmap.cross_icon_round));
    }
}
