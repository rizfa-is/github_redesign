package com.istekno.githubredesign.adapters.developerfragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.istekno.githubredesign.R
import com.istekno.githubredesign.models.DeveloperList
import kotlinx.android.synthetic.main.item_card_developer.view.*

class CardViewDeveloperAdapter(private val listDeveloperList: ArrayList<DeveloperList>, private val onItemClickCallback: OnItemClickCallback) : RecyclerView.Adapter<CardViewDeveloperAdapter.CardViewViewHolder>() {

    interface OnItemClickCallback {
        fun onItemClicked(developerList: DeveloperList, itemView: View)
    }

    inner class CardViewViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val devName : TextView = itemView.findViewById(R.id.tv_item_card_developer_name)
        private val btnShare : Button = itemView.findViewById(R.id.item_card_developer_btn_set_share)
        private val btnDetail : Button = itemView.findViewById(R.id.item_card_developer_btn_set_detail)

        fun bind(DeveloperList: DeveloperList) {
            Glide.with(itemView.context)
                .load(DeveloperList.avatar)
                .apply(RequestOptions().override(300, 450))
                .into(itemView.img_item_card_developer)

            this.devName.text = DeveloperList.username

            this.btnShare.setOnClickListener { onItemClickCallback.onItemClicked(listDeveloperList[this.adapterPosition], this.btnShare) }
            this.btnDetail.setOnClickListener { onItemClickCallback.onItemClicked(listDeveloperList[this.adapterPosition], this.btnDetail) }
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, position: Int): CardViewViewHolder {
        val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.item_card_developer, viewGroup, false)
        return CardViewViewHolder(view)
    }

    override fun onBindViewHolder(holder: CardViewViewHolder, position: Int) {
        holder.bind(listDeveloperList[position])
    }

    override fun getItemCount(): Int = listDeveloperList.size
}