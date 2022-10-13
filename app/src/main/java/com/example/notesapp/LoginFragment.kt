package com.example.notesapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.notesapp.databinding.FragmentLoginBinding
import com.example.notesapp.module.User
import com.example.notesapp.module.UserRequest
import com.example.notesapp.utils.NetworkResult
import com.example.notesapp.utils.TokenManager
import dagger.hilt.android.AndroidEntryPoint
import java.util.zip.Inflater
import javax.inject.Inject


@AndroidEntryPoint
class LoginFragment : Fragment() {

    private  var _binding :FragmentLoginBinding?=null
    private val binding get() =_binding!!
    private val authViewModel by viewModels<AuthViewModel>()

    @Inject
    lateinit var tokenManager: TokenManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?):
            View? {

        _binding=FragmentLoginBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnLogin.setOnClickListener {
            val validationResult= validateUserInput()
            if(validationResult.first){
                    authViewModel.loginUser(getUserRequest())
            }
            else
            {
                binding.txtError.text= validationResult.second
            }
        }
        binding.btnSignUp.setOnClickListener {
            findNavController().popBackStack()
        }
        bindObserver()
    }

    private fun bindObserver() {
        authViewModel.useResponceLiveData.observe(viewLifecycleOwner, Observer {
            binding.progressBar.isVisible= false

            when(it){
                is NetworkResult.Success -> {
                    tokenManager.saveToken(it.data!!.token)
                    findNavController().navigate(R.id.action_loginFragment_to_mainFragment)
                    //will save token of login after
                }
                is NetworkResult.Error ->  {
                    binding.txtError.text= it.message

                }
                is NetworkResult.Loading -> {
                    binding.progressBar.isVisible= true
                }
            }
        })
    }

    private fun getUserRequest(): UserRequest {
        val emailAddress = binding.txtEmail.text.toString()
        val password = binding.txtPassword.text.toString()
        return UserRequest(emailAddress,password,"")
    }
    private fun validateUserInput(): Pair<Boolean, String>{
        val userrequest=getUserRequest()
        return authViewModel.validateCrendital(userrequest.username,userrequest.email,userrequest.password, true)
    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding=null
    }

}