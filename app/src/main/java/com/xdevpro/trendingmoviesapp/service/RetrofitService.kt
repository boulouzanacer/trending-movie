import com.xdevpro.trendingmoviesapp.model.DetailMovieModel
import com.xdevpro.trendingmoviesapp.model.ResponseModel
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path


interface RetrofitService {

    @GET("3/discover/movie?api_key=c9856d0cb57c3f14bf75bdc6c063b8f3")
    fun getAllMovies() : Call<ResponseModel>

    @GET("3/movie/{movie_id}?api_key=c9856d0cb57c3f14bf75bdc6c063b8f3")
    fun getMovieDetail(@Path("movie_id") movie_id: String?): Call<DetailMovieModel>

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