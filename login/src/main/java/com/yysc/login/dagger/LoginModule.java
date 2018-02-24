package com.yysc.login.dagger;

import com.yysc.commomlibary.base.BaseActivity;
import com.yysc.login.activity.LoginActivity;
import com.yysc.login.viewmodel.LoginViewModel;

import dagger.Module;
import dagger.Provides;

/**
 * Created by kj00037 on 2018/1/8.
 */
@Module
public class LoginModule {

    public DaggerLoginComponent component;
    public LoginActivity activity;

    public LoginModule() {

    }

    public LoginModule(LoginActivity activity) {
        this.activity = activity;
    }

    @Provides
    public LoginActivity provideActivity() {
        return this.activity;
    }

    @Provides
    public LoginViewModel provideLoginViewModel() {
        return new LoginViewModel(component);
    }

    public void setComponent(DaggerLoginComponent component) {
        this.component = component;
    }
}
