package com.picpay.desafio.android.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import com.picpay.desafio.android.R
import com.picpay.desafio.android.data.state.ViewState
import com.picpay.desafio.android.databinding.HomeFragmentBinding
import com.picpay.desafio.android.widget.HeaderAdapter
import com.picpay.desafio.android.widget.UserListAdapter
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : Fragment() {

    private val viewModel: HomeViewModel by viewModel()
    private val usersAdapter: UserListAdapter by inject()
    private val headerAdapter: HeaderAdapter by inject()

    private lateinit var homeFragmentBinding: HomeFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.home_fragment, container, false)
        homeFragmentBinding = HomeFragmentBinding.bind(view)
        return homeFragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViews()
        setupObservers()

        viewModel.getAllUsers()
    }

    private fun setupViews() {
        val adapters = ConcatAdapter(headerAdapter, usersAdapter)

        homeFragmentBinding.recycleView.apply {
            this.adapter = adapters
            this.layoutManager = LinearLayoutManager(requireContext())
        }

        homeFragmentBinding.tryAgainButton.setOnClickListener {
            viewModel.getAllUsers()
        }
    }

    private fun setupObservers() {
        viewModel.viewState.observe(viewLifecycleOwner, Observer { viewState ->
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

                    usersAdapter.users = viewState.data
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
        homeFragmentBinding.txtError.isVisible = txtVisibility
        homeFragmentBinding.tryAgainButton.isVisible = buttonVisibility
        homeFragmentBinding.recycleView.isVisible = recycleViewVisibility
        homeFragmentBinding.progressCircular.isVisible = progressBarVisibility
    }

}