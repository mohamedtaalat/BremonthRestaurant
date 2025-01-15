package com.example.bremonthrestaurant.user.acessUser

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.bremonthrestaurant.R
import com.example.bremonthrestaurant.databinding.FragmentForgetPasswordBinding
import com.example.bremonthrestaurant.user.dataLogAndSign.UserDatabase
import com.example.bremonthrestaurant.user.dataLogAndSign.UserRepo
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class ForgetPasswordFragment : Fragment() {
    lateinit var binding: FragmentForgetPasswordBinding
    val viewModel by viewModels<ForgetViewModel> {
        ForgetViewModelFactory(UserRepo(UserDatabase.getDatabase(requireContext()).dao()))
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=FragmentForgetPasswordBinding.inflate(layoutInflater)
        val view=binding.root
        binding.updatePassword.setOnClickListener{
            viewModel.getEmail(binding.emailForgetUser.text.toString())
        }
        viewModel.emailLiveData.observe(viewLifecycleOwner){
            if (binding.emailForgetUser.text.toString()==it){
                lifecycleScope.launch(Dispatchers.IO) {
                    if (binding.passwordForgetUser.text.toString().length>=8){
                        viewModel.updatePassword(binding.emailForgetUser.text.toString(),binding.passwordForgetUser.text.toString())
                        Toast.makeText(requireContext(),"Succesful", Toast.LENGTH_SHORT).show()
                        findNavController().navigate(R.id.action_forgetPasswordFragment_to_logInFragment)
                    }else{
                        binding.passwordForgetUser.setError("wrong")
                        Snackbar.make(binding.textView3,"password must be more 8",Snackbar.LENGTH_SHORT).show()
                    }

                }
            }else{
                binding.emailForgetUser.setError("wrong")
                Snackbar.make(binding.textView3,"email is wrong",Snackbar.LENGTH_SHORT).show()
            }
        }
        return view
    }

}