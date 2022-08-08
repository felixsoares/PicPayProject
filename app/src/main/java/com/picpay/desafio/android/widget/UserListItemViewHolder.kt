package com.picpay.desafio.android.widget

import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.picpay.desafio.android.R
import com.picpay.desafio.android.data.model.User
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso

class UserListItemViewHolder(
    itemView: View
) : RecyclerView.ViewHolder(itemView) {

    private var nameTxt: TextView = itemView.findViewById(R.id.name)
    private var userNameTxt: TextView = itemView.findViewById(R.id.username)
    private var progressBar: ProgressBar = itemView.findViewById(R.id.progressBar)
    private var userImage: ImageView = itemView.findViewById(R.id.picture)

    fun bind(user: User) {
        nameTxt.text = user.name
        userNameTxt.text = user.username
        progressBar.visibility = View.VISIBLE
        Picasso.get()
            .load(user.img)
            .error(R.drawable.ic_round_account_circle)
            .into(userImage, object : Callback {
                override fun onSuccess() {
                    progressBar.visibility = View.GONE
                }

                override fun onError(e: Exception?) {
                    progressBar.visibility = View.GONE
                }
            })
    }
}