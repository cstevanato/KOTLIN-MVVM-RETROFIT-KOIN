package br.com.architerure.stv.app.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import br.com.architerure.stv.api.callback.ApiCallBack
import br.com.architerure.stv.api.domains.Movie
import br.com.architerure.stv.api.domains.MoviesResponse
import br.com.architerure.stv.api.repository.MoviesRepository
import br.com.architerure.stv.app.base.BaseViewModel
import br.com.architerure.stv.app.ui.main.domains.MoviesTotalsPage

class MoviesViewModel(
    private val moviesRepository: MoviesRepository
) : BaseViewModel<MoviesRepository>(moviesRepository) {

    private var page: Int = 0

    private var _allMovies: MutableLiveData<MutableList<Movie>> = MutableLiveData()
    val allMovies: LiveData<MutableList<Movie>> = _allMovies

    private var populaMoviesChosen = true
    private var moviesTotalsPage: MoviesTotalsPage? = null

    init {
        popularMovies()
    }

    //region exposed

    fun popularMovies() {
        popularMoviesBegingPage()
        if (!isNextPage()) {
            processError(Throwable("Paginas Error"))
            return
        }

        moviesRepository.loadPopularMovies(page.toString(), "en-US",
            object : ApiCallBack<MoviesResponse> {
                override fun onError(error: Throwable) {
                    processError(error)
                }

                override fun onSucess(response: MoviesResponse) {
                    moviesTotalsPage = MoviesTotalsPage(
                        response.page,
                        response.total_results,
                        response.total_pages
                    )
                    if (_allMovies.value == null)
                        _allMovies.value = response.movies.toMutableList()
                    else {
                        val list = mutableListOf<Movie>()
                        list.addAll(_allMovies.value!!)
                        list.addAll(response.movies.toMutableList())
                        _allMovies.value = list
                    }
                }
            })
    }

    fun upComingMovies() {
        upComingMoviesBeginPage()
        if (!isNextPage()) {
            processError(Throwable("Paginas Error"))
            return
        }

        moviesRepository.loadUpComingMovies(page.toString(), "en-US",
            object : ApiCallBack<MoviesResponse> {
                override fun onError(error: Throwable) {
                    processError(error)
                }

                override fun onSucess(response: MoviesResponse) {
                    moviesTotalsPage = MoviesTotalsPage(
                        response.page,
                        response.total_results,
                        response.total_pages
                    )
                    if (_allMovies.value == null)
                        _allMovies.value = response.movies.toMutableList()
                    else {
                        val list = mutableListOf<Movie>()
                        list.addAll(_allMovies.value!!)
                        list.addAll(response.movies.toMutableList())
                        _allMovies.value = list
                    }
                }
            })
    }

    //endregion

    //region Private

    private fun upComingMoviesBeginPage() {
        if (populaMoviesChosen) {
            populaMoviesChosen = false
            page = 0
        }
    }

    private fun isNextPage(): Boolean {
        moviesTotalsPage?.let {
            if (it.total_pages >= page) {
                page++
                return true
            }
        } ?: run {
            page++
            return true
        }
        return false
    }

    private fun popularMoviesBegingPage() {
        if (!populaMoviesChosen) {
            populaMoviesChosen = true
            page = 0
        }
    }

    //endregion
}