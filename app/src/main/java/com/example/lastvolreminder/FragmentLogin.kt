package com.example.lastvolreminder

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.lastvolreminder.databinding.FragmentLoginBinding
import com.example.lastvolreminder.userdatabase.UserDatabase
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async

class FragmentLogin : Fragment() {

    companion object {
        const val EXTRA_EMAIL = "EXTRA_EMAIL"
    }
    val sharedPrefFile = "login_account"
    var userDb: UserDatabase? = null

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
        val sharedPreferences : SharedPreferences = this.requireActivity().getSharedPreferences(sharedPrefFile, Context.MODE_PRIVATE)
        userDb = UserDatabase.getInstance(this.requireContext())

        val sharedEmail = sharedPreferences.getString("email", "-")
        val sharedPassword = sharedPreferences.getString("password", "-")

        if(sharedEmail != "-" && sharedPassword != "-"){
            findNavController().navigate(R.id.action_loginFragment_to_listFragment)
        }

        binding.tvBtnGotoRegister.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }

        binding.btnLogin.setOnClickListener {
            val email = binding.etInputEmail.text.toString()
            val password = binding.etInputPassword.text.toString()

            GlobalScope.async {
                val verif = userDb?.userDao()?.checkLogin(email, password)


                activity?.runOnUiThread {
                    if(verif.isNullOrEmpty()){
                        Toast.makeText(it.context, "account not found", Toast.LENGTH_SHORT).show()
                    }
                    else{
                        val editor: SharedPreferences.Editor = sharedPreferences.edit()
                        editor.putString("email", email)
                        editor.putString("password", password)
                        editor.putString("username", verif[0].username)
                        editor.apply()
                        Toast.makeText(it.context, "hello ${verif[0].username}", Toast.LENGTH_SHORT).show()
                        findNavController().navigate(R.id.action_loginFragment_to_listFragment)
                    }
                }
            }
        }

        
    }
//    fun loginVerification(email: String, password: String): List<User>? {
//        return userDb?.userDao()?.checkLogin(email, password)
//    }
}