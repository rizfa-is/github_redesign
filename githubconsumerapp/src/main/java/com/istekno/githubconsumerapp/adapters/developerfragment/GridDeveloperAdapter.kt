package com.istekno.githubconsumerapp.adapters.developerfragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.istekno.githubconsumerapp.R
import com.istekno.githubconsumerapp.models.DeveloperDetail
import kotlinx.android.synthetic.main.item_grid_developer.view.*

class GridDeveloperAdapter(private val onItemClickCallback: OnItemClickCallback) : RecyclerView.Adapter<GridDeveloperAdapter.GridViewHolder>() {

    private val dataDeveloperList = ArrayList<DeveloperDetail>()

    fun setData(items: ArrayList<DeveloperDetail>) {
        dataDeveloperList.clear()
        dataDeveloperList.addAll(items)
        notifyDataSetChanged()
    }

    interface OnItemClickCallback {
        fun onItemClicked(developerDetail: DeveloperDetail)
    }

    inner class GridViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(developerDetail: DeveloperDetail) {
            Glide.with(itemView.context)
                .load(developerDetail.avatar)
                .apply(RequestOptions().override(250, 250))
                .into(itemView.img_item_grid_developer)

            this.itemView.tv_name_developer_grid.text = developerDetail.username

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