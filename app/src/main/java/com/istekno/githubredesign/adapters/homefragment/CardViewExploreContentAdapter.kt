package com.istekno.githubredesign.adapters.homefragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.istekno.githubredesign.R
import com.istekno.githubredesign.models.Content
import kotlinx.android.synthetic.main.item_card_home_explore_content.view.*

class CardViewExploreContentAdapter(private val listContent : ArrayList<Content>, private val onItemClickCallback: OnItemClickCallback) : RecyclerView.Adapter<CardViewExploreContentAdapter.CardViewHolder>() {

    interface OnItemClickCallback {
        fun onItemClicked(content: Content)
    }

    inner class CardViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val checkMore : TextView = itemView.findViewById(R.id.explore_content_more)
        private val contentName : TextView = itemView.findViewById(R.id.explore_content_name)

        fun bind(content: Content) {
                Glide.with(itemView.context)
                    .load(content.photo)
                    .apply(RequestOptions().override(630, 360))
                    .into(itemView.explore_content_img)

                this.contentName.text = content.name
                this.checkMore.setOnClickListener { onItemClickCallback.onItemClicked(listContent[this.adapterPosition]) }
            }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, position: Int): CardViewHolder {
        val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.item_card_home_explore_content, viewGroup, false)
        return CardViewHolder(view)
    }

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        holder.bind(listContent[position])
    }

    override fun getItemCount(): Int = listContent.size
}