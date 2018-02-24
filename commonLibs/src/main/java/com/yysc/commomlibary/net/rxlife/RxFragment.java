package com.yysc.commomlibary.net.rxlife;


import android.arch.lifecycle.LifecycleFragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;

import rx.subjects.BehaviorSubject;

/**
 * Created by kj00037 on 2017/12/12.
 */

public class RxFragment extends LifecycleFragment {

    BehaviorSubject lifeSubject = BehaviorSubject.create();

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        lifeSubject.onNext(FragmentEvent.ATTACH);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        lifeSubject.onNext(FragmentEvent.CREAT);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        lifeSubject.onNext(FragmentEvent.CREAT_VIEW);
    }

    @Override
    public void onStart() {
        super.onStart();
        lifeSubject.onNext(FragmentEvent.START);
    }

    @Override
    public void onResume() {
        super.onResume();
        lifeSubject.onNext(FragmentEvent.RESUME);
    }

    @Override
    public void onPause() {
        super.onPause();
        lifeSubject.onNext(FragmentEvent.PAUSE);
    }

    @Override
    public void onStop() {
        super.onStop();
        lifeSubject.onNext(FragmentEvent.STOP);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        lifeSubject.onNext(FragmentEvent.DESTORY_VIEW);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        lifeSubject.onNext(FragmentEvent.DESTORY);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        lifeSubject.onNext(FragmentEvent.DETACH);
    }
}
