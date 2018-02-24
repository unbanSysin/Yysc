package com.yysc.activity;

import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.Observer;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.yysc.activity.Api.HttpGetService;
import com.yysc.activity.bean.Coder;
import com.yysc.commomlibary.base.BaseActivity;
import com.yysc.commomlibary.base.BaseApplication;
import com.yysc.commomlibary.mvvm.BaseModel;
import com.yysc.commomlibary.net.HttpApiService;
import com.yysc.commomlibary.net.HttpListener;
import com.yysc.commomlibary.net.HttpResult;
import com.yysc.commomlibary.net.rxlife.ActivityEvent;
import com.yysc.commomlibary.net.rxlife.RxAppCompatActivity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.Observable;

import static android.content.ContentValues.TAG;

/**
 *
 * Created by kj00037 on 2017/12/22.
 */

public class TestModel extends BaseModel<List<Coder>,HttpGetService> {

    @Override
    public void observe(LifecycleOwner owner, Observer observer) {
        super.observe(owner,observer);
        RxAppCompatActivity activity = (BaseActivity) owner;
        net(activity,observer);
    }

//    private void net(RxAppCompatActivity activity, final Observer observer) {
//
//        Observable<HttpResult<List<Coder>>> data = HttpApiService.getApi(getServiceCls())
//                                                                 .getAppCommentData(setPara());
//        HttpApiService.doNetWork(data, activity.bindUtilEvent(ActivityEvent.STOP), new HttpListener<List<Coder>>() {
//            @Override
//            public void onSuccess(List<Coder> coders) {
//                setValue(coders.get(0).getContext());
////                observer.onChanged(coders.get(0).getContext());
//            }
//
//            @Override
//            public void onError(String error) {
//                Log.e(TAG, "onError: "+error);
//            }
//
//            @Override
//            public void onNetWorkEnd() {
//
//            }
//        });
//    }


    @Override
    protected void setValue(List<Coder> value) {
        super.setValue(value);
    }

    @Override
    public Map<String, String> setPara() {
        HashMap<String, String> map = new HashMap<String, String>();
        //        map.put("sign", "453eef6c70b5a1de5d5941b9d8f3546cb47f5e7e29f0ce0fc8d7b623c2a5643b");
        //        map.put("timestamp", "1513236084568");
        //        map.put("token", "9570380b-d385-4ff2-be44-9f318dd6fc51");
        //        map.put("url", "apply/querystep.do");
        map.put("type", "shunfeng");
        map.put("postid", "11111111111");
        return map;
    }

    @Override
    public Class<HttpGetService> getServiceCls() {
        return HttpGetService.class;
    }

    @Override
    public Observable<HttpResult<List<Coder>>> getObservable() {
        Observable<HttpResult<List<Coder>>> data = HttpApiService.getApi(getServiceCls())
                                                                 .getAppCommentData(setPara());
        return data;
    }
}
