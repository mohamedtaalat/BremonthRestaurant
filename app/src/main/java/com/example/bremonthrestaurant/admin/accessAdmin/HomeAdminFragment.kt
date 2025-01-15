package com.example.bremonthrestaurant.admin.accessAdmin

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bremonthrestaurant.R
import com.example.bremonthrestaurant.databinding.FragmentHomeAdminBinding
import com.example.bremonthrestaurant.menuData.MenuData
import com.example.bremonthrestaurant.menuData.MenuDatabase
import com.example.bremonthrestaurant.menuData.MenuRepo
import com.example.bremonthrestaurant.user.recyclerViewUser.RecyclerViewAdapterUser


class HomeAdminFragment : Fragment() {
  lateinit var binding:FragmentHomeAdminBinding
  val viewModel by viewModels<HomeAdminViewModel> {
      HomeAdminViewModelFactory(MenuRepo(MenuDatabase.getDatabase(requireContext()).dao()))
  }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=FragmentHomeAdminBinding.inflate(layoutInflater)
        val view=binding.root
        var items:List<MenuData> = listOf()
        val adapter=RecyclerViewAdapterUser(items,{
            viewModel.deleteItem(items.get(it).id)
            viewModel.getAllItems()
        },{

        })
        viewModel.getAllItems()
        viewModel.itemsLiveData.observe(viewLifecycleOwner){
            items=it
            adapter.setContent(items)
        }
        binding.addItemAdmin.setOnClickListener {
            findNavController().navigate(R.id.action_homeAdminFragment_to_addItemFragment)
        }
        binding.recAdmin.adapter=adapter
        binding.recAdmin.layoutManager=LinearLayoutManager(requireContext())
        return view
    }

}