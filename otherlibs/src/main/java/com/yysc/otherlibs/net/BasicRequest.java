package com.yysc.otherlibs.net;

import com.yysc.otherlibs.Commom;
import com.yysc.otherlibs.utils.Constance;
import com.yysc.otherlibs.utils.DESEncryptUtil;

import java.util.HashMap;

import okhttp3.Request;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.V;

/**
 * Created by kj00037 on 2018/1/9.
 */

public class BasicRequest<K,V> extends HashMap{

    public BasicRequest(String shortUrl) {
        Commom commom = new Commom();
        commom.setToken(Constance.TOKEN);
        String timestamp = String.valueOf(System.currentTimeMillis());
        commom.setTimestamp(timestamp);
        commom.setUrl(shortUrl);
        String sign = DESEncryptUtil.doSign(commom);

        put("timestamp",timestamp);
        put("url",shortUrl);
        put("sign",sign);
    }
}
