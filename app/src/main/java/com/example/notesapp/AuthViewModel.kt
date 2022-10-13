package com.example.notesapp

import android.text.TextUtils
import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.notesapp.module.UserRequest
import com.example.notesapp.module.UserResponce
import com.example.notesapp.reprository.UserReporsitory
import com.example.notesapp.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.regex.Pattern
import javax.inject.Inject


@HiltViewModel
class AuthViewModel @Inject constructor(private val userReporsitory: UserReporsitory): ViewModel() {

    val useResponceLiveData: LiveData<NetworkResult<UserResponce>>
        get() =userReporsitory.userResponceLiveData

    fun registerUser(userRequest: UserRequest){
        viewModelScope.launch {
            userReporsitory.registerUser(userRequest)
        }

    }
    fun loginUser(userRequest: UserRequest){
        viewModelScope.launch {
            userReporsitory.loginUser(userRequest)
        }

    }
    fun validateCrendital(username: String, emailaddress: String, password: String, isLogin: Boolean) : Pair<Boolean, String>{
        var result= Pair(true,"")
        if (!isLogin && TextUtils.isEmpty(username) || TextUtils.isEmpty(emailaddress) || TextUtils.isEmpty(password)){
            result= Pair(false,"Please Provide the credentials")
        }
        else if (!Patterns.EMAIL_ADDRESS.matcher(emailaddress).matches()){
            result=Pair(false, "Please provide valid email")
        }
        else if (password.length<=5){
            result= Pair(false, "Password lenght must be greater then 5")
        }
        return result
    }
}