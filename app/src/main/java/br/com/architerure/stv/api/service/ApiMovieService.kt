package br.com.architerure.stv.api.service

import br.com.architerure.stv.api.domains.CastResponse
import br.com.architerure.stv.api.domains.GenreResponse
import br.com.architerure.stv.api.domains.MoviesResponse
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiMovieService {

    companion object {
        operator fun invoke(baseUrl: String): ApiMovieService {
            val baseService = ApiService(baseUrl)
            return baseService.retrofit.create(ApiMovieService::class.java)
        }
    }

    @GET("/3/movie/popular")
    fun popularMovies(
        @Query("page") page: String,
        @Query("language") language: String
    ): Observable<MoviesResponse>

    @GET("/3/movie/upcoming")
    fun upComingMovies(
        @Query("page") page: String,
        @Query("language") language: String
    ): Observable<MoviesResponse>

    @GET("/3/movie/{movie_id}/credits")
    fun castMovie(
        @Path("movie_id") moveId: Int
    ): Observable<CastResponse>

    @GET("/3/genre/movie/list")
    fun genres(): Observable<GenreResponse>

}