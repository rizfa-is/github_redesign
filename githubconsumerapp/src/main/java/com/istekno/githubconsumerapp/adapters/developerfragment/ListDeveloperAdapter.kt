package com.istekno.githubconsumerapp.adapters.developerfragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.istekno.githubconsumerapp.R
import com.istekno.githubconsumerapp.models.DeveloperDetail
import kotlinx.android.synthetic.main.item_row_developer.view.*

class ListDeveloperAdapter(private val onItemClickCallback: OnItemClickCallback) : RecyclerView.Adapter<ListDeveloperAdapter.ListViewHolder>() {

    private val dataDeveloperList = ArrayList<DeveloperDetail>()
    
    fun setData(items: ArrayList<DeveloperDetail>) {
        dataDeveloperList.clear()
        dataDeveloperList.addAll(items)
        notifyDataSetChanged()
    }

    interface OnItemClickCallback {
        fun onItemClicked(developerDetail: DeveloperDetail)
    }

    inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val devName : TextView = itemView.findViewById(R.id.tv_item_row_developer_name)

        fun bind(developerDetail: DeveloperDetail) {
            Glide.with(itemView.context)
                .load(developerDetail.avatar)
                .apply(RequestOptions().override(55, 55))
                .into(itemView.img_item_row_developer)

            this.devName.text = developerDetail.username

            this.itemView.setOnClickListener { Toast.makeText(itemView.context, "Selected " + developerDetail.username, Toast.LENGTH_SHORT).show() }
            this.itemView.setOnClickListener { onItemClickCallback.onItemClicked(dataDeveloperList[this.adapterPosition]) }
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, position: Int): ListViewHolder {
        val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.item_row_developer, viewGroup, false)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bind(dataDeveloperList[position])
    }

    override fun getItemCount(): Int = dataDeveloperList.size
}