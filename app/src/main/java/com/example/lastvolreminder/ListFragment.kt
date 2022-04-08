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
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.lastvolreminder.bookdatabase.BookDatabase
import com.example.lastvolreminder.databinding.FragmentListBinding
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class ListFragment : Fragment() {

    private var _binding : FragmentListBinding? = null
    private val binding get() = _binding!!
    private val sharedPrefFile = "login_account"
    private var bookDb: BookDatabase? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentListBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val sharedPreferences: SharedPreferences = requireActivity().getSharedPreferences(sharedPrefFile, Context.MODE_PRIVATE)
        binding.recyclerItem.layoutManager = LinearLayoutManager(this.requireContext(), LinearLayoutManager.VERTICAL, false)
        bookDb = BookDatabase.getInstance(this.requireContext())
        fetchData()


        binding.tvBtnLogout.setOnClickListener {
            val editor = sharedPreferences.edit()
            editor.clear()
            editor.apply()
            Toast.makeText(requireContext(), "logout account", Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_listFragment_to_loginFragment)
        }

        binding.fabAdd.setOnClickListener {
            val dialogFragment = AddFragment()
            dialogFragment.show(requireActivity().supportFragmentManager, null)
        }

        binding.swipeAction.setOnRefreshListener {
            fetchData()
            binding.swipeAction.isRefreshing = false
        }
    }

    override fun onResume() {
        super.onResume()
        fetchData()
    }

    fun fetchData(){
        val sharedPreferences: SharedPreferences = requireActivity().getSharedPreferences(sharedPrefFile, Context.MODE_PRIVATE)
        GlobalScope.launch {
            val listBook = sharedPreferences.getString("username", "-")
                ?.let { bookDb?.bookDao()?.getUserBook(it) }

            activity?.runOnUiThread {
                listBook?.let{
                    val adapter = BookAdapter(it)
                    binding.recyclerItem.adapter = adapter
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        BookDatabase.destroyInstance()
    }


}