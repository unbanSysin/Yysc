package com.yysc.login.Api;

import com.yysc.commomlibary.net.HttpResult;
import com.yysc.login.bean.UserInfoCommom;
import com.yysc.login.bean.UserInfoContent;

import java.util.Map;

import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by kj00037 on 2018/1/8.
 */

public interface HttpGetApi {

    @POST("userAction/loginUser.do")/*评论页面数据*/
    @FormUrlEncoded
    Observable<HttpResult<UserInfoContent>> login(@FieldMap Map<String, String> names);
}
