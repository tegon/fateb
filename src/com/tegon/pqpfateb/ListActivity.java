package com.tegon.pqpfateb;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.util.SparseArray;
import android.os.AsyncTask;
import android.widget.ExpandableListView;
import android.os.Build;
import android.app.ActionBar;
import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Intent;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.widget.Toast;

import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.OptionsMenu;
import org.androidannotations.annotations.OptionsItem;
import org.androidannotations.annotations.Bean;

@EActivity(R.layout.activity_list)
@OptionsMenu(R.menu.activity_list)
public class ListActivity extends Activity {
  @Bean User currentUser;

  ProgressDialog progressDialog;
  SparseArray<Group> groups = null;
  ArrayList<Object> response = null;

  @OptionsItem
  void resetLogin() {
    AlertDialog alertDialog = new AlertDialog.Builder(this).create();
    alertDialog.setMessage("Tem certeza que deseja apagar o login e senha salvos?");
    alertDialog.setCancelable(true);

    alertDialog.setButton("Sim", new DialogInterface.OnClickListener() {
      public void onClick(DialogInterface dialog, int which) {
        currentUser.resetLogin();
        Toast.makeText(ListActivity.this, "Login e senha apagados!", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(ListActivity.this, MainActivity_.class);
        startActivity(intent);
        finish();
      }
    });

    alertDialog.setButton2("Cancelar", new DialogInterface.OnClickListener() {
      public void onClick(DialogInterface dialog, int which) {
      }
    });
    alertDialog.show();
  }

  @OptionsItem
  void about() {
    AlertDialog about = new AlertDialog.Builder(this).create();
    about.setMessage("App desenvolvido por Leonardo Tegon, para facilitar o acesso às notas e faltas da Fateb Birigui pelo celular.\nDúvidas, críticas e sugestões: ltegon93@gmail.com.");
    about.setButton("OK", new DialogInterface.OnClickListener() {
      public void onClick(DialogInterface dialog, int which) {
      }
    });
    about.show();
  }

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

    try {
      GetData request = new GetData(new Callback() {
        public void run(Object result) {
          if (result != null) {
            response = (ArrayList<Object>) result;
            actionBarSetup((ArrayList<String>) response.get(0));
            groups = (SparseArray<Group>) response.get(1);
            initializeListView();
          } else {
            showNetworkError();
          }
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

  public void showNetworkError() {
    Toast.makeText(this, "Ocorreu um erro ao conectar com a internet, por favor verifique sua conexão.", Toast.LENGTH_SHORT).show();
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