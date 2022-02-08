package com.xdevpro.trendingmoviesapp

import RetrofitService
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.xdevpro.trendingmoviesapp.adapter.ProductionComAdapter
import com.xdevpro.trendingmoviesapp.databinding.ActivityMovieDetailsBinding
import com.xdevpro.trendingmoviesapp.model.Production_companyModel
import com.xdevpro.trendingmoviesapp.repository.MainRepository
import com.xdevpro.trendingmoviesapp.viewmodel.DetailViewModel
import com.xdevpro.trendingmoviesapp.viewmodel.DetailViewModelFactory


class MovieDetails : AppCompatActivity() {

    var BASE_URL = "https://image.tmdb.org/t/p/" // Base url for image
    var ImageType = "original"  // Image type ( W500 / Original )

    private val TAG = "NASSER"
    private lateinit var binding: ActivityMovieDetailsBinding
    lateinit var viewModel: DetailViewModel
    private val retrofitService = RetrofitService.getInstance()
    private lateinit var ProductionAdapter: ProductionComAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMovieDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var movie_id : String = intent.getStringExtra("movie_id").toString()

        val recyclerView = binding.recyclerTrailer
        ProductionAdapter = ProductionComAdapter()
        val mLayoutManager = LinearLayoutManager(applicationContext)
        mLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
        recyclerView.layoutManager = mLayoutManager
        recyclerView.itemAnimator = DefaultItemAnimator()
        recyclerView.adapter = ProductionAdapter


        supportActionBar?.apply {
            title = "Movie detail"
            // show back button on toolbar
            // on back button press, it will navigate to parent activity
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
        }


        viewModel = ViewModelProvider(this, DetailViewModelFactory(MainRepository(retrofitService, movie_id))).get(DetailViewModel::class.java)


        //on success show the list of movies
        viewModel.movieDetail.observe(this, Observer {
            Log.d(TAG, "onCreate:" + it.homepage)


            binding.tvTitle.text = it.original_title
            binding.tvReleaseDate.text = it.release_date
            binding.tvVotesCount.text = it.vote_count.toString()
            binding.ratingBar.rating = 4.3F
            binding.tvPlot.text = it.overview
            Glide.with(baseContext).load(BASE_URL+ ImageType+it.poster_path).into(binding.imgPoster) // Using Glide Library for fetching Network Image

            var productionList = it.production_companies
            ProductionAdapter.setProductionList(productionList)

            binding.trailersLoading.visibility = View.GONE

        })

        // Show message error when unable to get list of movies
        viewModel.errorMessage.observe(this, Observer {
            Toast.makeText(baseContext, "Error : $it", Toast.LENGTH_LONG).show()
        })


        viewModel.getMovieDetail()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.getItemId()) {
            android.R.id.home -> {
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}