package com.example.lastvolreminder

import android.app.Dialog
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.findNavController
import com.example.lastvolreminder.bookdatabase.Book
import com.example.lastvolreminder.bookdatabase.BookDatabase
import com.example.lastvolreminder.databinding.FragmentEditBinding
import com.example.lastvolreminder.userdatabase.User
import com.example.lastvolreminder.userdatabase.UserDatabase
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async

class EditFragment : DialogFragment() {

    private var _binding : FragmentEditBinding? = null
    private val binding get() = _binding!!

    val sharedPrefFile = "login_account"
    var bookDb: BookDatabase? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentEditBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        dialog?.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val sharedPreferences: SharedPreferences =
            this.requireActivity().getSharedPreferences(sharedPrefFile, Context.MODE_PRIVATE)
        bookDb = BookDatabase.getInstance(this.requireContext())
        binding.btnEdit.setOnClickListener {
            when {
                binding.etInputTitle.text.isNullOrEmpty() -> binding.etInputTitle.error =
                    "Input your book title"
                binding.etInputVolume.text.isNullOrEmpty() -> binding.etInputVolume.error =
                    "input at least 0 volume"
                else -> {
                    val objectBook = sharedPreferences.getString("username", "-")?.let { it1 ->
                        Book(
                            binding.etInputTitle.text.toString(),
                            it1,
                            binding.etInputVolume.text.toString().toInt(),
                            binding.cbComplete.isChecked
                        )
                    }
                    GlobalScope.async {
                        val editResult = objectBook?.let { it1 -> bookDb?.bookDao()?.updateBook(it1) }
                        activity?.runOnUiThread {
                            if(editResult != 0){
                                Toast.makeText(requireContext(), "Book Edited", Toast.LENGTH_SHORT).show()
                            }
                            else{
                                Toast.makeText(requireContext(), "edit failed", Toast.LENGTH_SHORT).show()
                            }
                            onStop()
                        }
                    }
                }
            }

        }
    }
}