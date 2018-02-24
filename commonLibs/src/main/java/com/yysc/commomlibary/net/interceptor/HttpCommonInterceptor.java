package com.yysc.commomlibary.net.interceptor;

import com.yysc.commomlibary.base.BaseApplication;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by kj00037 on 2017/12/6.
 */

public class HttpCommonInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain)
            throws IOException
    {
        Request request = chain.request();
        request = addHead(request);
        return chain.proceed(request);
    }

    public Request addHead(Request request) {
        Request.Builder builder = request.newBuilder();
//        builder.addHeader("User-Agent","123");
//        builder.addHeader("deviceID","456");
//        builder.addHeader("Content-Language", BaseApplication.getContext().getResources().getConfiguration().locale.getLanguage());
//        builder.addHeader("token", "12313213");
        return builder.build();
    }

}
