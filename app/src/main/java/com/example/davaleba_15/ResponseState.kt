package com.example.davaleba_15

import com.example.davaleba_15.model.TokenFResponse

sealed class ResponseState (
    open val isLoading : Boolean
    ) {
      class Success(val successData: TokenFResponse?, override val isLoading: Boolean = false) :
        ResponseState(isLoading = false)

      class Loading(isLoading: Boolean = true) : ResponseState(isLoading)

      class Error(val errorMessage: String?, override val isLoading: Boolean = false) :
        ResponseState(isLoading = false)

}