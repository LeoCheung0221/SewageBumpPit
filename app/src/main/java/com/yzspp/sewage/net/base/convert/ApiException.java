package com.yzspp.sewage.net.base.convert;

/**
 * Created by LeoCheung4ever on 2018/8/3.
 *
 * @See
 * @Description
 */

public class ApiException extends RuntimeException {

    private String mErrorCode;

    public ApiException(String errorCode, String errorMessage) {
        super(errorMessage);
        mErrorCode = errorCode;
    }

    public String getErrorCode() {
        return mErrorCode;
    }
}
