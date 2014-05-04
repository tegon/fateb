package com.tegon.facul.app;

import org.androidannotations.annotations.sharedpreferences.SharedPref;

@SharedPref(value=SharedPref.Scope.UNIQUE)
public interface UserPreferences {
    String login();
    String password();
}
