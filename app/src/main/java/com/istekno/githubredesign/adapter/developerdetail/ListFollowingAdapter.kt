package com.istekno.githubredesign.adapter.developerdetail

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.istekno.githubredesign.R
import com.istekno.githubredesign.model.Follows
import kotlinx.android.synthetic.main.item_row_developer.view.*

class ListFollowingAdapter(private val listFollowers: ArrayList<Follows>) : RecyclerView.Adapter<ListFollowingAdapter.ListViewHolder>() {

    inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val devName : TextView = itemView.findViewById(R.id.tv_item_row_developer_name)

        fun bind(follows: Follows) {
            Glide.with(itemView.context)
                .load(follows.avatar)
                .apply(RequestOptions().override(55, 55))
                .into(itemView.img_item_row_developer)

            this.devName.text = follows.name

            this.itemView.setOnClickListener { Toast.makeText(itemView.context, "Selected " + follows.name, Toast.LENGTH_SHORT).show() }
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, position: Int): ListViewHolder {
        val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.item_row_developer, viewGroup, false)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bind(listFollowers[position])
    }

    override fun getItemCount(): Int = listFollowers.size
}