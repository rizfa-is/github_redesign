package com.istekno.githubredesign.adapter.developerfragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.istekno.githubredesign.R
import com.istekno.githubredesign.data.Developer
import kotlinx.android.synthetic.main.item_card_developer.view.*

class CardViewDeveloperAdapter(private val listDeveloper: ArrayList<Developer>, private val onItemClickCallback: OnItemClickCallback) : RecyclerView.Adapter<CardViewDeveloperAdapter.CardViewViewHolder>() {

    interface OnItemClickCallback {
        fun onItemClicked(developer: Developer, itemView: View)
    }

    inner class CardViewViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val devName : TextView = itemView.findViewById(R.id.tv_item_card_developer_name)
        private val devLocation : TextView = itemView.findViewById(R.id.tv_item_card_developer_location)
        private val btnShare : Button = itemView.findViewById(R.id.item_card_developer_btn_set_share)
        private val btnDetail : Button = itemView.findViewById(R.id.item_card_developer_btn_set_detail)

        fun bind(developer: Developer) {
            Glide.with(itemView.context)
                .load(developer.avatar)
                .apply(RequestOptions().override(300, 450))
                .into(itemView.img_item_card_developer)

            this.devName.text = developer.name
            this.devLocation.text = developer.location

            this.btnShare.setOnClickListener { onItemClickCallback.onItemClicked(listDeveloper[this.adapterPosition], this.btnShare) }
            this.btnDetail.setOnClickListener { onItemClickCallback.onItemClicked(listDeveloper[this.adapterPosition], this.btnDetail) }
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, position: Int): CardViewViewHolder {
        val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.item_card_developer, viewGroup, false)
        return CardViewViewHolder(view)
    }

    override fun onBindViewHolder(holder: CardViewViewHolder, position: Int) {
        holder.bind(listDeveloper[position])
    }

    override fun getItemCount(): Int = listDeveloper.size
}