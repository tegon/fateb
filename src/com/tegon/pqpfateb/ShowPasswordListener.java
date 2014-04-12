package com.tegon.pqpfateb;

import roboguice.inject.InjectResource;
import roboguice.inject.InjectView;

import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.text.method.PasswordTransformationMethod;
import android.text.method.HideReturnsTransformationMethod;

public class ShowPasswordListener implements OnCheckedChangeListener {
  @InjectView(R.id.password) EditText password;

  @Override
  public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
    if (!isChecked) {
      password.setTransformationMethod(PasswordTransformationMethod.getInstance());
    } else {
      password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
    }
  }
}