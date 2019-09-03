package br.com.architerure.stv.api.repository

import br.com.architerure.stv.api.base.BaseRepository
import br.com.architerure.stv.api.callback.ApiCallBack
import br.com.architerure.stv.api.domains.CastResponse
import br.com.architerure.stv.api.domains.GenreResponse
import br.com.architerure.stv.api.domains.MoviesResponse
import br.com.architerure.stv.api.service.ApiMovieService
import io.reactivex.disposables.CompositeDisposable

class MoviesRepository(service: ApiMovieService, compositeDisposable: CompositeDisposable)
    : BaseRepository(service, compositeDisposable) {

    fun loadPopularMovies(
        page: String, language: String,
        callback: ApiCallBack<MoviesResponse>
    ) {
        fetchData(service.popularMovies(page, language), callback)
    }

    fun loadUpComingMovies(
        page: String, language: String,
        callback: ApiCallBack<MoviesResponse>
    ) {
        fetchData(service.upComingMovies(page, language), callback)
    }

    fun genreList(callback: ApiCallBack<GenreResponse>) {
        fetchData(service.genres(), callback)
    }

    fun castMovie(
        moveId: Int,
        callback: ApiCallBack<CastResponse>
    ) {
        fetchData(service.castMovie(moveId), callback)
    }
}

