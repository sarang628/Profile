package com.sarang.profile

import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import com.sarang.base_feed.FeedVH
import com.sarang.base_feed.FeedVH.Companion.create
import com.example.torang_core.data.model.Feed
import kotlin.collections.ArrayList

internal class MyFeedRvAdt(
    private val profileViewModel: ProfileViewModel,
    private val fragmentManager: FragmentManager,
    private val lifecycleOwner: LifecycleOwner
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var feeds = ArrayList<Feed>()

    init {
        profileViewModel.myFeed.observe(lifecycleOwner) {
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