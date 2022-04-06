package com.example.lastvolreminder

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.lastvolreminder.databinding.FragmentRegisterBinding
import com.example.lastvolreminder.userdatabase.User
import com.example.lastvolreminder.userdatabase.UserDatabase
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async


class RegisterFragment : Fragment() {

    var userDb: UserDatabase? = null
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

        userDb = UserDatabase.getInstance(this.requireContext())

        binding.btnRegister.setOnClickListener {
            val username: String = binding.etInputUsername.text.toString()
            val email: String = binding.etInputEmail.text.toString()
            val password: String = binding.etInputPassword.text.toString()
            val confirmPassword: String = binding.etConfirmPassword.text.toString()

            when {
                username.isNullOrEmpty() -> binding.etInputUsername.error = "you need to enter an username"
//                usernameOnConflict(username) -> binding.etInputUsername.error = "username already used"
                email.isNullOrEmpty() -> binding.etInputEmail.error = "You need to enter an email"
                password.isNullOrEmpty() -> binding.etInputPassword.error = "You need to enter a password"
                confirmPassword != password -> binding.etConfirmPassword.error = "your password does not match"
                else ->{
                    val objectUser = User(
                        email, username, password
                    )

                    GlobalScope.async {
                        val result = userDb?.userDao()?.insertUser(objectUser)
                        activity?.runOnUiThread {
                            if(result != 0.toLong()){
                                Toast.makeText(requireContext(), "Register Successfully", Toast.LENGTH_SHORT).show()
                            }
                            else{
                                Toast.makeText(requireContext(), "Register failed", Toast.LENGTH_SHORT).show()
                            }
                            findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
                        }
                    }
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
//    fun usernameOnConflict(username: String): Boolean{
//        val check = userDb?.userDao()?.getAllUser()
//        var condition = false
//        check?.forEach {
//            if(it.username == username){
//                condition = true
//            }
//        }
//        return condition
//    }
}
