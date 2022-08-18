package com.example.davaleba_15.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.davaleba_15.ResponseState
import com.example.davaleba_15.model.RetrofitObject
import com.example.davaleba_15.model.UserFPost
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class LogInViewModel: ViewModel() {

    private val _loginResponse = MutableStateFlow<ResponseState>(ResponseState.Loading())
    val loginResponse: StateFlow<ResponseState> get() = _loginResponse

    fun loginUser(user: UserFPost){
        viewModelScope.launch {
            val response = RetrofitObject.getRetrofitState().userFLogin(user)
            if (response.isSuccessful && response.body() != null){
                _loginResponse.emit(ResponseState.Success(response.body()))
            }else{
                _loginResponse.emit(ResponseState.Error(response.errorBody().toString()))
            }
            Log.wtf("TAG", response.body().toString())
        }
    }
}