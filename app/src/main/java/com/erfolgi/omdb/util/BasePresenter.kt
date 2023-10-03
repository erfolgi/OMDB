package com.erfolgi.omdb.util

import com.erfolgi.omdb.network.ApiCall
import com.erfolgi.omdb.network.ApiClient

abstract class BasePresenter() {
    val client: ApiCall = ApiClient.getClient().create(ApiCall::class.java)
}
