package com.example.bremonthrestaurant.user.acessUser

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.bremonthrestaurant.R
import com.example.bremonthrestaurant.databinding.FragmentSignupBinding
import com.example.bremonthrestaurant.user.dataLogAndSign.User
import com.example.bremonthrestaurant.user.dataLogAndSign.UserDatabase
import com.example.bremonthrestaurant.user.dataLogAndSign.UserRepo
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class SignupFragment : Fragment() {
    lateinit var binding: FragmentSignupBinding
    lateinit var photo:String
    val viewModel by viewModels<SignViewModel> {
        SignViewModelFactory(UserRepo(UserDatabase.getDatabase(requireContext()).dao()))
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=FragmentSignupBinding.inflate(layoutInflater)
        val view=binding.root
        binding.signSignUser.setOnClickListener {
            viewModel.checkEmail(binding.emailSignUser.text.toString())
        }
        binding.logInSignUser.setOnClickListener {
            findNavController().navigate(R.id.action_signupFragment_to_logInFragment)
        }
        viewModel.emailLiveData.observe(viewLifecycleOwner){
            checkValidation(it)
        }
        binding.photoSignUser.setOnClickListener {
            openImagePicker()
        }
        return view
    }

    private fun checkValidation(it: String?) {
        if (binding.emailSignUser.text.toString() != it) {
            if (binding.nameSignUser.text.toString() != "") {
                if (binding.emailSignUser.text.toString() != "" && binding.emailSignUser.text.toString()
                        .contains("@gmail.com")
                ) {
                    if (binding.passwordSignUser.text.toString().length >= 8) {
                        if (photo != "") {
                            val user = User(
                                binding.emailSignUser.text.toString(),
                                binding.nameSignUser.text.toString(),
                                binding.passwordSignUser.text.toString(),
                                photo
                            )
                            lifecycleScope.launch(Dispatchers.IO) {
                                viewModel.addUser(user)
                            }
                            Toast.makeText(requireContext(), "Succesful", Toast.LENGTH_SHORT)
                                .show()
                            val bundle=Bundle()
                            bundle.putString("email",binding.emailSignUser.text.toString())
                            findNavController().navigate(R.id.action_signupFragment_to_homeFragment,bundle)

                        } else {
                            val user = User(
                                binding.emailSignUser.text.toString(),
                                binding.nameSignUser.text.toString(),
                                binding.passwordSignUser.text.toString(),
                                R.drawable.profile.toString()
                            )
                            lifecycleScope.launch(Dispatchers.IO) {
                                viewModel.addUser(user)
                                Toast.makeText(requireContext(), "Succesful", Toast.LENGTH_SHORT)
                                    .show()
                                findNavController().navigate(R.id.action_signupFragment_to_homeFragment)
                            }
                        }
                    } else {
                        binding.passwordSignUser.setError("Wrong")
                        Snackbar.make(binding.textView4, "password require", Snackbar.LENGTH_SHORT)
                            .show()
                    }
                } else {
                    binding.emailSignUser.setError("Wrong")
                    Snackbar.make(binding.textView4, "email require", Snackbar.LENGTH_SHORT).show()
                }
            } else {
                binding.nameSignUser.setError("Wrong")
                Snackbar.make(binding.textView4, "name require", Snackbar.LENGTH_SHORT).show()
            }
        } else {
            binding.emailSignUser.setError("Wrong")
            Snackbar.make(binding.textView4, "email must be unique", Snackbar.LENGTH_SHORT).show()
        }
    }

    private fun openImagePicker() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        imagePickerLauncher.launch(intent)
    }

    private val imagePickerLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            result.data?.data?.let { uri ->
                displayImage(uri)
            }
        }
    }
    private fun displayImage(uri: Uri) {
        photo=uri.toString()
        binding.photoSignUser.setImageURI(uri)
    }
}