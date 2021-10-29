package com.example.tvshows.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.core.text.HtmlCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.example.tvshows.R
import com.example.tvshows.adapters.EpisodesAdapter
import com.example.tvshows.adapters.ImageSliderAdapter
import com.example.tvshows.databinding.ActivityTVShowDetailsBinding
import com.example.tvshows.databinding.LayoutEpisodesBottomSheetBinding
import com.example.tvshows.models.TVShow
import com.example.tvshows.viewModels.TVShowDetailsViewModel
import com.google.android.material.bottomsheet.BottomSheetDialog
import java.util.*

class TVShowDetailsActivity : AppCompatActivity() {

    private lateinit var viewModel: TVShowDetailsViewModel
    private lateinit var activityTVShowDetailsBinding: ActivityTVShowDetailsBinding
    private lateinit var tvShow: TVShow
    private var episodesBottomSheetDialog: BottomSheetDialog? = null
    private var layoutEpisodesBottomSheetBinding: LayoutEpisodesBottomSheetBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityTVShowDetailsBinding = DataBindingUtil.setContentView(this, R.layout.activity_t_v_show_details)
        tvShow = intent.getSerializableExtra("tvShow") as TVShow

        doInitialization()

    }

    private fun doInitialization() {
        viewModel = ViewModelProvider(this).get(TVShowDetailsViewModel::class.java)
        activityTVShowDetailsBinding.imageBack.setOnClickListener {
            onBackPressed()
        }
        getTVShowDetails()
    }

    private fun getTVShowDetails() {
        activityTVShowDetailsBinding.isLoading = true
        viewModel.getTVShowsDetails(tvShow.id.toString()).observe(this, { response ->
            activityTVShowDetailsBinding.isLoading = false

            if(response.tvShowDetails.pictures != null){
                loadImagesSlider(response.tvShowDetails.pictures)
            }
            activityTVShowDetailsBinding.tvShowImageUrl = response.tvShowDetails.imagePath
            activityTVShowDetailsBinding.imageTvShow.visibility = View.VISIBLE
            activityTVShowDetailsBinding.description = HtmlCompat.fromHtml(response.tvShowDetails.description, HtmlCompat.FROM_HTML_MODE_LEGACY) .toString()
            activityTVShowDetailsBinding.textDescription.visibility = View.VISIBLE
            activityTVShowDetailsBinding.textReadMore.visibility = View.VISIBLE
            activityTVShowDetailsBinding.textReadMore.setOnClickListener {
                if(activityTVShowDetailsBinding.textReadMore.text.toString() == "Read More"){
                    activityTVShowDetailsBinding.textDescription.maxLines = Int.MAX_VALUE
                    activityTVShowDetailsBinding.textDescription.ellipsize = null
                    activityTVShowDetailsBinding.textReadMore.text = getString(R.string.read_less)
                }else{
                    activityTVShowDetailsBinding.textDescription.maxLines = 4
                    activityTVShowDetailsBinding.textDescription.ellipsize = TextUtils.TruncateAt.END
                    activityTVShowDetailsBinding.textReadMore.text = getString(R.string.read_more)
                }
            }
            activityTVShowDetailsBinding.rating = String.format(Locale.getDefault(), "%.2f", response.tvShowDetails.rating.toDouble())
            if(response.tvShowDetails.genres.isNotEmpty()){
                activityTVShowDetailsBinding.genre = response.tvShowDetails.genres[0]
            }else{
                activityTVShowDetailsBinding.genre = "N/A"
            }
            activityTVShowDetailsBinding.runtime = response.tvShowDetails.runtime + " Min"
            activityTVShowDetailsBinding.viewDivider1.visibility = View.VISIBLE
            activityTVShowDetailsBinding.layoutMisc.visibility = View.VISIBLE
            activityTVShowDetailsBinding.viewDivider2.visibility = View.VISIBLE
            activityTVShowDetailsBinding.buttonEpisodes.visibility = View.VISIBLE
            activityTVShowDetailsBinding.buttonEpisodes.setOnClickListener {
                if (episodesBottomSheetDialog == null) {
                    episodesBottomSheetDialog = BottomSheetDialog(this@TVShowDetailsActivity)
                    layoutEpisodesBottomSheetBinding = DataBindingUtil.inflate(
                        LayoutInflater.from(this@TVShowDetailsActivity),
                        R.layout.layout_episodes_bottom_sheet,
                        findViewById(R.id.episodesContainer),
                        false
                    )
                    episodesBottomSheetDialog!!.setContentView(layoutEpisodesBottomSheetBinding!!.root)
                    layoutEpisodesBottomSheetBinding!!.episodesRecyclerView
                        .adapter = EpisodesAdapter(response.tvShowDetails.episodes)
                    layoutEpisodesBottomSheetBinding!!.textTitle
                        .text = "Episodes | ${tvShow.name}"
                    layoutEpisodesBottomSheetBinding!!.imageClose.setOnClickListener {
                        episodesBottomSheetDialog!!.dismiss()
                    }
                }
                // optional
//                val frameLayout = episodesBottomSheetDialog!!.findViewById<FrameLayout>(
//                    com.google.android.material.R.id.design_bottom_sheet
//                )
//                if (frameLayout != null) {
//                    val bottomSheetBehavior = BottomSheetBehavior.from(frameLayout)
//                    bottomSheetBehavior.peekHeight = Resources.getSystem().displayMetrics.heightPixels
//                    bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
//                }
                //

                episodesBottomSheetDialog!!.show()
            }

            loadBasicTVShowDetails()
        })
    }

    private fun loadImagesSlider(sliderImages: List<String>){
        activityTVShowDetailsBinding.sliderViewPager.offscreenPageLimit = 1
        activityTVShowDetailsBinding.sliderViewPager.adapter = ImageSliderAdapter(sliderImages)
        activityTVShowDetailsBinding.sliderViewPager.visibility = View.VISIBLE
        activityTVShowDetailsBinding.viewFadingEdge.visibility = View.VISIBLE
        setupSliderIndicator(sliderImages.size)
        activityTVShowDetailsBinding.sliderViewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                setCurrentSliderIndicator(position)
            }
        })
        setSliderMoveHandler(sliderImages.size-1)
    }

    private fun setupSliderIndicator(count: Int){
        val indicators = List(count) {ImageView(applicationContext) }
        val layoutParams = LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        layoutParams.setMargins(8, 0, 8, 0)
        for (i in indicators){
            i.setImageDrawable(ContextCompat.getDrawable(applicationContext, R.drawable.background_slider_indicator_inactive))
            i.layoutParams = layoutParams
            activityTVShowDetailsBinding.layoutSliderIndicator.addView(i)
        }
        activityTVShowDetailsBinding.layoutSliderIndicator.visibility = View.VISIBLE
        setCurrentSliderIndicator(0)
    }

    private fun loadBasicTVShowDetails(){
        activityTVShowDetailsBinding.tvShowName = tvShow.name
        activityTVShowDetailsBinding.networkCountry = "${tvShow.network} (${tvShow.country})"
        activityTVShowDetailsBinding.status = tvShow.status
        activityTVShowDetailsBinding.staredDate = tvShow.start_date
        activityTVShowDetailsBinding.textName.visibility = View.VISIBLE
        activityTVShowDetailsBinding.textNetworkCountry.visibility = View.VISIBLE
        activityTVShowDetailsBinding.textStatus.visibility = View.VISIBLE
        activityTVShowDetailsBinding.textStarted.visibility = View.VISIBLE
    }

    private fun setCurrentSliderIndicator(position: Int){
        val childCount = activityTVShowDetailsBinding.layoutSliderIndicator.childCount
        for (i in 0 until childCount){
            val imageView = activityTVShowDetailsBinding.layoutSliderIndicator.getChildAt(i) as ImageView
            if (i == position){
                imageView.setImageDrawable(ContextCompat.getDrawable(applicationContext, R.drawable.background_slider_indicator_active))
            }else{
                imageView.setImageDrawable(ContextCompat.getDrawable(applicationContext, R.drawable.background_slider_indicator_inactive))
            }
        }
    }

    private fun sliderMove(count: Int) {
        if (activityTVShowDetailsBinding.sliderViewPager.currentItem < count) {
            activityTVShowDetailsBinding.sliderViewPager.currentItem = activityTVShowDetailsBinding.sliderViewPager.currentItem + 1
        } else {
            activityTVShowDetailsBinding.sliderViewPager.currentItem = 0
        }
        setSliderMoveHandler(count)
    }

    private fun setSliderMoveHandler(count: Int) {
        Handler().postDelayed({ sliderMove(count) }, 4000)

    }



}