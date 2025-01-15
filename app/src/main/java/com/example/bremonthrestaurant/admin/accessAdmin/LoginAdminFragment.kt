package com.example.bremonthrestaurant.admin.accessAdmin

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.bremonthrestaurant.R
import com.example.bremonthrestaurant.databinding.FragmentLoginAdminBinding
import com.google.android.material.snackbar.Snackbar


class LoginAdminFragment : Fragment() {
    lateinit var binding:FragmentLoginAdminBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=FragmentLoginAdminBinding.inflate(layoutInflater)
        val view=binding.root
        val admin=Admin(email = "mohamedabotalat57@gmail.com", name = "mohamed talat", password = "12345678")
        binding.logAdmin.setOnClickListener {
            logAdmin(admin)
        }
        return view
    }

    private fun logAdmin(admin: Admin) {
        if (binding.emailAdmin.text.toString() == admin.email) {
            if (binding.passwordAdmin.text.toString() == admin.password) {
                findNavController().navigate(R.id.action_loginAdminFragment_to_homeAdminFragment)
            } else {
                binding.passwordAdmin.setError("wrong")
                Snackbar.make(binding.textView10, "password require", Snackbar.LENGTH_SHORT).show()
            }
        } else {
            binding.emailAdmin.setError("wrong")
            Snackbar.make(binding.textView10, "email require", Snackbar.LENGTH_SHORT).show()
        }
    }
}