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
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.CheckedChange;
import org.androidannotations.annotations.Bean;

@EActivity(R.layout.activity_main)
public class MainActivity extends Activity {
  @Bean User currentUser;

  @ViewById EditText login;

  @ViewById EditText password;

  @AfterViews
  void registerOrLogInUser() {
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
  }

  @Click
  void send() {
    currentUser.register(login.getText().toString(), password.getText().toString());
    openListActivity();
  }

  @CheckedChange
  void showPassword(CompoundButton buttonView, boolean isChecked) {
    if (!isChecked) {
      password.setTransformationMethod(PasswordTransformationMethod.getInstance());
    } else {
      password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
    }
  }

  public void openListActivity() {
    Intent intent = new Intent(this, ListActivity_.class);
    startActivity(intent);
    finish();
  }
}