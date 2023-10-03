package com.erfolgi.omdb.ui.list

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.erfolgi.omdb.R
import com.erfolgi.omdb.databinding.ActivityListBinding
import com.erfolgi.omdb.model.SearchItem
import com.erfolgi.omdb.util.PaginationListener
import com.erfolgi.omdb.util.Util
import com.erfolgi.omdb.util.Util.appear
import com.erfolgi.omdb.util.Util.begone
import com.erfolgi.omdb.util.Util.bindIcon
import com.erfolgi.omdb.util.Util.hideSkeleton
import com.faltenreich.skeletonlayout.Skeleton

class ListActivity : AppCompatActivity(),ListContract {
    lateinit var binding: ActivityListBinding
    lateinit var presenter: ListPresenter
    lateinit var skeleton: Skeleton
    lateinit var adapter:ListAdapter

    var lastPage=false
    var loading=false
    var page:Int? =1
    var search:String? ="Avenger"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.toolbar.tbBack.setOnClickListener { finish() }

        presenter = ListPresenter(this)

        skeleton = Util.skeletonRV(binding.rv,R.layout.item_movie)

        val manager = LinearLayoutManager(this)
        binding.rv.layoutManager=manager
        binding.rv.addOnScrollListener(object : PaginationListener(manager){
            override fun loadMoreItems() {
                if(!lastPage && !loading){
                    loadList()
                }
            }

            override val isLastPage: Boolean
                get() = lastPage
            override val isLoading: Boolean
                get() = loading
        })
        binding.cardFeatured.begone()

        binding.toolbar.tbSearch.setOnQueryTextListener(object: SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                this@ListActivity.search=query
                initLoad()
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                this@ListActivity.search=newText
                initLoad()
                return false
            }

        })
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
        page=1
        lastPage=false
        loadList()
    }
    fun loadList(){
        loading=true
        presenter.loadList(page = page, search = search)
    }

    override fun onLoadList(data: ArrayList<SearchItem>) {
        skeleton.hideSkeleton()
        loading=false
        if(data.isNotEmpty()){
            binding.rv.visibility =View.VISIBLE
//            binding.llEmpty.visibility =View.GONE

            if(page!=1){
                val temp = adapter.items.size
                adapter.items.addAll(data)
                adapter.items= adapter.items.distinct() as ArrayList<SearchItem>
                adapter.notifyItemRangeInserted(temp, data.size)
            }else{
                try {
                    setFeatured(data[0])
                }catch (_:Exception){}
                try {
                    var dataX=data
                    dataX.removeAt(0)
                    adapter= ListAdapter(this, dataX)
                    binding.rv.adapter=adapter
                }catch (_:Exception){}

            }
            page = page!! + 1;
        }else{
            lastPage=true
            if(page==1){
                binding.rv.visibility=View.GONE
//                binding.llEmpty.visibility=View.VISIBLE
            }

        }
    }

    override fun onFailedMessage(msg: String) {
        skeleton.hideSkeleton()
        Util.snackBar(this, msg)
    }

    fun setFeatured(data:SearchItem){
        with(binding){
            cardFeatured.appear()
            txFeatuedTitle.text = data.title
            txFeatuedDate.text = data.year
            imFeaturedPhoto.bindIcon(this@ListActivity,data.poster)

//            cardFeatured.setOnClickListener {
//                AppPreference(this@NewsActivity).setStoredNews(data)
//                Util.intent(this@NewsActivity, DetailNewsActivity::class.java){it}
//            }
        }
    }
}