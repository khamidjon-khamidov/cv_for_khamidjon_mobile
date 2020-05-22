package com.hamidjonhamidov.cvforkhamidjon.util.recycler

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.hamidjonhamidov.cvforkhamidjon.R
import com.hamidjonhamidov.cvforkhamidjon.models.offline.contact.MessageModel
import com.hamidjonhamidov.cvforkhamidjon.models.offline.contact.MessageModel.Companion.WHO_ME

class ContactMeAdapter
    : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val DIFF_CALLBACK = object : DiffUtil.ItemCallback<MessageModel>() {
        override fun areContentsTheSame(oldItem: MessageModel, newItem: MessageModel): Boolean {
            Log.d(TAG, "ContactMeAdapter: areContentsTheSame: ")
            return newItem == oldItem
        }

        override fun areItemsTheSame(oldItem: MessageModel, newItem: MessageModel): Boolean {
            return newItem.order == oldItem.order && newItem.status == oldItem.status && newItem.msg==oldItem.msg
        }
    }

    private val differ = AsyncListDiffer(this, DIFF_CALLBACK)
    
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        Log.d(TAG, "ContactMeAdapter: onCreateViewHolder: ")
        return when (viewType) {
            VIEW_TYPE_HIM -> {
                ContactMeViewHolderHim(
                    LayoutInflater.from(parent.context).inflate(
                        R.layout.single_message_him,
                        parent,
                        false
                    )
                )
            }

            else -> {
                ContactMeViewHolderMe(
                    LayoutInflater.from(parent.context).inflate(
                        R.layout.single_message_me,
                        parent,
                        false
                    )
                )
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ContactMeViewHolderMe -> {
                Log.d(TAG, "ContactMeAdapter: onBindViewHolder: me ${position}")
                holder.bind(differ.currentList[position])
            }

            is ContactMeViewHolderHim -> {
                Log.d(TAG, "ContactMeAdapter: onBindViewHolder: him ${position}")
                holder.bind(differ.currentList[position])
            }
        }
    }


    override fun getItemViewType(position: Int) =
        if (differ.currentList[position].toWhom == WHO_ME) {
            VIEW_TYPE_ME
        } else {
            VIEW_TYPE_HIM
        }


    override fun getItemCount(): Int {
        return differ.currentList.size
    }


    private val TAG = "AppDebug"

    fun submitList(list: List<MessageModel>) {
        differ.submitList(list)
    }

    companion object {
        const val VIEW_TYPE_ME = 0
        const val VIEW_TYPE_HIM = 1
    }
}