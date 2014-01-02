package com.tegon.pqpfateb;

import android.app.Activity;
import android.os.Bundle;
import android.util.SparseArray;
import android.os.AsyncTask;
import android.view.Menu;
import android.widget.ExpandableListView;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.app.ActionBar;
import android.annotation.TargetApi;

public class ListActivity extends Activity {
  public static final String PREFS_NAME = "FatebUser";
  public static SharedPreferences SETTINGS;

  @TargetApi(Build.VERSION_CODES.HONEYCOMB)
  private void actionBarSetup() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
      ActionBar ab = getActionBar();
      ab.setTitle("Leonardo Tegon");
      ab.setSubtitle("Sistemas de Informação");
    } else {
      setTitle("Leonardo Tegon");
    }
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_list);

    actionBarSetup();

    SETTINGS = getSharedPreferences(PREFS_NAME, 0);

    SparseArray<Group> groups = null;

    try {
      groups = new GetData().execute().get();
    } catch (Exception e) {
      e.printStackTrace();
    }

    ExpandableListView listView = (ExpandableListView) findViewById(R.id.listView);
    MyExpandableListAdapter adapter = new MyExpandableListAdapter(this, groups);
    listView.setAdapter(adapter);
  }

  private class GetData extends AsyncTask<SparseArray<Group>, Void, SparseArray<Group>> {

  	@Override
  	protected void onPreExecute() {
  		super.onPreExecute();
  	}

  	@Override
  	protected SparseArray<Group> doInBackground(SparseArray<Group>... params) {
  	  return Grades.get(SETTINGS.getString("login", ""), SETTINGS.getString("password", ""));
  	}

  	@Override
  	protected void onPostExecute(SparseArray<Group> groups) {
  	}
  }

}