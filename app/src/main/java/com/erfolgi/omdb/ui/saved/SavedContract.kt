package com.erfolgi.omdb.ui.saved

import com.erfolgi.omdb.model.DetailResponse
import com.erfolgi.omdb.model.SearchItem
import com.erfolgi.omdb.util.BaseContract

interface SavedContract : BaseContract {
    fun onLoadList(data:ArrayList<DetailResponse>)
}