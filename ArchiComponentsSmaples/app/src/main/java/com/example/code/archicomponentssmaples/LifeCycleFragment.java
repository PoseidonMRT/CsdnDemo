package com.example.code.archicomponentssmaples;

import android.app.Fragment;
import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.Lifecycle.State;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.LifecycleRegistry;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Created by tuozhaobing on 17/12/2.
 */

public class LifeCycleFragment extends Fragment implements LifecycleOwner {

  private LifecycleRegistry mLifecycleRegistry;

  public LifeCycleFragment(){
  }

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    mLifecycleRegistry = new LifecycleRegistry(this);
    mLifecycleRegistry.markState(State.CREATED);
  }

  @Override
  public void onStart() {
    super.onStart();
    mLifecycleRegistry.markState(State.STARTED);
  }

  @Override
  public void onResume() {
    super.onResume();
    mLifecycleRegistry.markState(State.RESUMED);
  }

  @Override
  public void onDestroy() {
    super.onDestroy();
    mLifecycleRegistry.markState(State.DESTROYED);
  }

  @NonNull
  @Override
  public Lifecycle getLifecycle() {
    return mLifecycleRegistry;
  }
}
