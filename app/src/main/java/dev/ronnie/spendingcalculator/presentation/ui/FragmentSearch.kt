package dev.ronnie.spendingcalculator.presentation.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import dev.ronnie.spendingcalculator.presentation.adapters.SearchMessageAdapter
import dev.ronnie.spendingcalculator.databinding.FragmentSearchBinding
import dev.ronnie.spendingcalculator.utils.InjectorUtils
import dev.ronnie.spendingcalculator.presentation.viewmodels.FragmentSearchViewModel

@AndroidEntryPoint
class FragmentSearch : Fragment() {
    private lateinit var binding: FragmentSearchBinding
    private lateinit var adapter: SearchMessageAdapter
    private val viewModel: FragmentSearchViewModel by viewModels ()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentSearchBinding.inflate(inflater, container, false)
        context ?: return binding.root

        initRecyclerView()

        binding.searchView.setOnEditorActionListener(TextView.OnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                performSearch(binding.searchView.text.toString().trim())
                return@OnEditorActionListener true
            }
            false
        })

        binding.img.setOnClickListener { view ->
            view.findNavController().navigateUp()
        }

        return binding.root
    }

    private fun initRecyclerView() {
        binding.searchRecyclerview.layoutManager = LinearLayoutManager(requireContext())
        adapter = SearchMessageAdapter()
        binding.searchRecyclerview.adapter = adapter
        displayListItems()
    }

    private fun displayListItems() {
        viewModel.messageListLiveData.observe(viewLifecycleOwner, Observer {
            adapter.setList(it)
            adapter.notifyDataSetChanged()
            binding.searchRecyclerview.visibility = View.VISIBLE
            binding.relSearchGlass.visibility = View.GONE
        })
    }

    private fun performSearch(tag: String) {
        viewModel.getTaggedMessages(tag)
    }
}
