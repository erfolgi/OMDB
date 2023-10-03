package com.erfolgi.omdb.ui.detail

import com.erfolgi.omdb.model.DetailResponse
import com.erfolgi.omdb.util.BaseContract

interface DetailContract :BaseContract {
    fun onLoadDetail(data:DetailResponse)
}