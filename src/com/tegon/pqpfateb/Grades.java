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

import android.util.SparseArray;

public class Grades {
  public static SparseArray get(String login, String password) {
    Element gradeInfo = getTable(login, password);
    SparseArray<Group> groups = parseTable(gradeInfo);
    return groups;
  }

  public static Element getTable(String login, String password) {
    Element gradeInfo = null;

    Map<String, String> cookies = new HashMap<String, String>();
    cookies.put("login", login);
    cookies.put("senha", password);
    cookies.put("tipousuario", "aluno");
    cookies.put("idusuario", login);


    try {
      // Connect to the web site
      Document document = Jsoup
        .connect("http://www2.fateb.br/saladeestudos/aluno/notas.php")
        .cookies(cookies)
        .header("Referer", "http://www2.fateb.br/saladeestudos/aluno/framecentral.php")
        .post();

      gradeInfo = document.select("table").last();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return gradeInfo;
  }

  public static SparseArray parseTable(Element gradeInfo) {
    SparseArray<Group> groups = new SparseArray<Group>();

    Elements rows = gradeInfo.select("tr");
    ArrayList<String> keys = new ArrayList<String>();
    ArrayList<ArrayList<String>> grades = new ArrayList<ArrayList<String>>();

    // save keys: DISCIPLINAS, Faltas
    for (Element row : rows) {
      for (Element td : row.select("td.titulo")) {
        keys.add(td.text());
      }
    }
    // add all rows and their columns to grades list
    for (Element row : rows) {
      ArrayList<String> grade = new ArrayList<String>();
      for (Element td : row.select("td")) {
        grade.add(td.text());
      }
      grades.add(grade);
    }
    // remove all keys from grades array
    grades.remove(0);
    keys.remove(0);

    //remove all first position
    int i = 0;
    for (ArrayList<String> grade : grades) {
      Group group = new Group(grade.get(0));
      grade.remove(0);
      int j = 0;
      for (String value : grade) {
        group.children.add(keys.get(j) + ": " + value);
        j++;
      }
      groups.append(i, group);
      i++;
    }
    return groups;
  }
}