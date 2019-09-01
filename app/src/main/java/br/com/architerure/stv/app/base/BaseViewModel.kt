package br.com.architerure.stv.app.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.architerure.stv.api.domains.Errors
import br.com.architerure.stv.api.repository.Unsubscribe

open class BaseViewModel<T : Unsubscribe>(private val clientRepository: T) : ViewModel() {
    val errors: MutableLiveData<Errors> = MutableLiveData()

    override fun onCleared() {
        clientRepository.unSubscribe()
        super.onCleared()
    }


    protected fun processError(error: Throwable) {
        val er = ArrayList<String>(1)
        error.message?.let {
            er.add(it)
        } ?: run { er.add("Erro não identificado.") }
        errors.value = Errors(er)
    }

    fun errorsClear() {
        errors.value = Errors(arrayListOf())
    }


}