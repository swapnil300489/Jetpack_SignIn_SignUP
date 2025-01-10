package com.jetpack.loginsignup.utils

import com.jetpack.loginsignup.networkCommunication.RetrofitInstance
import com.jetpack.loginsignup.req_res_model.LoginRequest
import com.jetpack.loginsignup.req_res_model.LoginResponse
import com.jetpack.loginsignup.req_res_model.UserListResponse
import retrofit2.Retrofit

class Repository {
    private val apiCall = RetrofitInstance.api

    suspend fun login(userName: String, password: String): LoginResponse{

        //return apiCall.login(LoginRequest(userName, password))
        return apiCall.login(userName, password)

    }

    suspend fun getAllUsers(): List<UserListResponse>{
        return apiCall.getUsers()

    }
}