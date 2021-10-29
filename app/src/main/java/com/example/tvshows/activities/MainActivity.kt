package com.example.tvshows.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.tvshows.R
import com.example.tvshows.adapters.TVShowsAdapter
import com.example.tvshows.databinding.ActivityMainBinding
import com.example.tvshows.listeners.TVShowListener
import com.example.tvshows.models.TVShow
import com.example.tvshows.viewModels.MostPopularTVShowsViewModel

class MainActivity : AppCompatActivity(), TVShowListener {

    private lateinit var viewModel: MostPopularTVShowsViewModel
    private lateinit var activityMainBinding: ActivityMainBinding
    private val list = ArrayList<TVShow>()
    private var tvShowsAdapter: TVShowsAdapter? = null
    private var currentPage = 1
    private var totalAvailablePage = 1


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        doInitialization()
    }

    private fun doInitialization(){
        viewModel = ViewModelProvider(this).get(MostPopularTVShowsViewModel::class.java)
        activityMainBinding.recyclerView.setHasFixedSize(true)
        tvShowsAdapter = TVShowsAdapter(list, this)
        activityMainBinding.recyclerView.adapter = tvShowsAdapter
        activityMainBinding.recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (!activityMainBinding.recyclerView.canScrollVertically(1)){
                    if (currentPage <= totalAvailablePage){
                        currentPage += 1
                        getMostPopularTVShows(currentPage)
                    }
                }
            }
        })
        getMostPopularTVShows(currentPage)


    }
    private fun getMostPopularTVShows(page: Int){
        toggleLoading()
        viewModel.getMostPopularTVShows(page).observe(this, {response->
            toggleLoading()
            if(response != null){
                totalAvailablePage = response.pages
                if (response.tv_shows != null){
                    val oldCount = list.size
                    list.addAll(response.tv_shows)
                    tvShowsAdapter!!.notifyItemRangeInserted(oldCount, list.size)
                }else{
                    Toast.makeText(applicationContext, "response.tv_shows is null", Toast.LENGTH_LONG).show()
                }
            }else{
                Toast.makeText(applicationContext, "response is null", Toast.LENGTH_LONG).show()

            }
        })
    }

    private fun toggleLoading(){
        if(currentPage == 1){
            activityMainBinding.isLoading = !activityMainBinding.isLoading
        }else{
            activityMainBinding.isLoadingMore = !activityMainBinding.isLoadingMore
        }
    }

    override fun onTVShowClicked(tvShow: TVShow, image:ImageView) {
        val intent = Intent(this@MainActivity, TVShowDetailsActivity::class.java)
        intent.putExtra("tvShow", tvShow)
//
//        val option =
//            ActivityOptions.makeSceneTransitionAnimation(this@MainActivity, image, "sharedImage")
//                .toBundle()
//
//        startActivity(intent, option)
        startActivity(intent)
    }
}