package com.erfolgi.omdb.util

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.erfolgi.omdb.R
import com.jsibbold.zoomage.ZoomageView

class ImageActivity : AppCompatActivity() {
    companion object{
        var EXT_URL = "EXT_URL"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image)

        val iv = findViewById<ZoomageView>(R.id.zoomimage)
        Glide.with(this).load(intent.getStringExtra(EXT_URL)).into(iv)
    }
}