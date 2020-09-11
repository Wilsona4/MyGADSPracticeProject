package com.funcrib.gadsapp.repository.data;

public interface DataResponseCallback<T> {
    void onResponse(T response);
    void onError(Throwable error);
}