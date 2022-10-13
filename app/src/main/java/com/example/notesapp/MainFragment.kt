package com.example.notesapp

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.notesapp.api.NoteAPI
import com.example.notesapp.utils.Constants.CHEEZYCODE
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainFragment : Fragment() {

   @Inject
   lateinit var  noteAPI: NoteAPI



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        CoroutineScope(Dispatchers.IO).launch {
            val response= noteAPI.getNotes()
            Log.d(CHEEZYCODE, response.body().toString())
        }

        return inflater.inflate(R.layout.fragment_main, container, false)
    }


}