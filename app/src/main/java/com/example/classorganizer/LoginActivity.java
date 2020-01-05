package com.example.classorganizer;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Process;
import android.view.View;
import android.widget.EditText;

import java.io.IOException;

import model.data.User;
import model.network.Server;
import model.network.ServerException;

public class LoginActivity extends AppCompatActivity {

    private static class ConnectAsyncTask extends AsyncTask<Void, Integer, ServerException>
    {

        LoginActivity loginActivity;
        Server server;

        ConnectAsyncTask(LoginActivity loginActivity, Server server)
        {
            this.loginActivity = loginActivity;
            this.server = server;
        }

        @Override
        protected ServerException doInBackground(Void... voids)
        {
            try
            {
                server.connect();
            }
            catch(IOException e)
            {
                return new ServerException(e.hashCode(), e.getMessage());
            }
            return new ServerException(0, "Success");
        }

        @Override
        protected void onPostExecute(ServerException e)
        {
            if(e.getErrorCode() != 0)
            {
                AlertDialog.Builder connectionFailedDialogBuilder = new AlertDialog.Builder(loginActivity, R.style.modalStyle);
                connectionFailedDialogBuilder.setTitle(loginActivity.getResources().getString(R.string.connection_failed));
                connectionFailedDialogBuilder.setMessage(e.getMessage());
                connectionFailedDialogBuilder.setOnDismissListener(new DialogInterface.OnDismissListener()
                {
                    @Override
                    public void onDismiss(DialogInterface dialog)
                    {
                        Process.killProcess(Process.myPid());
                    }
                });
                AlertDialog connectionFailedDialog = connectionFailedDialogBuilder.create();
                connectionFailedDialog.show();
            }
        }
    }

    private static class  AuthorizeAsyncTask extends AsyncTask<String, AlertDialog.Builder, User>
    {
        LoginActivity loginActivity;
        Server server;

        AuthorizeAsyncTask(LoginActivity loginActivity, Server server)
        {
            this.loginActivity = loginActivity;
            this.server = server;
        }

        @Override
        protected User doInBackground(String... strings)
        {
            try
            {
                return server.authorize(strings[0], strings[1]);
            }
            catch(ServerException e)
            {
                AlertDialog.Builder wrongCredentialsDialogBuilder = new AlertDialog.Builder(loginActivity, R.style.modalStyle);
                wrongCredentialsDialogBuilder.setTitle("SIGN IN");
                wrongCredentialsDialogBuilder.setMessage(e.getMessage());
                publishProgress(wrongCredentialsDialogBuilder);
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(AlertDialog.Builder... values)
        {
            AlertDialog connectionFailedDialog = values[0].create();
            connectionFailedDialog.show();
        }

        @Override
        protected void onPostExecute(User user)
        {
            if(user != null)
            {
                Intent intent = new Intent(loginActivity, MenuActivity.class);
                loginActivity.startActivity(intent);
            }
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Server server = Server.getInstance();
        server.setIpAddress(getResources().getString(R.string.ip_address));
        server.setPort(getResources().getInteger(R.integer.port));
        ConnectAsyncTask connectAsyncTask = new ConnectAsyncTask(this, server);
        connectAsyncTask.execute();
    }

    public void showRegister(View view) {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }

    public void showMenu(View view) {
        EditText emailEditText = findViewById(R.id.email_input);
        EditText passwordEditText = findViewById(R.id.password_input);
        String email = emailEditText.getText().toString();
        String password = passwordEditText.getText().toString();
        AuthorizeAsyncTask authorizeAsyncTask = new AuthorizeAsyncTask(this, Server.getInstance());
        authorizeAsyncTask.execute(email, password);
    }
}
