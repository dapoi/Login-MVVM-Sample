package com.dapascript.loginsamplemvvm.presentation.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dapascript.loginsamplemvvm.data.repository.LoginRepository
import com.dapascript.loginsamplemvvm.data.source.model.LoginResponse
import com.dapascript.loginsamplemvvm.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginRepository: LoginRepository
) : ViewModel() {

    private val _loginState = MutableLiveData<Resource<LoginResponse>>()
    val loginState: LiveData<Resource<LoginResponse>> = _loginState

    fun login(email: String, password: String) {
        viewModelScope.launch {
            loginRepository.login(email, password).collect { response ->
                _loginState.value = response
            }
        }
    }
}