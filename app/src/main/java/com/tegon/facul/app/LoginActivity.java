package com.tegon.facul.app;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.EditText;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

@EActivity
public class LoginActivity extends ActionBarActivity {
    @ViewById
    EditText login;

    @ViewById
    EditText password;

    @Bean
    User currentUser;

    @Override
    public void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);

        setContentView(R.layout.activity_login);
    }

    @AfterViews
    void registerOrLoginInUser() {
        if (currentUser.isRegistered()) {
            openListActivity();
        }
    }

    @Click
    void send() {
        currentUser.register(login.getText().toString(), password.getText().toString());
        openListActivity();
    }

    void openListActivity() {
        Intent intent = new Intent(this, ListActivity_.class);
        startActivity(intent);
        finish();
    }
}
