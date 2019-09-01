package br.com.architerure.stv.api.callback

interface ApiCallBack<in T> {
    fun onComplete() {}
    fun onStart() {}
    fun onError(error: Throwable)
    fun onSucess(response: T)
}