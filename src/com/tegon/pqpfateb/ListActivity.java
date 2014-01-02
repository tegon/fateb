package com.tegon.pqpfateb;

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

    currentUser = new User(this);

    try {
      GetData request = new GetData(new Callback() {
        public void run(Object result) {
          groups = (SparseArray<Group>) result;
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

  private class GetData extends AsyncTask<SparseArray<Group>, Void, SparseArray<Group>> {
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
  	protected SparseArray<Group> doInBackground(SparseArray<Group>... params) {
  	  return Grades.get(currentUser.getLogin(), currentUser.getPassword());
  	}

  	@Override
  	protected void onPostExecute(SparseArray<Group> groups) {
      callback.run(groups);
      removeDialog();
  	}
  }

}