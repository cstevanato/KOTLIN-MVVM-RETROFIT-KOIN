package br.com.architerure.stv.app.ui.main

import android.util.Log
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import br.com.architerure.stv.BuildConfig
import br.com.architerure.stv.RxSchedulerRule
import br.com.architerure.stv.api.domains.Movie
import br.com.architerure.stv.api.repository.MoviesRepository
import br.com.architerure.stv.api.service.ApiMovieService
import br.com.architerure.stv.testObserver
import com.google.common.truth.Truth
import com.nhaarman.mockitokotlin2.times
import io.reactivex.disposables.CompositeDisposable
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.*
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoRule


//junit:junit:4.12
@RunWith(JUnit4::class)
class MoviesViewModelTest {

    // androidx.arch.core:core-testing:2.1.0
    @get:Rule
    val mockitoRule: MockitoRule = MockitoJUnit.rule()

    @get:Rule
    val taskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val rxSchedulerRule = RxSchedulerRule()


    @Mock
    lateinit var service: ApiMovieService
    lateinit var compositeDisposable: CompositeDisposable
    lateinit var moviesRepository: MoviesRepository
    lateinit var moviesViewModel: MoviesViewModel

    @Captor
    lateinit var captor: ArgumentCaptor<MutableList<Movie>>

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        service = ApiMovieService(BuildConfig.SERVER_URL)
        compositeDisposable = CompositeDisposable()
        moviesRepository = MoviesRepository(service, compositeDisposable)
        moviesViewModel = MoviesViewModel(moviesRepository)
    }


    @Test
    fun getAllMovies() {
        val liveDataUnderTest =  moviesViewModel.allMovies.testObserver()
        //moviesViewModel.popularMovies()
        Truth.assert_()
            .that(liveDataUnderTest.observedValues)
            .isNotEmpty()

    }
}