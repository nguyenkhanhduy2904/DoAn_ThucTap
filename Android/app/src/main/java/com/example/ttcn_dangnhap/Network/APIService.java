package com.example.ttcn_dangnhap.Network;

import models.APIResponse;
import models.ImageUpload;
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
}
