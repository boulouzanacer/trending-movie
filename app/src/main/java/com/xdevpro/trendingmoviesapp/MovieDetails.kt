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

        val movie_id : String = intent.getStringExtra("movie_id").toString()
        val movie_name : String = intent.getStringExtra("movie_name").toString()

        val recyclerView = binding.recyclerTrailer
        ProductionAdapter = ProductionComAdapter()
        val mLayoutManager = LinearLayoutManager(applicationContext)
        mLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
        recyclerView.layoutManager = mLayoutManager
        recyclerView.itemAnimator = DefaultItemAnimator()
        recyclerView.adapter = ProductionAdapter


        supportActionBar?.apply {
            title = movie_name
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
            binding.tvReleaseDate.text = resources.getString(R.string.released_in).plus(it.release_date)
            binding.tvPopularity.text = resources.getString(R.string.popularity).plus(" "+ it.popularity)
            binding.tvBudget.text = resources.getString(R.string.budget).plus(" " +it.budget.toString()+ " ").plus(resources.getString(R.string.dollar))
            binding.tvRating.text = it.vote_average.toString()
            binding.ratingBar.rating = (it.vote_average.toFloat() * 5) / 10
            //--------------------
            var geners = ""
            var i = 0
            while (it.genres.size > i){
                geners = geners.plus( " "+it.genres[i].name + " /")
                i++
            }
            binding.tvMovieGenres.text = resources.getString(R.string.generes).plus(geners)
            //---------------------
            var prod_countries = ""
            i = 0
            while (it.production_countries.size > i){
                prod_countries = prod_countries.plus( " "+it.production_countries[i].name + " /")
                i++
            }
            binding.tvProductionCount.text = resources.getString(R.string.production_countries).plus(prod_countries)
            //---------------------
            var spoken_languages = ""
            i = 0
            while (it.spoken_languages.size > i){
                spoken_languages = spoken_languages.plus( " "+it.spoken_languages[i].english_name + " /")
                i++
            }
            binding.tvSpokenLang.text = resources.getString(R.string.spoken_languages).plus(spoken_languages)
            //---------------------

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