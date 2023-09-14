package com.dapascript.loginsamplemvvm.data.source.service

import com.dapascript.loginsamplemvvm.data.source.model.LoginRequest
import com.dapascript.loginsamplemvvm.data.source.model.LoginResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {

    @POST("api/login")
    suspend fun login(
        @Body loginRequest: LoginRequest
    ): LoginResponse
}