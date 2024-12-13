package com.capstone.frutify.viewModel

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.capstone.frutify.model.LoginRequest
import com.capstone.frutify.model.LoginResponse
import com.capstone.frutify.model.RegisterRequest
import com.capstone.frutify.model.RegisterResponse
import com.capstone.frutify.network.ApiInstance
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AuthViewModel(application: Application): AndroidViewModel(application) {

    private val sharedPreferences: SharedPreferences =
        application.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)

    private val _loginState = MutableLiveData<Result<LoginResponse>>()
    val loginState: LiveData<Result<LoginResponse>> = _loginState

    private val _registerState = MutableLiveData<Result<RegisterResponse>>()
    val registerState: LiveData<Result<RegisterResponse>> = _registerState

    fun saveLoginResponse(loginResponse: LoginResponse) {
        // Pastikan untuk menyimpan data di thread yang aman, bukan di main thread
        sharedPreferences.edit()
            .putString("auth_token", loginResponse.token)
            .putString("user_name", loginResponse.name)
            .putString("user_email", loginResponse.email)
            .putString("user_gender", loginResponse.gender)
            .apply()
    }

    fun checkLoginStatus(): Boolean {
        return sharedPreferences.contains("auth_token") && sharedPreferences.contains("user_name") && sharedPreferences.contains("user_email") && sharedPreferences.contains("user_gender")
    }

    fun getUserName(): String? {
        return sharedPreferences.getString("user_name", null)
    }

    fun getUserEmail(): String? {
        return sharedPreferences.getString("user_email", null)
    }

    fun loginUser(email: String, password: String) {
        viewModelScope.launch {
            val request = LoginRequest(email, password)
            try {
                ApiInstance.api.login(request).enqueue(object: Callback<LoginResponse> {
                    override fun onResponse(
                        call: Call<LoginResponse>,
                        response: Response<LoginResponse>
                    ) {
                        if (response.isSuccessful) {
                            // Handle a successful response
                            response.body()?.let {
                                saveLoginResponse(it)
                                _loginState.postValue(Result.success(it))
                            } ?: _loginState.postValue(Result.failure(Throwable("Empty Response")))
                        } else {
                            // Handle an unsuccessful response
                            val errorMessage = response.errorBody()?.string() ?: "Unknown error occurred"
                            _loginState.postValue(Result.failure(Throwable(errorMessage)))
                        }
                    }
                    override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                        _loginState.postValue(Result.failure(t))
                    }
                })
            }catch (e: Exception) {
                _loginState.postValue(Result.failure(e))
            }
        }
    }

    fun registerUser(name: String, email: String, password: String, gender: String) {
        // Launch a coroutine in the ViewModel scope
        viewModelScope.launch {
            // Create the registration request
            val request = RegisterRequest(name, email, password, gender)

            try {
                // Make the API call asynchronously
                ApiInstance.api.register(request).enqueue(object : Callback<RegisterResponse> {
                    override fun onResponse(
                        call: Call<RegisterResponse>,
                        response: Response<RegisterResponse>
                    ) {
                        if (response.isSuccessful) {
                            // Handle a successful response
                            response.body()?.let {
                                _registerState.postValue(Result.success(it))
                            } ?: _registerState.postValue(Result.failure(Throwable("Empty Response")))
                        } else {
                            // Handle an unsuccessful response
                            val errorMessage = response.errorBody()?.string() ?: "Unknown error occurred"
                            _registerState.postValue(Result.failure(Throwable(errorMessage)))
                        }
                    }

                    override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                        // Handle failure during the API call
                        _registerState.postValue(Result.failure(t))
                    }
                })
            } catch (e: Exception) {
                // Handle unexpected exceptions
                _registerState.postValue(Result.failure(e))
            }
        }
    }

}