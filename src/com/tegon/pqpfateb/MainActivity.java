package com.tegon.pqpfateb;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.content.Context;
import android.content.SharedPreferences;

public class MainActivity extends Activity {
  public static final String PREFS_NAME = "FatebUser";
  public static SharedPreferences SETTINGS;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    SETTINGS = getSharedPreferences(PREFS_NAME, 0);

    if (getLogin() != "" && getPassword() != "") {
      Intent intent = new Intent(MainActivity.this, ListActivity.class);
      startActivity(intent);
    }

    Button button1 = (Button) findViewById(R.id.button1);
    final EditText login = (EditText) findViewById(R.id.login);
    final EditText password = (EditText) findViewById(R.id.password);

    // final ProgressDialog mProgressDialog = new ProgressDialog(this);
    button1.setOnClickListener(new OnClickListener() {

  		@Override
  		public void onClick(View v) {
  			// mProgressDialog.setMessage("Carregando...");
  			// mProgressDialog.show();
        setLogin(login.getText().toString());
        setPassword(password.getText().toString());

  			Intent intent = new Intent(MainActivity.this, ListActivity.class);
  			startActivity(intent);
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
}