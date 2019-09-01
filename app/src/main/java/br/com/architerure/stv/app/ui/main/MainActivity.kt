package br.com.architerure.stv.app.ui.main

import android.content.res.Configuration
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.architerure.stv.R
import br.com.architerure.stv.app.common.Alert
import br.com.architerure.stv.app.ui.activities.MoviesDatailsActivity
import br.com.architerure.stv.app.ui.adapters.MovieAdpter
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.startActivity
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class MainActivity : AppCompatActivity() {

    private var isLoading: Boolean = false

    private val moviesViewModel: MoviesViewModel by viewModel {
        parametersOf(this)
    }

    private val movieAdapter: MovieAdpter by lazy {
        MovieAdpter { movie ->
            startActivity<MoviesDatailsActivity>(
                "MoviesObject" to movie
            )
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        moviesViewModel
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        configScreen()
        configRecyclerView()
        diplayOrientationScreen()

        configUpcoming()
        configPopular()

        configError()
    }

    private fun configPopular() {
        button_popular.setOnClickListener {
            showProgress()
            moviesViewModel.upComingMovies()
            text_title.text = getString(R.string.main_title_popular_movie)
        }
    }

    private fun configUpcoming() {
        button_upcoming.setOnClickListener {
            showProgress()
            moviesViewModel.popularMovies()
            text_title.text = getString(R.string.main_title_upcoming_movie)
        }
    }

    private fun configRecyclerView() {

        recycle_view_movie.adapter = movieAdapter
        recycle_view_movie.itemAnimator = DefaultItemAnimator()

        moviesViewModel.allMovies.observe(this, Observer {
            it.let {
                hideProgress()
                movieAdapter.setMovies(it)
            }
        })
    }

    private fun configError() {
        moviesViewModel.errors.observe(this, Observer {
            if (!it.errors.isEmpty()) {
                hideProgress()
                this.Alert("Erros", it.toString()) {
                    moviesViewModel.errorsClear()
                }
            }
        })
    }

    private fun configScreen() {
        supportActionBar?.hide()
    }

    private fun diplayOrientationScreen() {
        val displayModel = resources.configuration.orientation

        val gridLayoutManager = GridLayoutManager(this, spanCount(displayModel))
        recycle_view_movie.layoutManager = gridLayoutManager

        if (displayModel == Configuration.ORIENTATION_PORTRAIT) {
            radioGroup.visibility = View.VISIBLE
        } else {
            radioGroup.visibility = View.GONE
            recycle_view_movie.layoutManager = gridLayoutManager
        }

        recycle_view_movie.addOnScrollListener(
            object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    val visibleItemCount = gridLayoutManager.childCount
                    val totalItemCount = gridLayoutManager.itemCount
                    val firstVisible = gridLayoutManager.findFirstVisibleItemPosition()
                    if (!isLoading && (visibleItemCount + firstVisible) >= totalItemCount) {
                        button_upcoming.performClick()
                    }
                }
            }
        )
    }

    private fun spanCount(displayModel: Int): Int =
        if (displayModel == Configuration.ORIENTATION_PORTRAIT) 3 else 5

    //region MainNavegation

    fun showProgress() {
        isLoading = true
//        recycle_view_movie.visibility = View.INVISIBLE
        frame_progress.visibility = View.VISIBLE
    }

    fun hideProgress() {
        isLoading = false
//        recycle_view_movie.visibility = View.VISIBLE
        frame_progress.visibility = View.GONE
    }
    //endregion


}
