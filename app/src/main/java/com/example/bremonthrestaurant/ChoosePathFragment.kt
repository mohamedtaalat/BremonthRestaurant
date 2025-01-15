package com.example.bremonthrestaurant

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.bremonthrestaurant.databinding.FragmentChoosePathBinding


class ChoosePathFragment : Fragment() {
    lateinit var binding:FragmentChoosePathBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=FragmentChoosePathBinding.inflate(layoutInflater)
        val view=binding.root

        binding.user.setOnClickListener {
            findNavController().navigate(R.id.action_choosePathFragment_to_logInFragment)
        }
        binding.admin.setOnClickListener {
            findNavController().navigate(R.id.action_choosePathFragment_to_navigationadmin)
        }
        binding.delivery.setOnClickListener {
            findNavController().navigate(R.id.action_choosePathFragment_to_navigationdelivery)
        }
        return view
    }

}