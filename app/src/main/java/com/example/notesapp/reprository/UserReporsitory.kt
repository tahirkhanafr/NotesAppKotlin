package com.example.notesapp.reprository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.notesapp.api.UserRequestApi
import com.example.notesapp.module.UserRequest
import com.example.notesapp.module.UserResponce
import com.example.notesapp.utils.NetworkResult
import org.json.JSONObject
import retrofit2.Response
import javax.inject.Inject

class UserReporsitory @Inject constructor(private val userRequestApi: UserRequestApi) {

    private val _userResponceLiveData = MutableLiveData<NetworkResult<UserResponce>>()
    val userResponceLiveData: LiveData<NetworkResult<UserResponce>>
        get() = _userResponceLiveData


    suspend fun registerUser(userRequest: UserRequest) {
        _userResponceLiveData.postValue(NetworkResult.Loading())
        val responce = userRequestApi.Signup(userRequest)

        handleresponce(responce)

    }

    suspend fun loginUser(userRequest: UserRequest) {
        _userResponceLiveData.postValue(NetworkResult.Loading())
        val responce = userRequestApi.Signin(userRequest)
        handleresponce(responce)
    }

    // to show error or success
    //how to read error message  //hum phir  is mai error dek lety hai.
    private fun handleresponce(responce: Response<UserResponce>) {
        if (responce.isSuccessful && responce.body() != null) {
            _userResponceLiveData.postValue(NetworkResult.Success(responce.body()!!))

        } else if (responce.errorBody() != null) {
            val errorObj = JSONObject(responce.errorBody()!!.charStream().readText())
            _userResponceLiveData.postValue(NetworkResult.Error(errorObj.getString("message")))
        } else {
            _userResponceLiveData.postValue(NetworkResult.Error("Something Went Wrong"))
        }
    }


}