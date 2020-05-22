package com.hamidjonhamidov.cvforkhamidjon.util.recycler

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.hamidjonhamidov.cvforkhamidjon.R
import com.hamidjonhamidov.cvforkhamidjon.models.offline.contact.MessageModel
import de.hdodenhof.circleimageview.CircleImageView

class ContactMeViewHolderHim (
    itemView: View
): RecyclerView.ViewHolder(itemView) {

    private val TAG = "AppDebug"

    fun bind(item: MessageModel) = with(item){
        // bind message
        val msgTv = itemView.findViewById<TextView>(R.id.contactme_tv_him)
        msgTv.text = item.msg

        // bind isRead status
        val msgIvRead = itemView.findViewById<CircleImageView>(R.id.contactme_iv_read_him)
        val msgIvUnread = itemView.findViewById<CircleImageView>(R.id.contactme_iv_unread_him)
        if(item.status==MessageModel.STATUS_SENT){
            msgIvRead.visibility = View.VISIBLE
            msgIvUnread.visibility = View.GONE
        } else {
            msgIvUnread.visibility = View.VISIBLE
            msgIvRead.visibility = View.GONE
        }
        Unit
    }
}