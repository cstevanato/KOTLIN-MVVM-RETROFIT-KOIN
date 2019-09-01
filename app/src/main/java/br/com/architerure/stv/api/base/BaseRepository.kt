package br.com.architerure.stv.api.base

import br.com.architerure.stv.api.callback.ApiCallBack
import br.com.architerure.stv.api.repository.Unsubscribe
import br.com.architerure.stv.api.service.ApiMovieService
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers

open class BaseRepository(
    @PublishedApi internal val service: ApiMovieService) :
    Unsubscribe {

    protected val  compositeDisposable = CompositeDisposable()

    override fun unSubscribe() {
        compositeDisposable.clear()
    }

    inline fun <reified T : Any> fetchData(
        function: Observable<T>,
        callBack: ApiCallBack<T>
    ) {

        compositeDisposable.add(function
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { callBack.onStart() }
            .subscribeWith(subscribe(callBack))
        )
    }

    fun <T> subscribe(callback: ApiCallBack<T>): DisposableObserver<T> {
        val observer = object : DisposableObserver<T>() {
            override fun onComplete() {
                callback.onComplete()
            }
            override fun onNext(t: T) {
                callback.onSucess(t)
            }
            override fun onError(e: Throwable) {
                callback.onError(e)
            }
        }
        return observer
    }

}