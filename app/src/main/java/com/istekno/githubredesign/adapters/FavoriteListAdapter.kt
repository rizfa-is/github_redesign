package com.istekno.githubredesign.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.istekno.githubredesign.R
import com.istekno.githubredesign.databinding.ItemRowDeveloperBinding
import com.istekno.githubredesign.entity.Favorite
import com.istekno.githubredesign.helpers.CustomOnItemClickListener
import com.istekno.githubredesign.views.activity.DeveloperDetailActivity
import com.istekno.githubredesign.views.fragments.DeveloperFragment

class FavoriteListAdapter: RecyclerView.Adapter<FavoriteListAdapter.FavoriteViewHolder>() {

    var listFavorite = ArrayList<Favorite>()
        set(value) {
            if (value.size > 0) {
                this.listFavorite.clear()
            }
            this.listFavorite.addAll(value)
            notifyDataSetChanged()
        }

    inner class FavoriteViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        private val binding = ItemRowDeveloperBinding.bind(itemView)
        private lateinit var customOnItemClickListener: CustomOnItemClickListener

        fun bind(favorite: Favorite) {
            customOnItemClickListener = CustomOnItemClickListener(adapterPosition)

            Glide.with(itemView.context).load(favorite.avatar).into(binding.imgItemRowDeveloper)
            binding.tvItemRowDeveloperName.text = favorite.username

            this.itemView.setOnClickListener {
                customOnItemClickListener.onItemClickCallback(object : CustomOnItemClickListener.OnItemClickCallback {
                    override fun onItemClicked(view: View, position: Int) {
                        val intent = Intent(itemView.context, DeveloperDetailActivity::class.java)
                        intent.putExtra(DeveloperFragment.FAV_INTENT_PARCELABLE, favorite)
                        itemView.context.startActivity(intent)
                    }
                })
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        return FavoriteViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_row_developer, parent, false))
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) = holder.bind(listFavorite[position])

    override fun getItemCount(): Int = listFavorite.size
}