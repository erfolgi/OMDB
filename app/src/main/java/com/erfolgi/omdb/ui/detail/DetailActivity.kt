package com.erfolgi.omdb.ui.detail

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.erfolgi.omdb.databinding.ActivityDetailBinding
import com.erfolgi.omdb.databinding.ContentDetailBinding
import com.erfolgi.omdb.model.DetailResponse
import com.erfolgi.omdb.model.SearchItem
import com.erfolgi.omdb.util.AppPreference
import com.erfolgi.omdb.util.Util
import com.erfolgi.omdb.util.Util.bindIcon

class DetailActivity : AppCompatActivity(), DetailContract {
    private lateinit var binding: ActivityDetailBinding
    lateinit var preference: AppPreference
    var data: SearchItem = SearchItem()
    lateinit var presenter: DetailPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        preference=AppPreference(this)
        presenter = DetailPresenter(this)




    }
    override fun onResume() {
        super.onResume()
        var temp = preference.getStoredMovie()
        if(temp.imdbID!=null){
            this.data = temp

        }
        data.imdbID?.let { presenter.loadDetail(it) }
    }

    @SuppressLint("SetTextI18n")
//    fun bind(){
//        binding.apply {
//            movieDetailPoster.bindIcon(this@DetailActivity,data.poster)
//            content.apply {
//                movieDetailRating.rating = (data.imdbRating?:"0").toFloat()
//                movieDetailDate.text = data.released
//                movieDetailRuntime.text = data.runtime
//
//                movieDetailActors.text = data.actors
//                movieDetailDirector.text = data.director
//                movieDetailGenre.text = data.genre
//
//                movieDetailBackdrop.bindIcon(this@DetailActivity,data.poster)
//
//
//            }
//        }
//    }

    override fun onLoadDetail(data: DetailResponse) {
        Log.d("D",data.imdbID?:"_---")
        binding.apply {
            movieDetailPoster.bindIcon(this@DetailActivity,data.poster)
            content.apply {
                movieDetailTitle.text = data.title
                movieDetailRating.rating = (data.imdbRating?:"0").toFloat()
                movieDetailDate.text = data.released
                movieDetailRuntime.text = data.runtime

                movieDetailActors.text = data.actors
                movieDetailDirector.text = data.director
                movieDetailGenre.text = data.genre

                movieDetailBackdrop.bindIcon(this@DetailActivity,data.poster)


            }
        }
    }

    override fun onFailedMessage(msg: String) {
        Util.snackBar(this, msg)
    }
}