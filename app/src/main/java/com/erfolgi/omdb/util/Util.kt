package com.erfolgi.omdb.util

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.view.View
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.erfolgi.omdb.R
import com.faltenreich.skeletonlayout.Skeleton
import com.faltenreich.skeletonlayout.applySkeleton
import com.faltenreich.skeletonlayout.createSkeleton
import com.google.android.material.snackbar.Snackbar

object Util {
    /**
     * ImageView Binder
     */
    fun ImageView.bindImage(context: Context, url:String?) = run {
        Glide.with(context).load(url)
            .placeholder(R.drawable.ic_launcher_foreground).error(R.drawable.ic_launcher_foreground)
            .into(this)
        setOnClickListener {
            val i = Intent(context,ImageActivity::class.java)
            i.putExtra(ImageActivity.EXT_URL,url)
            context.startActivity(i)
        }
    }
    fun ImageView.bindImage(context: Context, r_drawable:Int?) = run {
        Glide.with(context).load(r_drawable)
            .placeholder(R.drawable.ic_launcher_foreground).error(R.drawable.ic_launcher_foreground)
            .into(this)

        setOnClickListener {
            val i = Intent(context,ImageActivity::class.java)
            i.putExtra(ImageActivity.EXT_URL,r_drawable)
            context.startActivity(i)
        }
    }

    fun ImageView.bindIcon(context: Context, url:String?) = run {
        Glide.with(context).load(url)
            .placeholder(R.drawable.ic_launcher_foreground).error(R.drawable.ic_launcher_foreground)
            .into(this)
    }
    fun ImageView.bindIcon(context: Context, r_drawable:Int?) = run {
        Glide.with(context).load(r_drawable)
            .placeholder(R.drawable.ic_launcher_foreground).error(R.drawable.ic_launcher_foreground)
            .into(this)
    }

    /**
     * shorter code
     */
    fun View.begone() = run{
        visibility = View.GONE
    }
    fun View.appear() = run{
        visibility = View.VISIBLE
    }
    fun intent(context: Context,cls:Class<*>, extra:(i:Intent)->Intent?){
        var i = Intent(context, cls)
        var x = extra(i)
        if(x!=null){
            i=x
        }
        context.startActivity(i)
    }

    fun snackBar(activity: Activity, message: String, isError: Boolean = true) {
        val parentLayout = activity.findViewById<View>(android.R.id.content)
        Snackbar.make(parentLayout, message, Snackbar.LENGTH_LONG)
            .setBackgroundTint(
                ContextCompat.getColor(activity, if (isError) R.color.accent_red
            else R.color.black))
            .show()
    }


    /**
     * Skeleton
     */
    fun skeletonSetup(skeleton: Skeleton): Skeleton {
        skeleton.shimmerDurationInMillis=500
        skeleton.shimmerColor= Color.parseColor("#F0F0F2")
        skeleton.maskColor= Color.parseColor("#C8C8C8")

        skeleton.maskCornerRadius=16.0F
        return skeleton
    }

    fun skeletonButton(view: View): Skeleton {
        var skeleton = view.createSkeleton()
        skeleton = skeletonSetup(skeleton)
        return skeleton
    }
    fun skeletonRV(view: RecyclerView, layoutId:Int): Skeleton {
        var skeleton = view.applySkeleton(layoutId)
        skeleton = skeletonSetup(skeleton)
        return skeleton
    }
    fun Skeleton.hideSkeleton() = run {
        if(isSkeleton()){
            showOriginal()
        }
    }

}