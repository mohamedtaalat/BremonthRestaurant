package com.example.bremonthrestaurant.user.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.net.toUri
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bremonthrestaurant.R
import com.example.bremonthrestaurant.databinding.FragmentHomeBinding
import com.example.bremonthrestaurant.menuData.MenuData
import com.example.bremonthrestaurant.menuData.MenuDatabase
import com.example.bremonthrestaurant.menuData.MenuRepo
import com.example.bremonthrestaurant.user.recyclerViewUser.RecyclerViewAdapterUser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class HomeFragment : Fragment() {
    lateinit var binding: FragmentHomeBinding
    lateinit var itemsInCart:List<Int>
    val viewModel by viewModels<HomeViewModel> {
        HomeViewModelFactory(MenuRepo(MenuDatabase.getDatabase(requireContext()).dao()))
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val args=arguments?.getString("email")
        binding=FragmentHomeBinding.inflate(layoutInflater)
        val view=binding.root
        var items:List<MenuData> = listOf()
        val adapterUser=RecyclerViewAdapterUser(items,{
            itemsInCart+items.get(it).id
            Toast.makeText(requireContext(),"Add Successful",Toast.LENGTH_SHORT).show()
        },{
            val bundle=Bundle()
            bundle.putInt("Id",items.get(it).id)
            findNavController().navigate(R.id.action_homeFragment_to_displayItemFragment,bundle)
        })
        binding.getLocation.setOnClickListener {
            lifecycleScope.launch(Dispatchers.Default) {
                val uri="https://maps.app.goo.gl/cchdLrwWJqrqsDN98".toUri()
                val intent=Intent(Intent.ACTION_VIEW,uri)
                try {
                    startActivity(intent)
                }catch (ex:Exception){
                    Log.d("Eror", ex.message.toString())
                }
            }
        }
        viewModel.selectAllItems()
        viewModel.itemsLiveData.observe(viewLifecycleOwner){
            items=it
            adapterUser.setContent(items)
        }
        binding.cart.setOnClickListener {
            val bundle=Bundle()
            bundle.putIntArray("Ids",itemsInCart.toIntArray())
            findNavController().navigate(R.id.action_homeFragment_to_cartFragment,bundle)
        }
        binding.logout.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_choosePathFragment)
        }
        binding.contact.setOnClickListener {
            val intent= Intent(Intent.ACTION_DIAL).also {
                val phoneNumber="01094850454"
                it.putExtra("uri",phoneNumber)
            }
            startActivity(intent)
        }
        binding.profile.setOnClickListener {
            val bundle=Bundle()
            bundle.putString("email",args)
            findNavController().navigate(R.id.action_homeFragment_to_profileFragment,bundle)
        }
        binding.recUser.adapter=adapterUser
        binding.recUser.layoutManager=LinearLayoutManager(requireContext())
        return view
    }

}