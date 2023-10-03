package com.erfolgi.omdb.ui.list

import com.erfolgi.omdb.model.SearchItem
import com.erfolgi.omdb.util.BaseContract

interface ListContract: BaseContract {
    fun onLoadList(data:ArrayList<SearchItem>)
}