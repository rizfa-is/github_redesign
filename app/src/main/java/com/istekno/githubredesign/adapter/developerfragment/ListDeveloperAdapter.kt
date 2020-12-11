package com.istekno.githubredesign.adapter.developerfragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.istekno.githubredesign.R
import com.istekno.githubredesign.data.DeveloperList
import kotlinx.android.synthetic.main.item_row_developer.view.*

class ListDeveloperAdapter(private val listDeveloperList: ArrayList<DeveloperList>, private val onItemClickCallback: OnItemClickCallback) : RecyclerView.Adapter<ListDeveloperAdapter.ListViewHolder>() {

    interface OnItemClickCallback {
        fun onItemClicked(developerList: DeveloperList)
    }

    inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val devName : TextView = itemView.findViewById(R.id.tv_item_row_developer_name)

        fun bind(DeveloperList: DeveloperList) {
            Glide.with(itemView.context)
                .load(DeveloperList.avatar)
                .apply(RequestOptions().override(55, 55))
                .into(itemView.img_item_row_developer)

            this.devName.text = DeveloperList.username

            this.itemView.setOnClickListener { Toast.makeText(itemView.context, "Selected " + DeveloperList.username, Toast.LENGTH_SHORT).show() }
            this.itemView.setOnClickListener { onItemClickCallback.onItemClicked(listDeveloperList[this.adapterPosition]) }
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, position: Int): ListViewHolder {
        val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.item_row_developer, viewGroup, false)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bind(listDeveloperList[position])
    }

    override fun getItemCount(): Int = listDeveloperList.size
}