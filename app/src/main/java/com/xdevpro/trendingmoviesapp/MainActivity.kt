package com.xdevpro.trendingmoviesapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.xdevpro.trendingmoviesapp.adapter.MainAdapter
import com.xdevpro.trendingmoviesapp.databinding.ActivityMainBinding
import com.xdevpro.trendingmoviesapp.viewmodel.MainViewModel
import com.xdevpro.trendingmoviesapp.model.MovieModel
import com.xdevpro.trendingmoviesapp.viewmodel.MainViewModelFactory
import com.xdevpro.trendingmoviesapp.repository.MainRepository


class MainActivity : AppCompatActivity(), MainAdapter.ItemClickListener{

    private val TAG = "NASSER"
    private lateinit var binding: ActivityMainBinding
    lateinit var viewModel: MainViewModel
    private val retrofitService = RetrofitService.getInstance()
    val adapter = MainAdapter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this, MainViewModelFactory(MainRepository(retrofitService, ""))).get(
            MainViewModel::class.java)

        binding.recyclerview.adapter = adapter

        //on success show the list of movies
        viewModel.movieList.observe(this, Observer {
            Log.d(TAG, "onCreate: $it")
            adapter.setMovieList(it)
            binding.progressBarID.visibility = View.GONE
        })

        // Show message error when unable to get list of movies
        viewModel.errorMessage.observe(this, Observer {
            Toast.makeText(baseContext, "Error : $it", Toast.LENGTH_LONG).show()
        })

        viewModel.getAllMovies()
    }

    override fun onItemClick(position: Int, movie: MovieModel) {
      //  TODO("Not yet implemented")
        Log.v("NASSER", movie.id.toString())
        val intent = Intent(this, MovieDetails::class.java)
        intent.putExtra("movie_id", movie.id.toString())
        intent.putExtra("movie_name", movie.title)
        startActivity(intent)
        this.overridePendingTransition(R.anim.animation_enter,
            R.anim.animation_leave);
    }
}