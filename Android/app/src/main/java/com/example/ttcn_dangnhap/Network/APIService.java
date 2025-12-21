package com.example.ttcn_dangnhap.Network;

import com.google.gson.JsonObject;

import java.util.List;

import models.APIResponse;
import models.ImageUpload;
import models.OrderDTO;
import models.OrderItemDTO;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.*;

public interface APIService {

    @Multipart
    @POST("upload/image")
    Call<APIResponse<ImageUpload>> uploadImage(
            @Part MultipartBody.Part file,
            @Part("type") RequestBody type
    );
    @POST("orders")
    Call<APIResponse<OrderDTO>> addOrder(@Body OrderDTO order);
    @GET("orders")
    Call<APIResponse<List<OrderDTO>>> getAllOrders();
    @GET("orders/user/{id}")
    Call<APIResponse<List<OrderDTO>>> getOrdersByUser(@Path("id") int userId);
    @PUT("orders/{id}/status")
    Call<APIResponse<OrderDTO>> updateOrderStatus(
            @Path("id") int orderId,
            @Query("status") String status
    );
}
