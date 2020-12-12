package com.istekno.githubredesign.adapter.homefragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.istekno.githubredesign.R
import com.istekno.githubredesign.model.DeveloperList
import kotlinx.android.synthetic.main.item_card_home_most_popular.view.*

class CardViewMostPopularAdapter(private val listMostPopularDeveloperList: ArrayList<DeveloperList>, private val onItemClickCallback: OnItemClickCallback) : RecyclerView.Adapter<CardViewMostPopularAdapter.CardViewHolder>() {

    interface OnItemClickCallback {
        fun onItemClicked(developerList: DeveloperList)
    }

    inner class CardViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(DeveloperList: DeveloperList) {
            Glide.with(itemView.context)
                .load(DeveloperList.avatar)
                .apply(RequestOptions().override(140, 140))
                .into(itemView.img_cardView_photo)

            this.itemView.name_most_popular.text = DeveloperList.username

            val mpUp = this.itemView.mp_background_up
            val medal = this.itemView.medal_most_popular
            when (this.adapterPosition) {
                0 -> {
                    mpUp.setImageResource(R.color.colorGold)
                    medal.setImageResource(R.drawable.ic_medal_gold)
                }

                1 -> {
                    mpUp.setImageResource(R.color.colorSilver)
                    medal.setImageResource(R.drawable.ic_medal_silver)
                }

                2 -> {
                    mpUp.setImageResource(R.color.colorBronze)
                    medal.setImageResource(R.drawable.ic_medal_bronze)
                }
            }

            this.itemView.setOnClickListener { onItemClickCallback.onItemClicked(listMostPopularDeveloperList[this.adapterPosition]) }
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, position: Int): CardViewHolder {
        val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.item_card_home_most_popular, viewGroup, false)
        return CardViewHolder(view)
    }

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        holder.bind(listMostPopularDeveloperList[position])
    }

    override fun getItemCount(): Int = listMostPopularDeveloperList.size
}