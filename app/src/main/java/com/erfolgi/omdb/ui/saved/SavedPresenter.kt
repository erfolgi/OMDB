package com.erfolgi.omdb.ui.saved

import android.util.Log
import com.erfolgi.omdb.model.SearchResponse
import com.erfolgi.omdb.ui.list.ListContract
import com.erfolgi.omdb.util.AppPreference
import com.erfolgi.omdb.util.BasePresenter
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SavedPresenter (private val contract: SavedContract): BasePresenter() {
    fun loadLocal(preference:AppPreference) {

        contract.onLoadList(preference.getSavedMovie())
    }
}