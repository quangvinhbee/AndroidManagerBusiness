package com.nhom1.database;

public interface QueryResponse<T> {
    void onSuccess(T data);
    void onFailure(String message);
}
