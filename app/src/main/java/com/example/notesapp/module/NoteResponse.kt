package com.example.notesapp.module

data class NoteResponse(
    val __v : Int,
    val _id: String,
    val createdAt: String,
    val descriptiion: String,
    val title: String,
    val updatedAt: String,
    val userId: String
)