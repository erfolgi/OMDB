package com.erfolgi.omdb.util

import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import com.erfolgi.omdb.R
import com.erfolgi.omdb.model.SearchItem
import com.google.gson.Gson

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
}