package com.yysc.commomlibary.net;


import android.content.Context;

import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.functions.Func2;
import rx.schedulers.Schedulers;


/**
 * Created by kj00037 on 2017/12/7.
 */

public class HttpApiService {

    private static HttpRetrofitClient mClient;

    public HttpApiService() {

    }

    public static <T> T getApi(Class<T> serviceCls) {
        if (mClient == null) {
            mClient = new HttpRetrofitClient();
        }
        return mClient.getApiService(serviceCls);
    }

    public static <T> T getApi(Class<T> serviceCls, Context context) {
        if (mClient == null) {
            mClient = new HttpRetrofitClient(context);
        }
        return mClient.getApiService(serviceCls);
    }

    public static HttpRetrofitClient getRetrofitClient() {
        if (mClient == null) {
            mClient = new HttpRetrofitClient();
        }
        return mClient;
    }

    /**
     * 1.失败重试
     * 2.内存泄露问题，需要绑定activity或者fragment的周期
     * 3.不需要反订阅会在完成后自动执行反订阅
     * 4.subscribe的时候才发起请求（关键是rxjavacallAdapterFactory这个库）
     * @param observable
     * @param listener
     * @param <V>
     */
    public static <V> void doNetWork(Observable<HttpResult<V>> observable,
                                     Observable.Transformer transformer,
                                     final HttpListener<V> listener)
    {
        //网络请求时的进度条显示
        observable.subscribeOn(Schedulers.io())
                  .unsubscribeOn(Schedulers.io())
                  .observeOn(AndroidSchedulers.mainThread())
                  //错误重试
                  .retry(new Func2<Integer, Throwable, Boolean>() {
                      @Override
                      public Boolean call(Integer integer, Throwable throwable) {
                          if (integer <= 2) { return true; } else { return false; }
                      }
                  })
                  .map(new Func1<HttpResult<V>, V>() {
                      @Override
                      public V call(HttpResult<V> httpResult) {
                          return httpResult.getData();
                      }
                  })

                  .compose(transformer)
                  .subscribe(new Observer<V>() {
                      @Override
                      public void onCompleted() {
                          listener.onNetWorkEnd();
                      }

                      @Override
                      public void onError(Throwable e) {
                          listener.onError(e.toString());
                      }

                      @Override
                      public void onNext(V data) {
                          listener.onSuccess(data);
                      }
                  });
    }
}
