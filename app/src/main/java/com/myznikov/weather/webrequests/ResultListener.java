package com.myznikov.weather.webrequests;

/**
 * Created by MS Creator on 04.04.2015.
 */
public interface ResultListener<T, E> {
    void onSuccessResult(T result);
    void onErrorResult(E error);
}
