package com.example.bremonthrestaurant.user.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bremonthrestaurant.R
import com.example.bremonthrestaurant.databinding.FragmentCartBinding
import com.example.bremonthrestaurant.menuData.MenuData
import com.example.bremonthrestaurant.menuData.MenuDatabase
import com.example.bremonthrestaurant.menuData.MenuRepo
import com.example.bremonthrestaurant.user.recyclerViewUser.RecyclerViewAdapterUser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class CartFragment : Fragment() {
    lateinit var binding:FragmentCartBinding
    val viewModel by viewModels<HomeViewModel> {
        HomeViewModelFactory(MenuRepo(MenuDatabase.getDatabase(requireContext()).dao()))
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=FragmentCartBinding.inflate(layoutInflater)
        val view=binding.root
        val args=arguments?.getIntArray("Ids")
        val items:List<MenuData> = listOf()
        var adapter=RecyclerViewAdapterUser(items,{},{})
        lifecycleScope.launch(Dispatchers.IO) {
            for (i in args!! ){
                viewModel.selectItem(i)
            }
        }
        viewModel.itemLiveData.observe(viewLifecycleOwner){
            items+it
            adapter.setContent(items)
        }
        binding.makeOrder.setOnClickListener {
            var price = 0.0
            for(i in items){
                price+=i.price
            }
            val bundle=Bundle()
            bundle.putDouble("price",price)
            findNavController().navigate(R.id.action_cartFragment_to_orderFragment,bundle)
        }
        binding.recCart.adapter=adapter
        binding.recCart.layoutManager=LinearLayoutManager(requireContext())
        return view
    }

}