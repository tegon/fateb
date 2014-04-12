package com.tegon.pqpfateb;

import com.google.inject.Inject;
import com.google.inject.AbstractModule;
import roboguice.inject.SharedPreferencesName;

public class Application extends AbstractModule {
  @Override
  protected void configure() {
    bindConstant()
      .annotatedWith(SharedPreferencesName.class)
      .to("FatebUser");
  }
}