package com.yysc.commomlibary.net.interceptor;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by kj00037 on 2017/12/7.
 */

public class HttpsInterceptor implements Interceptor{
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        request = addHead(request);

        return chain.proceed(request);
    }

    private Request addHead(Request request) {
        Request.Builder builder = request.newBuilder();
//        builder.addHeader("timestamp",String.valueOf(System.currentTimeMillis()));
//        builder.addHeader("token","");
//        builder.addHeader("sign","");
        return builder.build();
    }
}
