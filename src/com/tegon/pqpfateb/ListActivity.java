package com.tegon.pqpfateb;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.util.SparseArray;
import android.os.AsyncTask;
import android.view.Menu;
import android.widget.ExpandableListView;
import android.content.Context;
import android.os.Build;
import android.app.ActionBar;
import android.annotation.TargetApi;
import android.app.ProgressDialog;

public class ListActivity extends Activity {
  User currentUser;
  ProgressDialog progressDialog;
  SparseArray<Group> groups = null;
  ArrayList<Object> response = null;

  @TargetApi(Build.VERSION_CODES.HONEYCOMB)
  private void actionBarSetup(ArrayList<String> user) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
      ActionBar ab = getActionBar();
      ab.setTitle(user.get(1).replace("Nome: ", ""));
      ab.setSubtitle(user.get(3).replace("Curso: ", ""));
    } else {
      setTitle(user.get(1).replace("Nome: ", ""));
    }
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_list);

    currentUser = new User(this);

    try {
      GetData request = new GetData(new Callback() {
        public void run(Object result) {
          response = (ArrayList<Object>) result;
          actionBarSetup((ArrayList<String>) response.get(0));
          groups = (SparseArray<Group>) response.get(1);
          initializeListView();
        }
      });
      request.execute();

    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void initializeListView() {
    ExpandableListView listView = (ExpandableListView) findViewById(R.id.listView);
    GroupExpandableListAdapter adapter = new GroupExpandableListAdapter(this, groups);
    listView.setAdapter(adapter);
  }

  public void showDialog() {
    progressDialog = ProgressDialog.show(this, "", "Carregando...", true, false);
  }

  public void removeDialog() {
    progressDialog.dismiss();
  }

  private class GetData extends AsyncTask<ArrayList<Object>, Void, ArrayList<Object>> {
    Callback callback;

    public GetData(Callback callback) {
      this.callback = callback;
    }

  	@Override
  	protected void onPreExecute() {
  		super.onPreExecute();
      showDialog();
  	}

  	@Override
  	protected ArrayList<Object> doInBackground(ArrayList<Object>... params) {
  	  return Grades.get(currentUser.getLogin(), currentUser.getPassword());
  	}

  	@Override
  	protected void onPostExecute(ArrayList<Object> groups) {
      callback.run(groups);
      removeDialog();
  	}
  }

}