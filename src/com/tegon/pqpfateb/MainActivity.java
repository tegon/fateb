package com.tegon.pqpfateb;

import java.lang.Exception;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.TextView.OnEditorActionListener;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.text.method.PasswordTransformationMethod;
import android.text.method.HideReturnsTransformationMethod;

public class MainActivity extends Activity {
  User currentUser;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    currentUser = new User(this);

    if (currentUser.isRegistered()) {
      openListActivity();
    }

    Button button1 = (Button) findViewById(R.id.button1);
    CheckBox showPassword = (CheckBox) findViewById(R.id.showPassword);
    final EditText login = (EditText) findViewById(R.id.login);
    final EditText password = (EditText) findViewById(R.id.password);

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

    showPassword.setOnCheckedChangeListener(new OnCheckedChangeListener() {

      public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (!isChecked) {
          password.setTransformationMethod(PasswordTransformationMethod.getInstance());
        } else {
          password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
        }
      }
    });
  }

  public void openListActivity() {
    Intent intent = new Intent(this, ListActivity.class);
    startActivity(intent);
    finish();
  }
}