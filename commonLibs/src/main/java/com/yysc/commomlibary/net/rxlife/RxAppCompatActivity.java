package com.yysc.commomlibary.net.rxlife;

import android.arch.lifecycle.LifecycleActivity;
import android.os.Bundle;
import android.support.annotation.Nullable;

import rx.Observable;
import rx.functions.Func1;
import rx.subjects.BehaviorSubject;

/**
 * Created by kj00037 on 2017/12/12.
 */

public class RxAppCompatActivity extends LifecycleActivity{
    private static final String TAG = "RxAppCompatActivity";
    Observable mObservable;
    Observable lifeObservable;
    BehaviorSubject lifeSubject = BehaviorSubject.create();

    public Observable.Transformer bindUtilEvent(final ActivityEvent bindEvent){
        lifeObservable = lifeSubject.takeFirst(new Func1<ActivityEvent, Boolean>() {
            @Override
            public Boolean call(ActivityEvent event) {
                return event.equals(bindEvent);
            }
        });

        return new Observable.Transformer<Object,Object>(){

            @Override
            public Observable<Object> call(Observable<Object> observable) {
                return observable.takeUntil(lifeObservable);
            }
        };
    }

    /**
     * lifeSubject.onNext是接收事件
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        lifeSubject.onNext(ActivityEvent.CREAT);
    }

    @Override
    protected void onStart() {
        super.onStart();
        lifeSubject.onNext(ActivityEvent.START);
    }

    @Override
    protected void onResume() {
        super.onResume();
        lifeSubject.onNext(ActivityEvent.RESUME);
    }

    @Override
    protected void onPause() {
        lifeSubject.onNext(ActivityEvent.PAUSE);
        super.onPause();
    }

    @Override
    protected void onStop() {
        lifeSubject.onNext(ActivityEvent.STOP);
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        lifeSubject.onNext(ActivityEvent.DESTORY);
        super.onDestroy();
    }
}
