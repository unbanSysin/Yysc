package com.yysc.login.viewmodel;

import android.databinding.ObservableField;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.yysc.commomlibary.mvvm.BaseViewModel;
import com.yysc.commomlibary.net.HttpApiService;
import com.yysc.commomlibary.net.HttpListener;
import com.yysc.commomlibary.net.HttpResult;
import com.yysc.commomlibary.net.rxlife.ActivityEvent;
import com.yysc.login.Api.HttpGetApi;
import com.yysc.login.activity.LoginActivity;
import com.yysc.login.bean.UserInfoContent;
import com.yysc.login.dagger.DaggerLoginComponent;
import com.yysc.otherlibs.net.BasicRequest;
import com.yysc.otherlibs.utils.DESEncryptUtil;
import com.yysc.otherlibs.utils.SHA256Util;

import java.util.Map;

import javax.inject.Inject;

import rx.Observable;

/**
 * Created by kj00037 on 2018/1/2.
 */

public class LoginViewModel extends BaseViewModel {

    DaggerLoginComponent component;
    @Inject
    LoginActivity mActivity;

    private static final String                  TAG      = "LoginViewModel";
    public final         ObservableField<String> phoneNum = new ObservableField<String>();
    public final         ObservableField<String> password = new ObservableField<String>();

    public LoginViewModel() {

    }

    public LoginViewModel(DaggerLoginComponent component) {
        this.component = component;
        component.injectViewModel(this);
//        phoneNum.set("18823195531");
//        password.set("st123456");
    }

    public void onClickButton(View view) {

        if (!TextUtils.isEmpty(phoneNum.get()) && !TextUtils.isEmpty(password.get())) { login(); }
    }

    public void login() {

        Observable<HttpResult<UserInfoContent>> observable = HttpApiService.getApi(HttpGetApi.class,mActivity)
                                                                           .login(setPara());
        HttpApiService.doNetWork(observable,
                                 mActivity.bindUtilEvent(ActivityEvent.STOP),
                                 new HttpListener<UserInfoContent>() {
                                     @Override
                                     public void onSuccess(UserInfoContent userInfoContent) {
                                         phoneNum.set(userInfoContent.getCustName());
                                         password.set(userInfoContent.getCertNo());

                                     }

                                     @Override
                                     public void onError(String error) {

                                     }

                                     @Override
                                     public void onNetWorkEnd() {

                                     }
                                 });

    }

    public Map<String, String> setPara() {
//        HashMap<String, String> map = new HashMap<String, String>();
        BasicRequest<String, String> request = new BasicRequest<String, String>("userAction/loginUser.do");

        //        map.put("sign", "453eef6c70b5a1de5d5941b9d8f3546cb47f5e7e29f0ce0fc8d7b623c2a5643b");
        //        map.put("timestamp", "1513236084568");
        //        map.put("token", "9570380b-d385-4ff2-be44-9f318dd6fc51");
        //        map.put("url", "apply/querystep.do");
        request.put("phone", DESEncryptUtil.encryptThreeDESECB(phoneNum.get()));
        request.put("password",
                DESEncryptUtil.encryptThreeDESECB(SHA256Util.EncryptMD5(password.get())));
        return request;
    }
}
