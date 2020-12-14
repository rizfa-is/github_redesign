package com.istekno.githubredesign.adapters.developerfragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.istekno.githubredesign.R
import com.istekno.githubredesign.models.DeveloperList
import kotlinx.android.synthetic.main.item_grid_developer.view.*

class GridDeveloperAdapter(private val onItemClickCallback: OnItemClickCallback) : RecyclerView.Adapter<GridDeveloperAdapter.GridViewHolder>() {

    private val dataDeveloperList = ArrayList<DeveloperList>()

    fun setData(items: ArrayList<DeveloperList>) {
        dataDeveloperList.clear()
        dataDeveloperList.addAll(items)
        notifyDataSetChanged()
    }

    interface OnItemClickCallback {
        fun onItemClicked(developerList: DeveloperList)
    }

    inner class GridViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(DeveloperList: DeveloperList) {
            Glide.with(itemView.context)
                .load(DeveloperList.avatar)
                .apply(RequestOptions().override(250, 250))
                .into(itemView.img_item_grid_developer)

            this.itemView.tv_name_developer_grid.text = DeveloperList.username

            this.itemView.setOnClickListener { onItemClickCallback.onItemClicked(dataDeveloperList[this.adapterPosition]) }
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, position: Int): GridViewHolder {
        val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.item_grid_developer, viewGroup, false)
        return GridViewHolder(view)
    }

    override fun onBindViewHolder(holder: GridViewHolder, position: Int) {
        holder.bind(dataDeveloperList[position])
    }

    override fun getItemCount(): Int = dataDeveloperList.size
}