package dev.ronnie.spendingcalculator.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import dev.ronnie.spendingcalculator.data.Message
import dev.ronnie.spendingcalculator.R
import dev.ronnie.spendingcalculator.databinding.MessageItemBinding


class MessageAdapter(

    private var list: ArrayList<Message>,
    private val clickListener: (String?, String) -> Unit,
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

        if (tag != null) {
            holder.binding.addTag.text = holder.binding.root.context.getString(R.string.edit_tag)
        }

        holder.tag = tag
        holder.id = message.id

    }

    inner class MyViewHolder(var binding: MessageItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        var tag: String? = null
        var id: String? = null

        init {
            binding.addTag.setOnClickListener {
                clickListener(tag, id!!)
            }

        }


    }

}