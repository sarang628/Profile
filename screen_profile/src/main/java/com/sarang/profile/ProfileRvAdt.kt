package com.sarang.profile

import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import com.example.torang_core.data.model.Feed
import com.sarang.base_feed.FeedVH
import com.sarang.base_feed.FeedVH.Companion.create

internal class ProfileRvAdt(
    private val profileViewModel: ProfileViewModel,
    private val feedLiveData : LiveData<List<Feed>>,
    private val lifecycleOwner: LifecycleOwner
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var feeds = ArrayList<Feed>()

    init {
        feedLiveData.observe(lifecycleOwner) {
            it?.also {
                this.feeds = ArrayList(it)
                notifyDataSetChanged()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return create(parent, profileViewModel, lifecycleOwner)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is FeedVH) {
            holder.setFeed(feeds[position]);
        }
    }

    override fun getItemCount(): Int {
        return feeds.size
    }
}