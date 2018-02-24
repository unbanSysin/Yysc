package com.yysc.login.dagger;

import com.yysc.login.activity.LoginActivity;
import com.yysc.login.viewmodel.LoginViewModel;

import dagger.Component;

/**
 * Created by kj00037 on 2018/1/8.
 */
@Component(modules = {LoginModule.class})
public interface LoginComponent {

    void inject(LoginActivity activity);

    void injectViewModel(LoginViewModel viewModel);
}
