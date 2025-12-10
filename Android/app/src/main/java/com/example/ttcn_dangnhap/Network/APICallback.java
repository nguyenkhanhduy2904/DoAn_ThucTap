package com.example.ttcn_dangnhap.Network;

public interface APICallback <T>{
    void onSuccess(T result);
    void onError(String errorMessage);
}
