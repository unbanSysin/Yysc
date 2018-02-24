package com.yysc.activity.dragger;

import com.yysc.activity.MainActivity;
import com.yysc.commomlibary.base.BaseActivity;

import dagger.Component;


/**
 * Created by kj00037 on 2018/1/3.
 */
@Component(modules = {mainModule.class})
public interface AppComponent {
    void inject(MainActivity activity);
}
