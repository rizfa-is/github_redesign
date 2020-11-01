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
import com.istekno.githubredesign.data.Developer
import kotlinx.android.synthetic.main.item_row_developer.view.*

class ListDeveloperAdapter(private val listDeveloper: ArrayList<Developer>, private val onItemClickCallback: OnItemClickCallback) : RecyclerView.Adapter<ListDeveloperAdapter.ListViewHolder>() {

    interface OnItemClickCallback {
        fun onItemClicked(developer: Developer)
    }

    inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val devName : TextView = itemView.findViewById(R.id.tv_item_row_developer_name)
        private val devLocation : TextView = itemView.findViewById(R.id.tv_item_row_developer_location)

        fun bind(developer: Developer) {
            Glide.with(itemView.context)
                .load(developer.avatar)
                .apply(RequestOptions().override(55, 55))
                .into(itemView.img_item_row_developer)

            this.devName.text = developer.name
            this.devLocation.text = developer.location

            this.itemView.setOnClickListener { Toast.makeText(itemView.context, "Selected " + developer.name, Toast.LENGTH_SHORT).show() }
            this.itemView.setOnClickListener { onItemClickCallback.onItemClicked(listDeveloper[this.adapterPosition]) }
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, position: Int): ListViewHolder {
        val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.item_row_developer, viewGroup, false)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bind(listDeveloper[position])
    }

    override fun getItemCount(): Int = listDeveloper.size
}