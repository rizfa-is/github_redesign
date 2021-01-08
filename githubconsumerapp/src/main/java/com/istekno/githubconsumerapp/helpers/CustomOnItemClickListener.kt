package com.istekno.githubconsumerapp.helpers

import android.view.View

class CustomOnItemClickListener(private val position: Int): View.OnClickListener {

    private lateinit var onItemClickCallback: OnItemClickCallback

    override fun onClick(v: View) {
        onItemClickCallback.onItemClicked(v, position)
    }

    fun onItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    interface OnItemClickCallback {
        fun onItemClicked(view: View, position: Int)
    }
}