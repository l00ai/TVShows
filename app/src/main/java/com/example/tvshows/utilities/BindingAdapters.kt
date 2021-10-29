package com.example.tvshows.utilities

import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target

@BindingAdapter("android:imageURL")
fun setImageURL(imageView: ImageView, URL: String?) {
    try {
        Glide.with(imageView.context).load(URL).listener(object : RequestListener<Drawable> {
            override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable>?, isFirstResource: Boolean): Boolean {
                return false
            }

            override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
               // imageView.animation = AnimationUtils.loadAnimation(imageView.context, R.anim.scale_animation)
                if (imageView.alpha == 0f){
                    imageView.animate().setDuration(300).alpha(1f).start()
                }
                return false
            }

        }).into(imageView)
    } catch (ignored: Exception) {

    }

}
