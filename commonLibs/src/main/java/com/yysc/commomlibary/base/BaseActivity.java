package com.yysc.commomlibary.base;

import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.yysc.commomlibary.R;
import com.yysc.commomlibary.mvvm.BaseViewModel;
import com.yysc.commomlibary.net.rxlife.RxAppCompatActivity;

import java.lang.reflect.ParameterizedType;

/**
 * Created by kj00037 on 2017/12/12.
 * activity基类
 */

public abstract class BaseActivity extends RxAppCompatActivity {

    public ViewDataBinding mDataBinding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDataBinding = DataBindingUtil.setContentView(this, getContentView());
        initView();
        initData();
        initEvent();
    }

    public abstract void initView();

    public abstract void initData();

    public abstract void initEvent();

    public abstract int getContentView();



//    private Class<T> clazz;
//
//    public  T getViewModel() {
//        try {
//            ParameterizedType type = (ParameterizedType) this.getClass().getGenericSuperclass();
//            clazz = (Class<T>) type.getActualTypeArguments()[0];
//
//            T viewModel = (T) ViewModelProviders.of(this).get(clazz);
//            return viewModel;
//        }catch (Exception e) {
//            e.printStackTrace();
//            return null;
//        }
//    }
}
