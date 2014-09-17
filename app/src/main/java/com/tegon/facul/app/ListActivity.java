package com.tegon.facul.app;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ItemClick;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

@EActivity(R.layout.activity_list)
public class ListActivity extends ActionBarActivity {
    @Bean
    User currentUser;

    FatebCrawler crawler;

    @ViewById
    RelativeLayout progressBar;

    ActionBar actionBar;

    @Bean
    GradeListAdapter adapter;

    @ViewById
    ListView cardListView;

    public void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);

        if (savedInstance == null) {
            startCrawler();
        }
    }

    @Background
    void startCrawler() {
        showProgressBar();
        crawler = new FatebCrawler(currentUser);
        crawler.importGrades();
        removeProgressBar();
        customizeActionBar();
        updateGradeList();
    }

    @UiThread
    void showProgressBar() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @UiThread
    void removeProgressBar() {
        progressBar.setVisibility(View.GONE);
    }

    @UiThread
    void customizeActionBar() {
        actionBar = getSupportActionBar();
        actionBar.setTitle(currentUser.getName());
        actionBar.setSubtitle(currentUser.getCourse());
    }

    @UiThread
    void updateGradeList() {
        adapter.initAdapter(currentUser.getGrades());
        cardListView.setAdapter(adapter);
    }

    @ItemClick
    void cardListViewItemClicked(Grade grade) {
        System.out.println(grade.getName());
        openDetailsActivity(grade);
    }

    void openDetailsActivity(Grade grade) {
        Intent i = new Intent(this, DetailsActivity_.class);
        i.putExtra("Grade", grade);
        startActivity(i);
    }
}
