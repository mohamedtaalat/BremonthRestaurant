package com.example.bremonthrestaurant.user.acessUser

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.bremonthrestaurant.R
import com.example.bremonthrestaurant.databinding.FragmentLogInBinding
import com.example.bremonthrestaurant.user.dataLogAndSign.UserDatabase
import com.example.bremonthrestaurant.user.dataLogAndSign.UserRepo
import com.google.android.material.snackbar.Snackbar


class LogInFragment : Fragment() {
    lateinit var binding: FragmentLogInBinding
    val viewModel by viewModels<LogInViewModel> {
        LogInViewModelFactory(UserRepo(UserDatabase.getDatabase(requireContext()).dao()))
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=FragmentLogInBinding.inflate(layoutInflater)
        val view=binding.root
        viewModel.emailLiveData.observe(viewLifecycleOwner){
            if(binding.emailUserLog.text.toString()==it){
                if (binding.passwordLoginUser.text.toString()==viewModel.passwordLiveData.value){
                    Toast.makeText(requireContext(),"Succesful",Toast.LENGTH_SHORT).show()
                    val bundle=Bundle()
                    bundle.putString("email",binding.emailUserLog.text.toString())
                    findNavController().navigate(R.id.action_logInFragment_to_homeFragment,bundle)
                }else{
                    binding.passwordLoginUser.setError("wrong")
                    Snackbar.make(binding.textView2,"password is not correct",Snackbar.LENGTH_SHORT).show()
                }
            }else{
                binding.emailUserLog.setError("wrong")
                Snackbar.make(binding.textView2,"email is not correct",Snackbar.LENGTH_SHORT).show()
            }
        }
        binding.loginUser.setOnClickListener{
            viewModel.getUser(binding.emailUserLog.text.toString())
        }
        binding.signUpUser.setOnClickListener {
            findNavController().navigate(R.id.action_logInFragment_to_signupFragment)
        }
        binding.forgetUser.setOnClickListener {
            findNavController().navigate(R.id.action_logInFragment_to_forgetPasswordFragment)
        }

        return view
    }
}