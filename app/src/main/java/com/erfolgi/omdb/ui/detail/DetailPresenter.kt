package com.erfolgi.omdb.ui.detail

import android.util.Log
import com.erfolgi.omdb.model.DetailResponse
import com.erfolgi.omdb.util.BasePresenter
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailPresenter (private val contract: DetailContract): BasePresenter() {
    fun loadDetail(id:String) {
        Log.d("HELP","LOAD ID: ${id}")
        client.getDetail(id=id).enqueue(object:
            Callback<DetailResponse> {
            override fun onResponse(
                call: Call<DetailResponse>,
                response: Response<DetailResponse>
            ) {
                when(response.code()){
                    200 -> {
                        val body: DetailResponse? = response.body()
                        if (body?.response == "True") {
                            contract.onLoadDetail(body)
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

            override fun onFailure(call: Call<DetailResponse>, t: Throwable) {
                contract.onFailedMessage("Unknown Error: $t")
                Log.e("onFailure",t.toString())
            }
        })
    }
}