package dev.ronnie.spendingcalculator.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import dev.ronnie.spendingcalculator.R
import dev.ronnie.spendingcalculator.databinding.MessageItemBinding
import dev.ronnie.spendingcalculator.domain.Message


class MessageAdapter(

    private var list: ArrayList<Message>,
    private val clickListener: (String?, Message) -> Unit,
    private val checkIfMessageHasTag: (Message) -> String?

) :
    RecyclerView.Adapter<MessageAdapter.MyViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val activityAdapterBinding = DataBindingUtil.inflate<MessageItemBinding>(
            LayoutInflater
                .from(parent.context),
            R.layout.message_item, parent, false
        )
        return MyViewHolder(activityAdapterBinding)
    }

    override fun getItemCount(): Int {
        return list.size
    }


    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val message = list[position]
        holder.binding.body.text = message.body
        holder.binding.name.text = message.number

        val tag = checkIfMessageHasTag(message)

        tag?.let {
            holder.binding.addTag.text = holder.binding.root.context.getString(R.string.edit_tag)
        }

        holder.tag = tag
        holder.message = message

    }

    inner class MyViewHolder(var binding: MessageItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        var tag: String? = null
        var message: Message? = null

        init {
            binding.addTag.setOnClickListener {
                clickListener(tag, message!!)
            }

        }


    }

}