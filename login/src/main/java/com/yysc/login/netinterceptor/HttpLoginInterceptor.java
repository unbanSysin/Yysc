package com.yysc.login.netinterceptor;

import com.yysc.otherlibs.Commom;
import com.yysc.otherlibs.utils.Constance;
import com.yysc.otherlibs.utils.DESEncryptUtil;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by kj00037 on 2017/12/29.
 */

public class HttpLoginInterceptor
        implements Interceptor
{

    private String shortUrl;

    public HttpLoginInterceptor(String shortUrl) {
        this.shortUrl = shortUrl;
    }

    @Override
    public Response intercept(Chain chain)
            throws IOException
    {
        Request request = chain.request();
        request = addHeader(request);
        Response proceed = chain.proceed(request);
        return proceed;
    }

    private Request addHeader(Request request) {
        Commom commom = new Commom();
        commom.setToken(Constance.TOKEN);
        String timestamp = String.valueOf(System.currentTimeMillis());
        commom.setTimestamp(timestamp);
        commom.setUrl(shortUrl);
        String sign = DESEncryptUtil.doSign(commom);
//
        Request.Builder builder = request.newBuilder();
        builder.addHeader("timestamp", timestamp);
        builder.addHeader("token", Constance.TOKEN);
        builder.addHeader("sign", sign);
        return builder.build();
    }

}
