package br.com.architerure.stv.app.modules

import br.com.architerure.stv.BuildConfig
import br.com.architerure.stv.api.repository.MoviesRepository
import br.com.architerure.stv.api.service.ApiMovieService
import br.com.architerure.stv.app.ui.activities.MoviesDatailsViewModel
import br.com.architerure.stv.app.ui.main.MoviesViewModel
import io.reactivex.disposables.CompositeDisposable
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module

val appModule: Module = module {
    single { ApiMovieService(BuildConfig.SERVER_URL) }
    single { MoviesRepository(get()) }

    viewModel {
        MoviesViewModel(get())
    }

    viewModel {
        MoviesDatailsViewModel(get())
    }

}