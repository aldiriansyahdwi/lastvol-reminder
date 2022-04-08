package com.example.lastvolreminder

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.example.lastvolreminder.bookdatabase.Book
import com.example.lastvolreminder.bookdatabase.BookDatabase
import com.example.lastvolreminder.databinding.FragmentAddBinding
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async

class AddFragment : DialogFragment() {

    private var _binding: FragmentAddBinding? = null
    private val binding get() = _binding!!

    private var sharedPreFile = "login_account"
    private var bookDb: BookDatabase? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        dialog?.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val sharedPreferences: SharedPreferences =
            this.requireActivity().getSharedPreferences(sharedPreFile, Context.MODE_PRIVATE)
        bookDb = BookDatabase.getInstance(this.requireContext())
        binding.btnAdd.setOnClickListener {
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
                        val editResult = objectBook?.let { it1 -> bookDb?.bookDao()?.insertUserBook(it1) }
                        activity?.runOnUiThread {
                            if(editResult != 0.toLong()){
                                Toast.makeText(requireContext(), "Book Added", Toast.LENGTH_SHORT).show()
                            }
                            else{
                                Toast.makeText(requireContext(), "add failed", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                }
            }
            dialog?.dismiss()
        }
    }

}