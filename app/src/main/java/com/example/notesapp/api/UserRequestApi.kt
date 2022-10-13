package com.example.notesapp.api

import com.example.notesapp.module.UserRequest
import com.example.notesapp.module.UserResponce
import dagger.Provides
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST



interface UserRequestApi {

    @POST("/users/signup")  //In Post we send Body so here we send userrequest so we menton @Body
    suspend fun Signup(@Body userRequest: UserRequest): Response<UserResponce>


    @POST("/users/signin")
    suspend fun Signin(@Body userRequest: UserRequest): Response<UserResponce>
}