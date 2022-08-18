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

class RegisterViewModel: ViewModel() {

    private val _registerResponse = MutableStateFlow<ResponseState>(ResponseState.Loading())
    val registerResponse: StateFlow<ResponseState> get() = _registerResponse

    fun registerUser(user: UserFPost){
        viewModelScope.launch {
            val response = RetrofitObject.getRetrofitState().userFRegister(user)
            if (response.isSuccessful && response.body() != null){
                _registerResponse.emit(ResponseState.Success(response.body()))
            }else{
                _registerResponse.emit(ResponseState.Error(response.errorBody().toString()))
            }
            Log.wtf("TAG", response.body().toString())
        }
    }
}