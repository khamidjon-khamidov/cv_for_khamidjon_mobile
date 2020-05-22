package com.hamidjonhamidov.cvforkhamidjon.ui.d_contact.a_contact_me

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager

import com.hamidjonhamidov.cvforkhamidjon.R
import com.hamidjonhamidov.cvforkhamidjon.models.offline.contact.MessageModel
import com.hamidjonhamidov.cvforkhamidjon.ui.d_contact.viewmodel.ContactViewModel
import com.hamidjonhamidov.cvforkhamidjon.ui.d_contact.viewmodel.addMessageToList
import com.hamidjonhamidov.cvforkhamidjon.ui.d_contact.viewmodel.state.ContactsStateEvent
import com.hamidjonhamidov.cvforkhamidjon.util.recycler.ContactMeAdapter
import kotlinx.android.synthetic.main.fragment_contact_me.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.InternalCoroutinesApi

@ExperimentalCoroutinesApi
@FlowPreview
@InternalCoroutinesApi
class ContactMeFragment(
    viewModelProvider: ViewModelProvider.Factory
) : Fragment(R.layout.fragment_contact_me) {

    private val TAG = "AppDebug"

    val viewModel: ContactViewModel by activityViewModels {
        viewModelProvider
    }

    lateinit var listAdapter: ContactMeAdapter


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initData()
        initRecyclerView()
        bindButtons()
        subscribeObservers()
    }

    private fun initRecyclerView(){
        contact_rv_contact_me.apply {
            layoutManager = LinearLayoutManager(this@ContactMeFragment.context, LinearLayoutManager.VERTICAL, true)
            listAdapter = ContactMeAdapter()
            adapter = listAdapter
        }
    }

    private fun initData() {
        ifMessagesNotNullSubmit()
        if(getMessages()==null){
            viewModel.setStateEvent(ContactsStateEvent.GetMessages())
        }
    }

    private fun subscribeObservers() {
        viewModel.viewState.observe(viewLifecycleOwner, Observer {
            ifMessagesNotNullSubmit()
        })

        viewModel.notifier.observe(viewLifecycleOwner, Observer {
            Log.d(TAG, "ContactMeFragment: subscribeObservers: message notified")
            ifMessagesNotNullSubmit()
        })
    }

    private fun bindButtons() {
        contact_iv_send_ic.setOnClickListener {
            contact_et_send.text?.toString()?.trim()?.let{
                if(it!=""){
                    val mMessage = MessageModel(
                        getOrder(),
                        MessageModel.WHO_HIM,
                        it,
                        MessageModel.STATUS_NOT_SENT
                    )
                    contact_et_send.setText("")
                    viewModel.addMessageToList(mMessage)
                    listAdapter.notifyItemInserted(0)
                    viewModel.setStateEvent(ContactsStateEvent.SendMessage(mMessage))
                }
            }
        }
    }

    private fun getOrder(): Int {
        if(getMessages().isNullOrEmpty()){
            return 1
        } else {
            return getMessages()!![0].order+1
        }
    }

    private fun ifMessagesNotNullSubmit(){
        getMessages()?.let {
            if(it.size>0) contact_rv_contact_me.scrollToPosition(0)
            listAdapter.submitList(it)
        }
    }

    private fun getMessages() =
        viewModel.viewState.value?.contactMeFragmentView?.messages
}




















