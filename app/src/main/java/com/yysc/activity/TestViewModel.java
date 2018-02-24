package com.yysc.activity;

import android.arch.lifecycle.Observer;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.support.annotation.Nullable;
import android.widget.EditText;

import com.yysc.commomlibary.base.BaseActivity;
import com.yysc.commomlibary.mvvm.BaseViewModel;

import javax.inject.Inject;

/**
 * Created by kj00037 on 2017/12/22.
 */

public class TestViewModel extends BaseViewModel<TestModel>{
    private  BaseActivity view;
    public TestModel mModel;
    public final ObservableField<String>   mObservableString  = new ObservableField<String>();
//    public final ObservableString   mObservableString  = new ObservableString();
    public final ObservableBoolean mObservableBoolean = new ObservableBoolean();

    //    public TestModel getModel() {
//ObservableBoolean
//        return new TestModel();
//    }

    public TestViewModel() {

    }

    @Override
    public TestModel getModel() {
        mModel = super.getModel();
        mModel.observe(null, new Observer() {
            @Override
            public void onChanged(@Nullable Object o) {
//                mObservableChar.set();
            }
        });
        return mModel;
    }

    public void changeEditText(EditText edit) {
        String text = edit.getText()
                          .toString()
                          .trim();
        mObservableString.set(text);
    }
}
