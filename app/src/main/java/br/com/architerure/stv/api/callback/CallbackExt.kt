package br.com.architerure.stv.api.callback

fun <T> apiCallback(
    onComplete: () -> Unit = {},
    onStart: () -> Unit = {},
    onError: (error: Throwable) -> Unit,
    onSucess: (response: T) -> Unit
): ApiCallBack<T> {
    return object : ApiCallBack<T> {
        override fun onComplete() {
            onComplete()
        }

        override fun onStart() {
            onStart()
        }

        override fun onError(error: Throwable) {
            onError(error)
        }

        override fun onSucess(response: T) {
            onSucess(response)
        }

    }
}