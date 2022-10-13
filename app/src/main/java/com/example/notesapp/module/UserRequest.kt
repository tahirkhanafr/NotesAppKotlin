package com.example.notesapp.module

data class UserRequest(
    val email: String,
    val password: String,
    val username: String
)