package com.example.grocery_shop.adapter

import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.RecyclerView
import com.example.grocery_shop.R

class CustomAnimation : DefaultItemAnimator() {

    override fun animateRemove(holder: RecyclerView.ViewHolder?): Boolean {
        holder!!.itemView.animation =
            AnimationUtils.loadAnimation(holder.itemView.context, R.anim.anim_remove)
        return super.animateRemove(holder)
    }

    override fun animateAdd(holder: RecyclerView.ViewHolder?): Boolean {
        holder!!.itemView.animation =
            AnimationUtils.loadAnimation(holder.itemView.context, R.anim.anim_add)
        return super.animateAdd(holder)
    }

    override fun getRemoveDuration(): Long {
        return 500
    }

    override fun getAddDuration(): Long {
        return 500
    }
}