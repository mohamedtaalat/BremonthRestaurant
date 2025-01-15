package com.example.bremonthrestaurant.user.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.bremonthrestaurant.R
import com.example.bremonthrestaurant.databinding.FragmentDisplayOrderBinding
import com.example.bremonthrestaurant.orderData.OrderDatabase
import com.example.bremonthrestaurant.orderData.OrderRepo
import com.example.bremonthrestaurant.orderData.OrderState
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class DisplayOrderFragment : Fragment() {
    lateinit var binding:FragmentDisplayOrderBinding
    val viewModel by viewModels<OrderViewModel> {
        OrderViewModelFactory(OrderRepo(OrderDatabase.getDatabase(requireContext()).dao()))
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=FragmentDisplayOrderBinding.inflate(layoutInflater)
        val view=binding.root
        binding.search.setOnClickListener {
            if (binding.edOrderId.text.toString()!=""){
                lifecycleScope.launch(Dispatchers.IO) {
                    viewModel.getOrder(binding.edOrderId.text.toString().toInt())
                }
            }
        }
        viewModel.orderLiveData.observe(viewLifecycleOwner){
            binding.orderId.text="Order id is : "+it.id
            binding.orderLocation.text="Order location is : "+it.location
            binding.orderPrice.text="Order price is : "+it.price
            binding.orderState.text="Order state is : "+it.orderState
        }
        binding.cancelOrder.setOnClickListener {
            if (binding.edOrderId.text.toString()!=""){
                viewModel.cancelOrder(binding.edOrderId.text.toString().toInt())
                binding.orderState.text="Order state is : "+OrderState.Canceled
            }
            else{
                binding.edOrderId.setError("require")
                Snackbar.make(binding.textView8,"order id require",Snackbar.LENGTH_SHORT).show()
            }
        }
        return view
    }
}