package dev.ronnie.spendingcalculator.presentation.ui

import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.scopes.ActivityRetainedScoped
import dagger.hilt.android.scopes.ActivityScoped
import dev.ronnie.spendingcalculator.R
import dev.ronnie.spendingcalculator.databinding.FragmentSearchBinding
import dev.ronnie.spendingcalculator.presentation.adapters.SearchMessageAdapter
import dev.ronnie.spendingcalculator.presentation.viewmodels.FragmentSearchViewModel

@AndroidEntryPoint
@ActivityScoped
class FragmentSearch : Fragment(R.layout.fragment_search) {
    private lateinit var binding: FragmentSearchBinding
    private lateinit var adapter: SearchMessageAdapter
    private val viewModel: FragmentSearchViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentSearchBinding.bind(view)

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
    }

    private fun initRecyclerView() {
        binding.searchRecyclerview.layoutManager = LinearLayoutManager(requireContext())
        adapter = SearchMessageAdapter()
        binding.searchRecyclerview.adapter = adapter
        displayListItems()
    }

    private fun displayListItems() {
        viewModel.tagListLiveData.observe(viewLifecycleOwner, { tags ->
            adapter.setList(tags.map { it.message })
            adapter.notifyDataSetChanged()
            binding.searchRecyclerview.visibility = View.VISIBLE
            binding.relSearchGlass.visibility = View.GONE
        })
    }

    private fun performSearch(tag: String) {
        viewModel.getTaggedMessages(tag)
    }
}
