package com.example.bremonthrestaurant.user.home

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
import com.example.bremonthrestaurant.databinding.FragmentOrderBinding
import com.example.bremonthrestaurant.orderData.Order
import com.example.bremonthrestaurant.orderData.OrderDatabase
import com.example.bremonthrestaurant.orderData.OrderRepo
import com.example.bremonthrestaurant.orderData.OrderState
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class OrderFragment : Fragment() {
   lateinit var binding:FragmentOrderBinding
   val viewModel by viewModels<OrderViewModel> {
       OrderViewModelFactory(OrderRepo(OrderDatabase.getDatabase(requireContext()).dao()))
   }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=FragmentOrderBinding.inflate(layoutInflater)
        val view =binding.root
        val args=arguments?.getDouble("price")
        binding.priceOrder.text=args.toString()
        binding.submitOrder.setOnClickListener {
            lifecycleScope.launch(Dispatchers.IO) {
                makeOrder(args)
            }
        }
        return view
    }

    private fun makeOrder(args: Double?) {
        if (binding.locationOrder.text.toString() != "") {
            val order =
                Order(0, binding.locationOrder.text.toString(), args!!, OrderState.Preparing)
            viewModel.addOrder(order)
            Toast.makeText(requireContext(), "Order is " + OrderState.Preparing, Toast.LENGTH_SHORT)
                .show()
            findNavController().navigate(R.id.action_orderFragment_to_homeFragment)
        } else {
            binding.locationOrder.setError("required")
            Snackbar.make(binding.textView7, "need location", Snackbar.LENGTH_SHORT).show()
        }
    }


}