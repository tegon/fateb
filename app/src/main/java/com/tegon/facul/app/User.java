package com.tegon.facul.app;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.sharedpreferences.Pref;

import java.util.ArrayList;

@EBean
public class User {
    private String name;
    private String course;
    private ArrayList<Grade> grades;

    @Pref
    UserPreferences_ userPreferences;

    public User() {}

    public void register(String login, String password) {
        setLogin(login);
        setPassword(password);
    }

    public boolean isRegistered() {
        System.out.println("is registered " + getLogin() + getPassword());
        return this.getLogin() != "" && this.getPassword() != "";
    }

    public void setLogin(String login) {
        userPreferences.login().put(login);
    }

    public void setPassword(String password) {
        userPreferences.password().put(password);
    }

    public String getLogin() {
        return userPreferences.login().get();
    }

    public String getPassword() {
        return userPreferences.password().get();
    }

    public ArrayList<Grade> getGrades() {
        return grades;
    }

    public void setGrades(ArrayList<Grade> grades) {
        this.grades = grades;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public void setInfo(String name, String course) {
        this.name = name;
        this.course = course;
    }
}
