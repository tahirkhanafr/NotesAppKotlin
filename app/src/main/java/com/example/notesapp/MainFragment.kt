package com.example.notesapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.notesapp.databinding.FragmentMainBinding
import com.example.notesapp.utils.NetworkResult
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : Fragment() {
    private var _binding: FragmentMainBinding?=null
    private  val binding get() = _binding!!
    private  val noteViewModel by viewModels<NoteViewModel>()

    private lateinit var adapter: NoteAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding=FragmentMainBinding.inflate(inflater,container,false)
        adapter=NoteAdapter {  }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindObserver()
        noteViewModel.getNotes()
        binding.noteList.layoutManager=StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        binding.noteList.adapter=adapter
    }

    private fun bindObserver() {
        noteViewModel.notesLiveData.observe(viewLifecycleOwner, Observer {
            binding.progressBar.isVisible=false
            when(it)
            {
                is NetworkResult.Success ->{
                    adapter.submitList(it.data)
                }
                is NetworkResult.Error ->  {
                    Toast.makeText(requireContext(),it.message,Toast.LENGTH_LONG).show()
                }
                is NetworkResult.Loading -> {
                    binding.progressBar.isVisible=true
                }
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding=null
    }


}