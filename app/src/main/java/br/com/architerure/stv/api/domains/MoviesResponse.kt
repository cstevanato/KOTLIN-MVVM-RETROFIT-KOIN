package br.com.architerure.stv.api.domains

import com.google.gson.annotations.SerializedName

data class MoviesResponse(
    @SerializedName("results")
    val movies: List<Movie>,
    val page: Int,
    val total_results: Int,
    val total_pages: Int
)