package com.example.lastvolreminder

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.lastvolreminder.databinding.FragmentLoginBinding

class FragmentLogin : Fragment() {

    companion object {
        const val EXTRA_EMAIL = "EXTRA_EMAIL"
    }
    val sharedPrefFile = "login_account"

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding =  FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        val sharedPreferences : SharedPreferences = this.requireActivity().getSharedPreferences(sharedPrefFile, Context.MODE_PRIVATE)

        binding.tvBtnGotoRegister.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }

    }
}