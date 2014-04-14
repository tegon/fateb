package com.tegon.pqpfateb;

import android.content.SharedPreferences;
import android.app.Activity;

public class User {
  SharedPreferences settings;
  Activity activity;
  static final String PREFS_NAME = "FatebUser";

  public User(Activity activity) {
    this.activity = activity;
    this.settings = activity.getSharedPreferences(PREFS_NAME, 0);
  }

  public void editSettings(String key, String value) {
    SharedPreferences.Editor editor = settings.edit();
    editor.putString(key, value);
    editor.commit();
  }

  public String getLogin() {
    return settings.getString("login", "");
  }

  public String getPassword() {
    return settings.getString("password", "");
  }

  public void setLogin(String login) {
    editSettings("login", login);
  }

  public void setPassword(String password) {
    editSettings("password", password);
  }

  public Boolean isRegistered() {
    return getLogin() != "" && getPassword() != "";
  }

  public void resetLogin() {
    editSettings("login", "");
    editSettings("password", "");
  }

  public void register(String login, String password) {
    setLogin(login);
    setPassword(password);
  }
}