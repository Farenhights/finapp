package com.finapp.presentation.screens.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.finapp.R
import com.finapp.databinding.ItemTransactionBinding
import com.finapp.domain.model.Transaction
import com.finapp.domain.model.TransactionType

class TransactionAdapter :
    ListAdapter<Transaction, TransactionAdapter.TransactionViewHolder>(TransactionDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionViewHolder {
        val binding = ItemTransactionBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return TransactionViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TransactionViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class TransactionViewHolder(
        private val binding: ItemTransactionBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Transaction) = with(binding){
            descriptionText.text = item.description
            dateText.text = item.date
            valueText.text = "R$ %.2f".format(item.amount)

            if (item.type == TransactionType.CREDIT) {
                valueText.setTextColor(ContextCompat.getColor(root.context, R.color.green))
                typeIcon.setImageResource(R.drawable.ic_credit)
            } else {
                valueText.setTextColor(ContextCompat.getColor(root.context, R.color.red))
                typeIcon.setImageResource(R.drawable.ic_debit)
            }
        }
    }
}

class TransactionDiffCallback : DiffUtil.ItemCallback<Transaction>() {
    override fun areItemsTheSame(oldItem: Transaction, newItem: Transaction): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Transaction, newItem: Transaction): Boolean =
        oldItem == newItem
}