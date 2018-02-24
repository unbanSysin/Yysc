package com.yysc.activity.dragger;

import com.yysc.activity.TestViewModel;

import dagger.Module;
import dagger.Provides;

/**
 * Created by kj00037 on 2018/1/3.
 */
@Module
public class mainModule {

    @Provides
    public TestViewModel provideViewModel() {
        return new TestViewModel();
    }

}
