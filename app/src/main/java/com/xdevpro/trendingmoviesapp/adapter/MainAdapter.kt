package com.xdevpro.trendingmoviesapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.xdevpro.trendingmoviesapp.databinding.AdapterMovieBinding
import com.xdevpro.trendingmoviesapp.model.MovieModel

class MainAdapter constructor(val mItemClickListener:ItemClickListener): RecyclerView.Adapter<MainViewHolder>() {

    var movies = mutableListOf<MovieModel>()
    var BASE_URL = "https://image.tmdb.org/t/p/" // Base url for image
    var ImageType = "w500"  // Image type ( W500 / Original )


    //Interface of handling on click movie item.
    interface ItemClickListener{
        fun onItemClick(position: Int, movie: MovieModel)
    }

    fun setMovieList(movies: List<MovieModel>) {
        this.movies = movies.toMutableList()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        val binding = AdapterMovieBinding.inflate(inflater, parent, false)
        return MainViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        val movie = movies[position]
        holder.binding.title.text = movie.title
        holder.binding.rating.text = "Vote average : " + movie.vote_average.toString()
        holder.binding.originalLanguage.text = "Original language : "+ movie.original_language
        holder.binding.releaseYear.text = movie.release_date
        Glide.with(holder.itemView.context).load(BASE_URL+ ImageType+movie.poster_path).into(holder.binding.thumbnail) // Using Glide Library for fetching Network Image

        // on click movie item listener
        holder.binding.movieItem.setOnClickListener{
            mItemClickListener.onItemClick(position, movies[position])
        }
    }

    override fun getItemCount(): Int {
        return movies.size
    }
}

class MainViewHolder(val binding: AdapterMovieBinding) : RecyclerView.ViewHolder(binding.root) {

}