package com.tt.simpledagger2demo;

import dagger.Component;

/**
 * Created by tuozhaobing on 16-4-13.
 * Add Some Description There
 */
@Component(modules = ActivityModule.class)
public interface ActivityComponent {
    void inject(MainActivity mainActivity);
}
