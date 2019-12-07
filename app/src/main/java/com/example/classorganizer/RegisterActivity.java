package com.example.classorganizer;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.view.WindowManager.LayoutParams;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
    }

    public void showModal(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this, R.style.modalStyle);
        Boolean isRegistrationSuccess = true;
        if (isRegistrationSuccess) {
            builder.setMessage(getString(R.string.register_modal_success));
            builder.setTitle(getString(R.string.success));
        } else {
            builder.setMessage(getString(R.string.register_modal_error));
            builder.setTitle(getString(R.string.error));
        }
        builder.setNeutralButton(R.string.close, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });
        builder.setCancelable(true);
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
        alertDialog.getWindow().setLayout(950, LayoutParams.WRAP_CONTENT);
        TextView textView = alertDialog.findViewById(android.R.id.message);
        textView.setTextSize(26);
    }
}
