package com.example.bremonthrestaurant.admin.accessAdmin

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
import com.example.bremonthrestaurant.databinding.FragmentAddItemBinding
import com.example.bremonthrestaurant.menuData.MenuData
import com.example.bremonthrestaurant.menuData.MenuDatabase
import com.example.bremonthrestaurant.menuData.MenuRepo
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class AddItemFragment : Fragment() {
   lateinit var binding:FragmentAddItemBinding
   lateinit var photo:String
   val viewModel by viewModels<HomeAdminViewModel> {
       HomeAdminViewModelFactory(MenuRepo(MenuDatabase.getDatabase(requireContext()).dao()))
   }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=FragmentAddItemBinding.inflate(layoutInflater)
        val view =binding.root
        binding.ADDItem.setOnClickListener {
            lifecycleScope.launch(Dispatchers.IO) {
                addItem()
            }
        }
        binding.imageView2.setOnClickListener {
            lifecycleScope.launch(Dispatchers.IO) {
                openImagePicker()
            }
        }
        return view
    }

    private fun addItem() {
        if (binding.nameAddItem.text.toString() != "") {
            if (binding.desciriptionAddItem.text.toString() != "") {
                if (binding.priceAddItem.text.toString() != "") {
                    if (photo != "") {
                        val menuData = MenuData(
                            id = 0,
                            name = binding.nameAddItem.text.toString(),
                            description = binding.desciriptionAddItem.text.toString(),
                            price = binding.priceAddItem.text.toString().toDouble(),
                            photo = photo
                        )
                        viewModel.addItem(menuData)
                        Toast.makeText(requireContext(), "Add Successful", Toast.LENGTH_SHORT)
                            .show()
                        findNavController().navigate(R.id.action_addItemFragment_to_homeAdminFragment)
                    } else {
                        Snackbar.make(binding.textView9, "photo require", Snackbar.LENGTH_SHORT)
                            .show()
                    }
                } else {
                    Snackbar.make(binding.textView9, "price require", Snackbar.LENGTH_SHORT).show()
                }
            } else {
                Snackbar.make(binding.textView9, "description require", Snackbar.LENGTH_SHORT)
                    .show()
            }
        } else {
            Snackbar.make(binding.textView9, "name require", Snackbar.LENGTH_SHORT).show()
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
       binding.imageView2.setImageURI(uri)
    }
}