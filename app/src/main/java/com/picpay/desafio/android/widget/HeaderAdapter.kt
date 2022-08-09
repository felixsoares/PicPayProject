package com.picpay.desafio.android.widget

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.picpay.desafio.android.R
import com.picpay.desafio.android.databinding.HeaderItemBinding

class HeaderAdapter : RecyclerView.Adapter<HeaderViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HeaderViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.header_item, parent, false)
        val binding = HeaderItemBinding.bind(view)
        return HeaderViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HeaderViewHolder, position: Int) = Unit

    override fun getItemCount(): Int = 1
}