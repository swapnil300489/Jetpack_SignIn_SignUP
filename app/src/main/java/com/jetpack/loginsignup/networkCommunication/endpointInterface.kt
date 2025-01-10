package com.jetpack.loginsignup.networkCommunication

import com.jetpack.loginsignup.req_res_model.LoginRequest
import com.jetpack.loginsignup.req_res_model.LoginResponse
import com.jetpack.loginsignup.req_res_model.UserListResponse
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface endpointInterface {
    @FormUrlEncoded
    @POST("auth/login")
    //suspend fun login(@Body request: LoginRequest): LoginResponse
    suspend fun login(@Field("username") username: String,
                      @Field("password") password: String): LoginResponse


    @GET("users")
    suspend fun getUsers(): List<UserListResponse>
}