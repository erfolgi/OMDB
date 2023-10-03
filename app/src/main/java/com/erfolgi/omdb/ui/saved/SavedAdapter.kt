package com.erfolgi.omdb.ui.saved

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.erfolgi.omdb.R
import com.erfolgi.omdb.databinding.ItemMovieBinding
import com.erfolgi.omdb.model.DetailResponse
import com.erfolgi.omdb.ui.detail.DetailActivity
import com.erfolgi.omdb.util.AppPreference
import com.erfolgi.omdb.util.Util
import com.erfolgi.omdb.util.Util.bindIcon

class SavedAdapter(private val context: Context, var items: ArrayList<DetailResponse>)
    : RecyclerView.Adapter<SavedAdapter.CardHolder>(){
    lateinit var mInflater: LayoutInflater

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardHolder {
        mInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val itemRow = mInflater.inflate(R.layout.item_movie, parent, false)
        return CardHolder(itemRow)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: CardHolder, position: Int) {
        holder.bind(items[position],context)
    }

    class CardHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        private var binding: ItemMovieBinding = ItemMovieBinding.bind(itemView)
        fun bind(data: DetailResponse, context: Context){
            with(binding){
                imPhoto.bindIcon(context,data.poster)
                txTitle.text = data.title
                txYear.text = data.year
                ll.setOnClickListener {
                    AppPreference(context).savedToStored(data)
                    Util.intent(context, DetailActivity::class.java){

                        it
                    }
                }
            }
        }
    }
}