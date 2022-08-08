package com.picpay.desafio.android.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
import com.picpay.desafio.android.R
import com.picpay.desafio.android.data.state.ViewState
import com.picpay.desafio.android.widget.UserListAdapter
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : Fragment() {

    private val viewModel: HomeViewModel by viewModel()
    private val adapter: UserListAdapter by inject()

    private lateinit var txtError: TextView
    private lateinit var tryAgainButton: Button
    private lateinit var recyclerView: RecyclerView
    private lateinit var progressBar: ProgressBar

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.home_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews(view)
        setupViews()
        setupObservers()

        viewModel.getAllUsers()
    }

    private fun initViews(view: View) {
        txtError = view.findViewById(R.id.txt_error)
        tryAgainButton = view.findViewById(R.id.try_again_button)
        recyclerView = view.findViewById(R.id.recycle_view)
        progressBar = view.findViewById(R.id.progress_circular)
    }

    private fun setupViews() {
        with(recyclerView) {
            this.adapter = this@HomeFragment.adapter
            this.layoutManager = LinearLayoutManager(this@HomeFragment.requireContext())
        }

        tryAgainButton.setOnClickListener {
            viewModel.getAllUsers()
        }
    }

    private fun setupObservers() {
        viewModel.viewState.observe(this, Observer { viewState ->
            when (viewState) {
                is ViewState.Loading -> {
                    progressBarVisibility(
                        txtVisibility = false,
                        buttonVisibility = false,
                        recycleViewVisibility = false,
                        progressBarVisibility = true
                    )
                }
                is ViewState.ContentLoaded -> {
                    progressBarVisibility(
                        txtVisibility = false,
                        buttonVisibility = false,
                        recycleViewVisibility = true,
                        progressBarVisibility = false
                    )

                    adapter.users = viewState.data
                }
                is ViewState.ContentFailure -> {
                    progressBarVisibility(
                        txtVisibility = true,
                        buttonVisibility = true,
                        recycleViewVisibility = false,
                        progressBarVisibility = false
                    )
                }
            }
        })
    }

    private fun progressBarVisibility(
        txtVisibility: Boolean,
        buttonVisibility: Boolean,
        recycleViewVisibility: Boolean,
        progressBarVisibility: Boolean
    ) {
        txtError.isVisible = txtVisibility
        tryAgainButton.isVisible = buttonVisibility
        recyclerView.isVisible = recycleViewVisibility
        progressBar.isVisible = progressBarVisibility
    }

}