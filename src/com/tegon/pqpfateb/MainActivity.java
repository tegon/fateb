package com.tegon.pqpfateb;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.ProgressDialog;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		Button button1 = (Button) findViewById(R.id.button1);

		button1.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// Execute Title AsyncTask
				new Title().execute();
			}

		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	// Title AsyncTask
		private class Title extends AsyncTask<Void, Void, Void> {
			String title;
			String url;
			String table;
			@Override
			protected void onPreExecute() {
				super.onPreExecute();
			}

			@Override
			protected Void doInBackground(Void... params) {
				Map<String, String> cookies = new HashMap<String, String>();
				cookies.put("login", "login");
				cookies.put("senha", "passwd");
				cookies.put("tipousuario", "aluno");
				cookies.put("idusuario", "login");
				try {
					// Connect to the web site
					Document document = Jsoup
							.connect("http://www2.fateb.br/saladeestudos/aluno/notas.php")
							.cookies(cookies)
							.header("Referer", "http://www2.fateb.br/saladeestudos/aluno/framecentral.php")
							.post();
					// Get the html document title
					title = document.title();
					url = document.location();
					table = document.select("table").toString();
				} catch (IOException e) {
					e.printStackTrace();
				}
				return null;
			}

			@Override
			protected void onPostExecute(Void result) {
				TextView textView1 = (TextView) findViewById(R.id.textView1);
				textView1.setText(table);
			}
		}

}
