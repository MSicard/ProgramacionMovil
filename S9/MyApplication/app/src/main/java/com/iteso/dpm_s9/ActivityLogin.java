package com.iteso.dpm_s9;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.iteso.dpm_s9.beans.User;

public class ActivityLogin extends AppCompatActivity {

    protected EditText username;
    protected EditText password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        username = (EditText)findViewById(R.id.activity_login_username);
        password = (EditText)findViewById(R.id.activity_login_pwd);
    }

    public void onClick(View view){
        switch (view.getId()){
            case R.id.activity_login_signin:
                User user = new User();
                user.setUsername(username.getText().toString());
                user.setPassword(password.getText().toString());
                user.savePreferences(this);
                Intent intent = new Intent(this, ActivityMain.class);
                startActivity(intent);
                finish();
                break;
        }
    }
}
