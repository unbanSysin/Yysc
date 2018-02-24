package com.yysc.commomlibary.mvvm;

import android.app.Application;
import android.arch.lifecycle.ViewModel;

import com.yysc.commomlibary.net.HttpApiService;
import com.yysc.commomlibary.net.HttpListener;

import java.lang.reflect.ParameterizedType;
import java.util.Map;

/**
 * Created by kj00037 on 2017/12/22.
 */

public class BaseViewModel<T extends BaseModel>  extends ViewModel{

    private  Application mApplication;
    protected Class clazz;

    public BaseViewModel() {

    }

    public BaseViewModel(Application application) {
        mApplication = application;
    }

    /**
     * Return the application.
     */
    public <V extends Application> V getApplication() {
        return (V) mApplication;
    }

    public T getModel(){
        try {
            ParameterizedType type = (ParameterizedType) this.getClass().getGenericSuperclass();
            clazz = (Class<T>) type.getActualTypeArguments()[0];
            return  (T)clazz.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
            return null;
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            return null;
        }
    }

}
