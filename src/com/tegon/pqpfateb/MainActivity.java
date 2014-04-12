package com.tegon.pqpfateb;

import java.lang.Exception;
import com.google.inject.Inject;
import roboguice.activity.RoboActivity;
import roboguice.inject.InjectResource;
import roboguice.inject.InjectView;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.CheckBox;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.TextView.OnEditorActionListener;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;

public class MainActivity extends RoboActivity {
  User currentUser;
  @InjectView(R.id.button1) Button button1;
  @InjectView(R.id.showPassword) CheckBox showPassword;
  @InjectView(R.id.login) EditText login;
  @InjectView(R.id.password) EditText password;
  @Inject ShowPasswordListener showPasswordListener;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    currentUser = new User();

    if (currentUser.isRegistered()) {
      openListActivity();
    }

    password.setOnEditorActionListener(new OnEditorActionListener() {
      @Override
      public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        boolean handled = false;
        if (actionId == EditorInfo.IME_ACTION_SEND) {
          handled = true;
          currentUser.register(login.getText().toString(), password.getText().toString());
          openListActivity();
        }
        return handled;
      }
    });

    button1.setOnClickListener(new OnClickListener() {

  		@Override
  		public void onClick(View v) {
        currentUser.register(login.getText().toString(), password.getText().toString());
        openListActivity();
  		}
	  });

    showPassword.setOnCheckedChangeListener(showPasswordListener);
  }

  public void openListActivity() {
    Intent intent = new Intent(this, ListActivity.class);
    startActivity(intent);
    finish();
  }
}