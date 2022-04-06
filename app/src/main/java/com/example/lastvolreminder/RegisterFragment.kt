package com.example.lastvolreminder

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.lastvolreminder.databinding.FragmentLoginBinding
import com.example.lastvolreminder.databinding.FragmentRegisterBinding
import java.security.KeyStore

class RegisterFragment : Fragment() {

    val sharedPrefFile = "login_account"

    private var _binding : FragmentRegisterBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val sharedPreferences: SharedPreferences =
            this.requireActivity().getSharedPreferences(sharedPrefFile, Context.MODE_PRIVATE)

        binding.btnRegister.setOnClickListener {
            val username: String = binding.etInputUsername.text.toString()
            val email: String = binding.etInputEmail.text.toString()
            val password: String = binding.etInputPassword.text.toString()
            val confirmPassword: String = binding.etConfirmPassword.text.toString()
            val editor: SharedPreferences.Editor = sharedPreferences.edit()
            when {
                username.isNullOrEmpty() -> binding.etInputUsername.error = "you need to enter an username"
                email.isNullOrEmpty() -> binding.etInputEmail.error = "You need to enter an email"
                password.isNullOrEmpty() -> binding.etInputPassword.error = "You need to enter a password"
                confirmPassword != password -> binding.etConfirmPassword.error = "your password does not match"
                else -> {
                    editor.putString("username", username)
                    editor.putString("email", email)
                    editor.putString("password", password)
                    editor.apply()
                    Toast.makeText(this.requireContext(), "Register successful", Toast.LENGTH_SHORT).show()
                    findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
                }
            }
        }
    }
}
