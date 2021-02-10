package dev.ronnie.spendingcalculator.presentation.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import dev.ronnie.spendingcalculator.R
import dev.ronnie.spendingcalculator.databinding.FragmentSmsListBinding
import dev.ronnie.spendingcalculator.domain.Message
import dev.ronnie.spendingcalculator.presentation.adapters.MessageAdapter
import dev.ronnie.spendingcalculator.presentation.viewmodels.FragmentListViewModel

@AndroidEntryPoint
class FragmentSmsList : Fragment(R.layout.fragment_sms_list) {
    private lateinit var binding: FragmentSmsListBinding
    private val viewModel: FragmentListViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentSmsListBinding.bind(view)

        val toolbar: Toolbar = binding.toolbarShowSms as Toolbar
        (activity as AppCompatActivity).setSupportActionBar(toolbar)
        (activity as AppCompatActivity).supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        (activity as AppCompatActivity).supportActionBar!!.setHomeButtonEnabled(true)
        (activity as AppCompatActivity).supportActionBar!!.title = arguments?.getString("type")

        toolbar.setNavigationOnClickListener {
            it.findNavController().navigateUp()
        }

        val list = arguments?.getParcelableArrayList<Message>("list") as List<Message>

        setAdapter(list)


    }

    private fun setAdapter(list: List<Message>) {

        binding.recyclerview.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerview.adapter =
            MessageAdapter(
                list as ArrayList<Message>,
                { tag: String?, message: Message -> addOrEditClicked(tag, message) },
                { message: Message -> checkIfMessageHasTag(message) }
            )

    }

    private fun checkIfMessageHasTag(message: Message): String? =
        viewModel.checkIfMessagedHasTag(message.id)

    private fun addOrEditClicked(tag: String?, message: Message) {

        val modalSheet =
            AddTagModalSheet()

        val bundle = Bundle()
        bundle.putString("tag", tag)
        bundle.putParcelable("message", message)
        modalSheet.arguments = bundle
        modalSheet.show(requireActivity().supportFragmentManager, "")

    }
}
