package com.capstone.frutify.network

import com.capstone.frutify.model.LoginRequest
import com.capstone.frutify.model.LoginResponse
import com.capstone.frutify.model.RegisterRequest
import com.capstone.frutify.model.RegisterResponse
import com.capstone.frutify.model.SaveScanResponse
import com.capstone.frutify.model.ScanDataResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface FrutifyApi {

    @POST("auth/login")
    fun login(@Body request: LoginRequest): Call<LoginResponse>

    @POST("auth/register")
    fun register(@Body request: RegisterRequest): Call<RegisterResponse>

    @Multipart
    @POST("upload")
    fun saveScanData(
        @Part("user_id") userId: RequestBody,
        @Part("fruit_name") fruitName: RequestBody,
        @Part("fruit_condition") fruitCondition: RequestBody,
        @Part("fruit_weight") fruitWeight: RequestBody,
        @Part("nutrition_info") nutritionInfo: RequestBody,
        @Part file: MultipartBody.Part
    ): Call<SaveScanResponse>

    @GET("getAllData")
    fun getScanData(): Call<ScanDataResponse>
}


object ApiInstance {
    private const val BASE_URL = "http://34.101.249.35:7000/api/"

    val api: FrutifyApi by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(FrutifyApi::class.java)
    }
}