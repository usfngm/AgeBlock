package com.example.ageblock.api.callbacks;

public interface GenericReturnCallback<T> {
    void success(T callback);
    void error(String msg);
}
