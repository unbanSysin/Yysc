package com.yysc.commomlibary.net;

import okhttp3.Interceptor;

/**
 * Created by kj00037 on 2017/12/29.
 */

public interface CustomerInterceptorListener {
    public Interceptor getInterceptor();
}
