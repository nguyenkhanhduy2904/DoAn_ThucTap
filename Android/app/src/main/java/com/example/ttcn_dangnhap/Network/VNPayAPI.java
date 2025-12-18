package com.example.ttcn_dangnhap.Network;

import models.VNPayResponse;
import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface VNPayAPI {
    @POST("api/vnpay/create-payment")
    Call<VNPayResponse> createPayment(@Query("amount") long amount);
}
