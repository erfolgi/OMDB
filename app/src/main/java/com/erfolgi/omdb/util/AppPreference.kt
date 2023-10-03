package com.erfolgi.omdb.util

import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import com.erfolgi.omdb.R
import com.erfolgi.omdb.model.DetailResponse
import com.erfolgi.omdb.model.SearchItem
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class AppPreference (val context: Context) {
    private var prefs: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)

    //region movie
    fun setStoredMovie(input: SearchItem) {
        val editornama = this.prefs.edit()
        val keynama = context.resources.getString(R.string.stat_movie)
        val gson = Gson()
        editornama.putString(keynama, gson.toJson(input))
        editornama.apply()
    }
    fun getStoredMovie(): SearchItem {
        val key = context.resources.getString(R.string.stat_movie)
        val gson = Gson()
        val json : String = prefs.getString(key, gson.toJson(SearchItem())).toString()
        val data = gson.fromJson<SearchItem>(json,SearchItem ::class.java)
        destroyStoredMovie()
        return data
    }
    fun  destroyStoredMovie() {
        val editornama = this.prefs.edit()
        val keynama = context.resources.getString(R.string.stat_movie)
        editornama.putString(keynama, null)
        editornama.apply()
    }
    //endregion

    //region saved
    fun setSavedMovie(input: DetailResponse) {
        var local = getSavedMovie();
        local.add(input);
        val editornama = this.prefs.edit()
        val keynama = context.resources.getString(R.string.saved_movie)
        val gson = Gson()
        editornama.putString(keynama, gson.toJson(local))
        editornama.apply()
    }

    fun removeSavedMovie(input: DetailResponse) {
        var local = getSavedMovie();
        local.remove(input);
        val editornama = this.prefs.edit()
        val keynama = context.resources.getString(R.string.saved_movie)
        val gson = Gson()
        editornama.putString(keynama, gson.toJson(input))
        editornama.apply()
    }

    fun savedById(id: String):DetailResponse {
        var idx = getSavedMovie().map{it.imdbID}.indexOf(id)
        return getSavedMovie()[idx];
    }
    fun savedIdxById(id: String):Int {
        var idx = getSavedMovie().map{it.imdbID}.indexOf(id)
        return idx;
    }

    fun savedToStored(input: DetailResponse) {
        val editornama = this.prefs.edit()
        val keynama = context.resources.getString(R.string.stat_movie)
        val gson = Gson()
        editornama.putString(keynama, gson.toJson(input))
        editornama.apply()
    }

    fun getSavedMovie(): ArrayList<DetailResponse> {
        val key = context.resources.getString(R.string.saved_movie)
        val gson = Gson()
        class Token : TypeToken<ArrayList<DetailResponse>>()
        val json : String = prefs.getString(key, "[]").toString()
        val type:java.lang.reflect.Type=Token().type
        val data = gson.fromJson<ArrayList<DetailResponse>>(json,type)
        return data
    }


    fun  destroySavedMovie() {
        val editornama = this.prefs.edit()
        val keynama = context.resources.getString(R.string.saved_movie)
        editornama.putString(keynama, null)
        editornama.apply()
    }
    //endregion
}