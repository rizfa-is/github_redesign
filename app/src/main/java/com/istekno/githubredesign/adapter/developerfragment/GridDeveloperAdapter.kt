package com.istekno.githubredesign.adapter.developerfragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.istekno.githubredesign.R
import com.istekno.githubredesign.data.Developer
import kotlinx.android.synthetic.main.item_grid_developer.view.*

class GridDeveloperAdapter(private val listDeveloper: ArrayList<Developer>, private val onItemClickCallback: OnItemClickCallback) : RecyclerView.Adapter<GridDeveloperAdapter.GridViewHolder>() {

    interface OnItemClickCallback {
        fun onItemClicked(developer: Developer)
    }

    inner class GridViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(developer: Developer) {
            Glide.with(itemView.context)
                .load(developer.avatar)
                .apply(RequestOptions().override(250, 250))
                .into(itemView.img_item_grid_developer)

            this.itemView.tv_name_developer_grid.text = developer.username

            this.itemView.setOnClickListener { onItemClickCallback.onItemClicked(listDeveloper[this.adapterPosition]) }
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, position: Int): GridViewHolder {
        val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.item_grid_developer, viewGroup, false)
        return GridViewHolder(view)
    }

    override fun onBindViewHolder(holder: GridViewHolder, position: Int) {
        holder.bind(listDeveloper[position])
    }

    override fun getItemCount(): Int = listDeveloper.size
}