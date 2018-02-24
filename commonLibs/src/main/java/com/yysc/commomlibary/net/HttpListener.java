package com.yysc.commomlibary.net;

/**
 * Created by kj00037 on 2017/12/7.
 */

public interface HttpListener<V> {

    public void onSuccess(V v);

    public void onError(String error);

    public void onNetWorkEnd();
}
