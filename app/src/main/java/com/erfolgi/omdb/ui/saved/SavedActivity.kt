package com.erfolgi.omdb.ui.saved

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.erfolgi.omdb.R
import com.erfolgi.omdb.databinding.ActivityListBinding
import com.erfolgi.omdb.databinding.ActivitySavedBinding
import com.erfolgi.omdb.model.DetailResponse
import com.erfolgi.omdb.model.SearchItem
import com.erfolgi.omdb.ui.list.ListAdapter
import com.erfolgi.omdb.ui.list.ListPresenter
import com.erfolgi.omdb.util.AppPreference
import com.erfolgi.omdb.util.PaginationListener
import com.erfolgi.omdb.util.Util
import com.erfolgi.omdb.util.Util.begone
import com.erfolgi.omdb.util.Util.hideSkeleton
import com.faltenreich.skeletonlayout.Skeleton

class SavedActivity : AppCompatActivity(),SavedContract {
    lateinit var binding: ActivitySavedBinding
    lateinit var presenter: SavedPresenter
    lateinit var appPreference: AppPreference
    lateinit var skeleton: Skeleton
    lateinit var adapter: SavedAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySavedBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.toolbar.tbBack.setOnClickListener { finish() }
        appPreference=AppPreference(this)
        presenter = SavedPresenter(this)

        skeleton = Util.skeletonRV(binding.rv,R.layout.item_movie)

        val manager = LinearLayoutManager(this)
        binding.rv.layoutManager=manager
//
//        binding.toolbar.tbSearch.setOnQueryTextListener(object: SearchView.OnQueryTextListener {
//            override fun onQueryTextSubmit(query: String?): Boolean {
//                this@SavedActivity.search=query
//                initLoad()
//                return true
//            }
//
//            override fun onQueryTextChange(newText: String?): Boolean {
//                this@ListActivity.search=newText
//                initLoad()
//                return false
//            }
//
//        })
        binding.sw.setOnRefreshListener {
            binding.sw.isRefreshing=false
            initLoad()
        }
    }
    override fun onResume() {
        super.onResume()
        initLoad()
    }
    fun initLoad(){
        //binding.llEmpty.visibility= View.GONE
        binding.rv.visibility= View.VISIBLE
        skeleton.showSkeleton()
        loadList()
    }
    fun loadList(){
        presenter.loadLocal(appPreference)
    }

    override fun onLoadList(data: ArrayList<DetailResponse>) {
        skeleton.hideSkeleton()
        if(data.isNotEmpty()){
            binding.rv.visibility = View.VISIBLE
            adapter= SavedAdapter(this, data)
            binding.rv.adapter=adapter
        }else{
                binding.rv.visibility= View.GONE
            }

        }

    override fun onFailedMessage(msg: String) {
        Util.snackBar(this, msg)
    }
}