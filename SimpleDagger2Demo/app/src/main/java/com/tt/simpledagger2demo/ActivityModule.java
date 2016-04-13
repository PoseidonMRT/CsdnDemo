package com.tt.simpledagger2demo;

import dagger.Module;
import dagger.Provides;

/**
 * Created by tuozhaobing on 16-4-13.
 * Add Some Description There
 */
@Module
public class ActivityModule {
    @Provides
    UserModel provideUserModel() {
        return new UserModel();
    }
}
