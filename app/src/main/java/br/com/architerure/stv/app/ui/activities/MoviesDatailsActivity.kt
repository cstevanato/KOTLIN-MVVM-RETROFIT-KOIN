package br.com.architerure.stv.app.ui.activities

import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import br.com.architerure.stv.R
import br.com.architerure.stv.api.domains.Movie
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_movies_datails.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class MoviesDatailsActivity : AppCompatActivity() {

    private val moviesDatailsViewModel: MoviesDatailsViewModel by viewModel()

    private val movie: Movie?
        get() = intent.getSerializableExtra("MoviesObject") as Movie

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.activity_movies_datails)

        movie?.let {
            moviesDatailsViewModel.movieChosen(it)
        }

        moviesDatailsViewModel.allGenres.observe(this, Observer {
            text_detail_description.text = it.map { it.name }.toString()
        })

        moviesDatailsViewModel.allCast.observe(this, Observer {
            if (it.isNotEmpty())
                Picasso.get()
                    .load(Uri.parse("http://image.tmdb.org/t/p/w500${it[0].profile_path}"))
                    .into(imageViewCast01)
            if (it.size > 1)
                Picasso.get()
                    .load(Uri.parse("http://image.tmdb.org/t/p/w500${it[1].profile_path}"))
                    .into(imageViewCast02)
            if (it.size > 2)
                Picasso.get()
                    .load(Uri.parse("http://image.tmdb.org/t/p/w500${it[2].profile_path}"))
                    .into(imageViewCast03)
        })

        moviesDatailsViewModel.movie.observe(this, Observer {

            text_detail_title.text = it.title
            text_detail_comment.text = it.overview
            text_detail_year.text = it.release_date.substring(0, 4)

            Picasso.get()
                .load(Uri.parse("http://image.tmdb.org/t/p/w500${it.backdrop_path}"))
                .into(imageViewPoster)
        })
    }
}
