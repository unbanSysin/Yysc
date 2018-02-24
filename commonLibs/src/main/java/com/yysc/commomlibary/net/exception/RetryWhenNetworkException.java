package com.yysc.commomlibary.net.exception;

import rx.Observable;
import rx.functions.Func1;

/**
 * Created by kj00037 on 2017/12/14.
 */

public class RetryWhenNetworkException implements Func1<Observable<? extends Throwable>, Observable<?>> {

    @Override
    public Observable<?> call(Observable<? extends Throwable> observable) {
        return null;
    }
}
