package com.tegon.pqpfateb;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.SparseArray;
import android.os.AsyncTask;
import android.view.Menu;
import android.widget.ExpandableListView;

public class MainActivity extends Activity {
  ProgressDialog mProgressDialog;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    mProgressDialog = new ProgressDialog(this);
    mProgressDialog.setMessage("Carregando...");
    mProgressDialog.show();

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
  	  return Grades.get();
  	}

  	@Override
  	protected void onPostExecute(SparseArray<Group> groups) {
      mProgressDialog.dismiss();
  	}
  }

}