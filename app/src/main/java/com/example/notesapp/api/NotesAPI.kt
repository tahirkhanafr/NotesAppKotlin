package com.example.notesapp.api

import com.example.notesapp.module.NoteRequest
import com.example.notesapp.module.NoteResponce
import retrofit2.Response
import retrofit2.http.*

interface NoteAPI {

    @POST("/note")
    suspend fun createNote(@Body noteRequest: NoteRequest): Response<NoteRequest>

    @GET("/note")
    suspend fun getNotes(): Response<List<NoteResponce>>

    @DELETE("/note/{noteId}")
    suspend fun deleteNote(@Path("noteId") noteId: String) : Response<NoteResponce>

    @PUT("/note/{noteId}")
    suspend fun updateNote(
        @Path("noteId") noteId: String,
        @Body noteRequest: NoteRequest
    ): Response<NoteResponce>
}