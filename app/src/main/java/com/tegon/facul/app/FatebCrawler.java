package com.tegon.facul.app;

import android.util.SparseArray;

import org.apache.commons.lang3.text.WordUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class FatebCrawler {
    private User user;
    private HashMap<String, String> cookies;
    private static final String URL = "http://www2.fateb.br/saladeestudos/aluno/notas.php";
    private static final String REFERER = "http://www2.fateb.br/saladeestudos/aluno/framecentral.php";

    public FatebCrawler(User user) {
        this.user = user;
        setCookies();
    }

    private void setCookies() {
        cookies = new HashMap<String, String>();
        cookies.put("login", user.getLogin());
        cookies.put("senha", user.getPassword());
        cookies.put("tipousuario", "aluno");
        cookies.put("idusuario", user.getLogin());
    }

    public void importGrades() {
        Elements tables = get();

        if (tables == null) {
            handleNetworkError();
        } else {
            parseResponse(tables);
        }
    }

    private void handleNetworkError() {
    }

    private void parseResponse(Elements tables) {
        parseUserTable(tables.first());
        parseGradesTable(tables.last());
    }

    private void parseUserTable(Element userInfo) {
        ArrayList<String> user = new ArrayList<String>();

        for (Element row : userInfo.select("tr")) {
            for (Element td : row.select("td")) {
                user.add(td.text());
            }
        }

        String name = WordUtils.capitalizeFully(user.get(1).replace("Nome: ", ""));
        String course = WordUtils.capitalizeFully(user.get(3).replace("Curso: ", "")).replace("cao", "ção");

        this.user.setInfo(name, course);
    }

    private Elements get() {
        Elements tables = null;

        try {
            Document document = Jsoup
                    .connect(URL)
                    .cookies(cookies)
                    .timeout(30000)
                    .header("Referer", REFERER)
                    .post();
            tables = document.select("table");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return tables;
    }

    public void parseGradesTable(Element gradeInfo) {
        Elements rows = gradeInfo.select("tr");
        ArrayList<ArrayList<String>> grades = new ArrayList<ArrayList<String>>();

        for (Element row : rows) {
            ArrayList<String> grade = new ArrayList<String>();
            for (Element td : row.select("td")) {
                grade.add(td.text());
            }
            grades.add(grade);
        }

        grades.remove(0);

        ArrayList<Grade> gradeList = new ArrayList<Grade>();

        for (ArrayList<String> grade : grades) {
            String name = grade.get(0);
            Integer absences = Integer.parseInt(grade.get(1));
            Float absencesPercentage = parseFloat(grade.get(2));
            Float firstGrade = parseFloat(grade.get(3));
            Float secondGrade = parseFloat(grade.get(4));
            Float average = parseFloat(grade.get(5));
            Float exam = parseFloat(grade.get(6));
            String status = WordUtils.capitalize(grade.get(7));
            String situation = WordUtils.capitalize(grade.get(8));

            Grade newGrade = new Grade(name, absences, absencesPercentage, firstGrade, secondGrade,
                    average, exam, status, situation);
            gradeList.add(newGrade);
        }

        this.user.setGrades(gradeList);
    }

    private Float parseFloat(String string) {
        Float floatValue;

        try {
            floatValue = Float.parseFloat(string.replaceAll(",", ".").replaceAll("%", ""));
        } catch (NumberFormatException e) {
            floatValue = null;
        }

        return floatValue;
    }
}