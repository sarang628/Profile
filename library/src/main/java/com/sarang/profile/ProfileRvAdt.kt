package com.sarang.profile

import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView

internal class ProfileRvAdt (
    private val profileViewModel: ProfileViewModel,
//    private val feedLiveData: LiveData<List<FeedEntity>>,
private val lifecycleOwner: LifecycleOwner
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
//    private var feeds = ArrayList<FeedEntity>()

//    init {
//        feedLiveData.observe(lifecycleOwner) {
//            it?.also {
//                this.feeds = ArrayList(it)
//                notifyDataSetChanged()
//            }
//        }
//    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ProfileViewHolder(TextView(parent.context))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        /*if (holder is FeedVH) {
            holder.setFeed(feeds[position]);
        }*/
    }

    override fun getItemCount(): Int {
//        return feeds.size
        return 0;
    }
}

class ProfileViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

}