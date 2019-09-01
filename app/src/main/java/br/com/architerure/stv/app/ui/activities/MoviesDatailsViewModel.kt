package br.com.architerure.stv.app.ui.activities

import androidx.lifecycle.MutableLiveData
import br.com.architerure.stv.api.callback.ApiCallBack
import br.com.architerure.stv.api.domains.*
import br.com.architerure.stv.api.repository.MoviesRepository
import br.com.architerure.stv.app.base.BaseViewModel

class MoviesDatailsViewModel(private val moviesRepository: MoviesRepository) :
    BaseViewModel<MoviesRepository>(moviesRepository) {

    val movie: MutableLiveData<Movie> = MutableLiveData()

    val allGenres: MutableLiveData<List<Genre>> = MutableLiveData()
    val allCast: MutableLiveData<List<Cast>> = MutableLiveData()

    fun genreList() {
        moviesRepository.genreList(
            object : ApiCallBack<GenreResponse> {
                override fun onError(error: Throwable) {
                    processError(error)
                }

                override fun onSucess(response: GenreResponse) {
                    val genres: MutableList<Genre> = mutableListOf()
                    response.genres.let {
                        movie.value?.genre_ids?.forEach { idGenre ->
                            it.firstOrNull {
                                it.id == idGenre
                            }?.let {
                                genres.add(it)
                            }
                        }
                    }
                    allGenres.value = genres
                }
            })
    }

    fun castList(moveId: Int) {
        moviesRepository.castMovie(
            moveId,
            object : ApiCallBack<CastResponse> {
                override fun onError(error: Throwable) {
                    processError(error)
                }

                override fun onSucess(response: CastResponse) {
                    allCast.value = response.cast
                }
            })
    }

    fun movieChosen(movie: Movie) {
        if (this.movie.value == null) {
            this.movie.value = movie
            castList(movie.id)
            genreList()
        }
    }
}