package com.tegon.pqpfateb;

import java.lang.Exception;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.widget.TextView.OnEditorActionListener;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;

public class MainActivity extends Activity {
  public static final String PREFS_NAME = "FatebUser";
  public static SharedPreferences SETTINGS;
  ProgressDialog progressDialog = null;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    SETTINGS = getSharedPreferences(PREFS_NAME, 0);

    if (getLogin() != "" && getPassword() != "") {
      new OpenListActivity().execute();
    }

    Button button1 = (Button) findViewById(R.id.button1);
    final EditText login = (EditText) findViewById(R.id.login);
    final EditText password = (EditText) findViewById(R.id.password);

    password.setOnEditorActionListener(new OnEditorActionListener() {
      @Override
      public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        boolean handled = false;
        if (actionId == EditorInfo.IME_ACTION_SEND) {
          handled = true;
          setLogin(login.getText().toString());
          setPassword(password.getText().toString());
          new OpenListActivity().execute();
        }
        return handled;
      }
    });

    button1.setOnClickListener(new OnClickListener() {

  		@Override
  		public void onClick(View v) {
        setLogin(login.getText().toString());
        setPassword(password.getText().toString());

        new OpenListActivity().execute();
  		}
	  });
  }

  public void editSettings(String key, String value) {
    SharedPreferences.Editor editor = SETTINGS.edit();
    editor.putString(key, value);
    editor.commit();
  }

  public String getLogin() {
    return SETTINGS.getString("login", "");
  }

  public String getPassword() {
    return SETTINGS.getString("password", "");
  }

  public void setLogin(String login) {
    editSettings("login", login);
  }

  public void setPassword(String password) {
    editSettings("password", password);
  }

  public void showDialog() {
    progressDialog = ProgressDialog.show(this, "Fateb", "Carregando...", true, false);
  }

  public void removeDialog() {
    progressDialog.dismiss();
  }

  private class OpenListActivity extends AsyncTask<String, Integer, Boolean> {
    @Override
    protected Boolean doInBackground(String... params) {
      try {
        Intent intent = new Intent(MainActivity.this, ListActivity.class);
        startActivity(intent);
      } catch (Exception e) {
        e.printStackTrace();
      }
      return Boolean.TRUE;
    }

    @Override
    protected void onPreExecute() {
      showDialog();
    }

    @Override
    protected void onPostExecute(Boolean result) {
      removeDialog();
      finish();
    }
  }
}