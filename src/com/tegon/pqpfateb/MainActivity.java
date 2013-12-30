package com.tegon.pqpfateb;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;

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
				// Execute TableParser AsyncTask
				new TableParser().execute();
			}

		});
	}

	// TableParser AsyncTask
		private class TableParser extends AsyncTask<Void, Void, Void> {
			Element userInfo;
			Element gradeInfo;
			String text;
			List<String> keys = new ArrayList<String>();
			List<Map<String, String>> grades = new ArrayList<Map<String, String>>();

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

					userInfo = document.select("table").first();
					gradeInfo = document.select("table").last();

					for (Element row : userInfo.select("tr")) {
		        Elements tds = row.select("td");
		        text += tds.text() + " ";
		      }

    			for (Element row : gradeInfo.select("tr")) {
            for (Element td : row.select("td.titulo")) {
            	if (td.text() != null) {
            		keys.add(td.text());
            	}
            }
          }

          for (Element row : gradeInfo.select("tr")) {
          	Map<String, String> grade = new HashMap<String, String>();
          	int i = 0;
          	for (Element td : row.select("td")) {
          		if (!keys.contains(td.text()) && td.text() != null) {
          			grade.put(keys.get(i), td.text());
          			i++;
          		}
          	}
          	grades.add(grade);
          }

				} catch (IOException e) {
					e.printStackTrace();
				}
				return null;
			}

			@Override
			protected void onPostExecute(Void result) {
				TextView textView1 = (TextView) findViewById(R.id.textView1);
				textView1.setText(grades.toString());
			}
		}

}
