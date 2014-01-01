package com.tegon.pqpfateb;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

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

    int i = 0;
    for (Element row : gradeInfo.select("tr")) {
      Group group = new Group(row.select("td").first().text());
      for (Element td : row.select("td")) {
        group.children.add(td.text());
      }
      groups.append(i, group);
      i++;
    }
    return groups;
  }
}