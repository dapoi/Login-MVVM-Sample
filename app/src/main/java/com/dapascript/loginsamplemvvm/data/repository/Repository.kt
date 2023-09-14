package com.dapascript.loginsamplemvvm.data.repository

import com.dapascript.loginsamplemvvm.data.source.model.LoginRequest
import com.dapascript.loginsamplemvvm.data.source.model.LoginResponse
import com.dapascript.loginsamplemvvm.data.source.service.ApiService
import com.dapascript.loginsamplemvvm.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class LoginRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : LoginRepository {

    override suspend fun login(email: String, password: String): Flow<Resource<LoginResponse>> {
        return flow {
            emit(Resource.Loading)

            try {
                val loginRequest = LoginRequest(email, password)
                val response = apiService.login(loginRequest)
                emit(Resource.Success(response))
            } catch (e: Throwable) {
                emit(Resource.Failure(e))
            }
        }.flowOn(Dispatchers.IO)
    }
}

interface LoginRepository {
    suspend fun login(email: String, password: String): Flow<Resource<LoginResponse>>
}