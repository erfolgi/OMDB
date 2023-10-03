package com.erfolgi.omdb.ui.list

import android.util.Log
import com.erfolgi.omdb.model.SearchResponse
import com.erfolgi.omdb.network.ApiClient
import com.erfolgi.omdb.util.BasePresenter
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class ListPresenter(private val contract: ListContract):BasePresenter() {
    fun loadList(page:Int?, search:String?) {
        client.getSearch(s=search,page=page).enqueue(object:
            Callback<SearchResponse> {
            override fun onResponse(
                call: Call<SearchResponse>,
                response: Response<SearchResponse>
            ) {
                when(response.code()){
                    200 -> {
                        val body: SearchResponse? = response.body()
                        if (body?.search != null) {
                            contract.onLoadList(body.search)
                        }else{
                            body?.error?.let { contract.onFailedMessage(it) }
                        }
                    }
                    else->{
                        try{
                            val jObjError =
                                JSONObject(response.errorBody()!!.string())
                            contract.onFailedMessage(jObjError.getString("message"))
                        }catch(e:Exception){
                            contract.onFailedMessage(response.message())
                        }

                    }
                }
            }

            override fun onFailure(call: Call<SearchResponse>, t: Throwable) {
                contract.onFailedMessage("Unknown Error: $t")
                Log.e("onFailure",t.toString())
            }
        })
    }
}