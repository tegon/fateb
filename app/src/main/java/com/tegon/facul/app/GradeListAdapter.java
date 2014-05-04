package com.tegon.facul.app;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

import java.util.ArrayList;

@EBean
public class GradeListAdapter extends BaseAdapter {
    ArrayList<Grade> grades;

    @RootContext
    Context context;

    public GradeListAdapter() {}

    public void initAdapter(ArrayList<Grade> grades) {
        this.grades = grades;
    }

    @Override
    public int getCount() {
        return grades.size();
    }

    @Override
    public Grade getItem(int position) {
        return grades.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        GradeItemView gradeItemView;

        if (convertView == null) {
            gradeItemView = GradeItemView_.build(context);
        } else {
            gradeItemView = (GradeItemView) convertView;
        }

        gradeItemView.bind(getItem(position), context);

        return gradeItemView;
    }

    public void refill(ArrayList<Grade> grades) {
        grades.clear();
        grades.addAll(grades);
        notifyDataSetChanged();
    }
}
