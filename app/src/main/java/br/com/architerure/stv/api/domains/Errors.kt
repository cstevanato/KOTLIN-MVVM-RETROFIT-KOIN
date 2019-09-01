package br.com.architerure.stv.api.domains

data class Errors (val errors: ArrayList<String>) {
    override fun toString(): String {
        return errors.toString()
    }
}