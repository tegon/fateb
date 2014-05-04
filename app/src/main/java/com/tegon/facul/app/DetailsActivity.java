package com.tegon.facul.app;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.widget.TextView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

@EActivity(R.layout.activity_details)
public class DetailsActivity extends ActionBarActivity {
    Grade grade;
    ActionBar actionBar;

    @ViewById
    TextView textView;

    @ViewById
    TextView textView2;

    @ViewById
    TextView textView3;

    public void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);

        grade = (Grade) getIntent().getSerializableExtra("Grade");
        customizeActionBar();
    }

    @AfterViews
    void setGradeDetails() {
        textView.setText(grade.getAbsences().toString());
        textView2.setText(grade.getSituation());
        textView3.setText(grade.getAbsencesPercentage().toString());
    }

    void customizeActionBar() {
        actionBar = getSupportActionBar();
        actionBar.setTitle(grade.getName());
    }
}
