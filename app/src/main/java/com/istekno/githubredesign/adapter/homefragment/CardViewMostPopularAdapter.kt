package com.istekno.githubredesign.adapter.homefragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.istekno.githubredesign.R
import com.istekno.githubredesign.data.Developer
import kotlinx.android.synthetic.main.item_card_home_most_popular.view.*

class CardViewMostPopularAdapter(private val listMostPopularDeveloper: ArrayList<Developer>, private val onItemClickCallback: OnItemClickCallback) : RecyclerView.Adapter<CardViewMostPopularAdapter.CardViewHolder>() {

    interface OnItemClickCallback {
        fun onItemClicked(developer: Developer)
    }

    inner class CardViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(developer: Developer) {
            Glide.with(itemView.context)
                .load(developer.avatar)
                .apply(RequestOptions().override(140, 140))
                .into(itemView.img_cardView_photo)

            this.itemView.name_most_popular.text = developer.username

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

            this.itemView.setOnClickListener { onItemClickCallback.onItemClicked(listMostPopularDeveloper[this.adapterPosition]) }
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, position: Int): CardViewHolder {
        val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.item_card_home_most_popular, viewGroup, false)
        return CardViewHolder(view)
    }

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        holder.bind(listMostPopularDeveloper[position])
    }

    override fun getItemCount(): Int = listMostPopularDeveloper.size
}