package com.tegon.facul.app;

import android.content.Context;
import android.widget.FrameLayout;
import android.widget.TextView;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

@EViewGroup(R.layout.list_item)
public class GradeItemView extends FrameLayout {
    @ViewById
    TextView gradeTitle;

    @ViewById
    TextView gradeThumb;

    public GradeItemView(Context context) {
        super(context);
    }

    public void bind(Grade grade, Context context) {
        gradeTitle.setText(grade.getName());
        char first = grade.getName().charAt(0);
        gradeThumb.setText(String.valueOf(first));
    }
}
