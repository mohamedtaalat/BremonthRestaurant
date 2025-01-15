package com.example.bremonthrestaurant.user.acessUser

import android.Manifest
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat.checkSelfPermission
import androidx.core.net.toUri
import androidx.fragment.app.viewModels
import com.example.bremonthrestaurant.R
import com.example.bremonthrestaurant.databinding.FragmentProfileBinding
import com.example.bremonthrestaurant.user.dataLogAndSign.User
import com.example.bremonthrestaurant.user.dataLogAndSign.UserDatabase
import com.example.bremonthrestaurant.user.dataLogAndSign.UserRepo


class ProfileFragment : Fragment() {
    lateinit var binding: FragmentProfileBinding
    lateinit var args:String
    val viewModel by viewModels<ProfileViewModel> {
        ProfileViewModelFactory(UserRepo(UserDatabase.getDatabase(requireContext()).dao()))
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=FragmentProfileBinding.inflate(layoutInflater)
        val view=binding.root
        args= arguments?.getString("email").toString()
        viewModel.getUser(args)
        viewModel.userLiveData.observe(viewLifecycleOwner){
            checkPermission(it)
            binding.nameProfile.text=it.name
            binding.passwordProfile.text=it.password
            binding.emailProfile.text=it.email
        }
        binding.imageView.setOnClickListener {
            openImagePicker()
        }
        binding.nameProfile.setOnClickListener{

        }
        binding.passwordProfile.setOnClickListener {

        }
        viewModel.photoLiveData.observe(viewLifecycleOwner){
            binding.imageView.setImageURI(it.toUri())
        }
        return view
    }
    private fun openImagePicker() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        imagePickerLauncher.launch(intent)
    }

    private val imagePickerLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == RESULT_OK) {
            result.data?.data?.let { uri ->
                displayImage(uri)
            }
        }
    }
    private fun displayImage(uri: Uri) {
        viewModel.updatePhoto(args,uri.toString())
    }
    private fun checkPermission(it: User) {
        if (checkSelfPermission(
                requireContext(),
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                100
            )
            if (checkSelfPermission(
                    requireContext(),
                    Manifest.permission.READ_EXTERNAL_STORAGE
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                binding.imageView.setImageResource(R.drawable.profile)
            } else {
                binding.imageView.setImageURI(it.photo.toUri())
            }
        } else {
            binding.imageView.setImageURI(it.photo.toUri())
        }
    }

}