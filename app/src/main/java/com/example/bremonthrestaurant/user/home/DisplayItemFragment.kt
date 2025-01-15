package com.example.bremonthrestaurant.user.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.bremonthrestaurant.databinding.FragmentDisplayItemBinding
import com.example.bremonthrestaurant.menuData.MenuDatabase
import com.example.bremonthrestaurant.menuData.MenuRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class DisplayItemFragment : Fragment() {
    lateinit var binding:FragmentDisplayItemBinding
    val viewModel by viewModels<HomeViewModel> {
        HomeViewModelFactory(MenuRepo(MenuDatabase.getDatabase(requireContext()).dao()))
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=FragmentDisplayItemBinding.inflate(layoutInflater)
        val view=binding.root
        val args=arguments?.getInt("Id")
        lifecycleScope.launch(Dispatchers.IO) {
            viewModel.selectItem(args!!)
        }
        viewModel.itemLiveData.observe(viewLifecycleOwner){
            binding.photoDisplay.setImageURI(it.photo.toUri())
            binding.nameDisplay.text=it.name
            binding.descriptionDisplay.text=it.description
        }

        return view
    }

}