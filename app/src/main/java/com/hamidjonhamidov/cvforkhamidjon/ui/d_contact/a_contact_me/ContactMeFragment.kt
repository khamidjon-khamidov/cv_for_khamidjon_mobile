package com.hamidjonhamidov.cvforkhamidjon.ui.d_contact.a_contact_me

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.hamidjonhamidov.cvforkhamidjon.R
import com.hamidjonhamidov.cvforkhamidjon.ui.d_contact.viewmodel.ContactViewModel
import com.hamidjonhamidov.cvforkhamidjon.ui.d_contact.viewmodel.getMessages
import com.hamidjonhamidov.cvforkhamidjon.ui.d_contact.viewmodel.state.ContactsStateEvent.GetMessages
import com.hamidjonhamidov.cvforkhamidjon.ui.d_contact.viewmodel.state.ContactsStateEvent.SendMessage
import com.hamidjonhamidov.cvforkhamidjon.util.recycler.ContactMeAdapter
import kotlinx.android.synthetic.main.fragment_contact_me.*
import kotlinx.coroutines.*

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
        setHasOptionsMenu(true)

        initData()
        initRecyclerView()
        bindViews()
        subscribeObservers()
    }

    override fun onResume() {
        super.onResume()
        viewModel.sendUnsentMessages()
    }

    private fun initRecyclerView() {
        contact_rv_contact_me.apply {
            val mLayoutManager = LinearLayoutManager(
                this@ContactMeFragment.context
            )
            setHasFixedSize(true)
            mLayoutManager.reverseLayout = true

            layoutManager = mLayoutManager
            listAdapter = ContactMeAdapter()
            adapter = listAdapter
        }

        // when keyboard changes it should be scrolled accordingly
        contact_rv_contact_me.addOnLayoutChangeListener { _, _, _, _, bottom, _, _, _, oldBottom ->
            if (bottom > oldBottom || oldBottom > bottom) {
                scrollToPosAfterDelay(0)
            }
        }
    }

    private fun initData() {
        if (viewModel.getMessages().isNotEmpty()) {
            listAdapter.submitList(viewModel.getMessages())
        } else {
            viewModel.setStateEvent(GetMessages())
        }
    }

    private fun subscribeObservers() {
        viewModel.viewState.observe(viewLifecycleOwner, Observer {
            Log.d(TAG, "ContactMeFragment: subscribeObserversContact: ${viewModel.getMessages().size}")
            listAdapter.submitList(viewModel.getMessages())
            scrollToPosAfterDelay(0)
            viewModel.sendUnsentMessages()
        })

        viewModel.updateNotifier.observe(viewLifecycleOwner, Observer {
            listAdapter.submitList(viewModel.getMessages())
            if (it.insertedPos != -1) {
                listAdapter.notifyItemInserted(it.insertedPos)
                scrollToPosAfterDelay(0)

            } else if (it.updatedPos != -1) {
                listAdapter.submitList(viewModel.getMessages())
                listAdapter.notifyItemChanged(it.updatedPos)
            }
        })
    }

    private fun bindViews() {
        contact_iv_send_ic.setOnClickListener {
            contact_et_send.text?.toString()?.trim()?.let {
                if (it != "") {
                    contact_et_send.setText("")
                    viewModel.setStateEvent(SendMessage(it))
                }
            }
        }
    }

    private fun scrollToPosAfterDelay(pos: Int) {
        // something not working need some delay
        lifecycleScope.launch {
            delay(100)
            withContext(Dispatchers.Main) {
                contact_rv_contact_me.smoothScrollToPosition(pos)
            }
        }
    }
}




















