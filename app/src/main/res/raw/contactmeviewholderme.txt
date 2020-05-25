package com.hamidjonhamidov.cvforkhamidjon.util.recycler

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.hamidjonhamidov.cvforkhamidjon.R
import com.hamidjonhamidov.cvforkhamidjon.models.offline.contact.MessageModel

class ContactMeViewHolderMe (
    itemView: View
): RecyclerView.ViewHolder(itemView) {

    private val TAG = "AppDebug"

    fun bind(item: MessageModel) = with(item){
        // bind message
        val msgTv = itemView.findViewById<TextView>(R.id.contactme_tv_me)
        msgTv.text = item.msg
        Unit
    }
}



















