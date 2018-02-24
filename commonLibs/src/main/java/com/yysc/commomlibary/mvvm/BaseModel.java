package com.yysc.commomlibary.mvvm;

import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.util.Log;

import com.yysc.commomlibary.net.HttpApiService;
import com.yysc.commomlibary.net.HttpListener;
import com.yysc.commomlibary.net.HttpResult;
import com.yysc.commomlibary.net.rxlife.ActivityEvent;
import com.yysc.commomlibary.net.rxlife.RxAppCompatActivity;

import java.util.List;
import java.util.Map;

import rx.Observable;

import static android.content.ContentValues.TAG;


/**
 * Created by kj00037 on 2017/12/22.
 * 只做网络请求
 */

public abstract class BaseModel<T,V> extends LiveData<T>{

    public abstract Map<String,String> setPara();
    public abstract Class<V> getServiceCls();
    public abstract Observable<HttpResult<T>> getObservable();

    @Override
    public void observe(LifecycleOwner owner, Observer<T> observer) {
        super.observe(owner, observer);
    }

    public void net(RxAppCompatActivity activity, final Observer observer) {

//        Observable<HttpResult<K>> data = HttpApiService.getApi(getServiceCls())
//                                                                 .getAppCommentData(setPara());
        HttpApiService.doNetWork(getObservable(), activity.bindUtilEvent(ActivityEvent.STOP), new HttpListener<T>() {
            @Override
            public void onSuccess(T t) {
                setValue(t);
//                setValue(coders.get(0).getContext());
                //                observer.onChanged(coders.get(0).getContext());
            }

            @Override
            public void onError(String error) {
                Log.e(TAG, "onError: "+error);
            }

            @Override
            public void onNetWorkEnd() {

            }
        });
    }
}
