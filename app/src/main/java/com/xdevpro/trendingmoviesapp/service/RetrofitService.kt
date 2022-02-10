import com.xdevpro.trendingmoviesapp.model.DetailMovieModel
import com.xdevpro.trendingmoviesapp.model.ResponseModel
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query


interface RetrofitService {

    @GET("3/discover/movie")
    fun getAllMovies(@Query("api_key")  key: String) : Call<ResponseModel>

    @GET("3/movie/{movie_id}")
    fun getMovieDetail(@Path("movie_id") movie_id: String, @Query("api_key")  key: String): Call<DetailMovieModel>

    companion object {

        var retrofitService: RetrofitService? = null

        fun getInstance() : RetrofitService {

            if (retrofitService == null) {
                val retrofit = Retrofit.Builder()
                    .baseUrl("https://api.themoviedb.org/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                retrofitService = retrofit.create(RetrofitService::class.java)
            }
            return retrofitService!!
        }
    }
}