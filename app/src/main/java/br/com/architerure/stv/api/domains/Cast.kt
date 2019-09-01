package br.com.architerure.stv.api.domains

data class Cast(
    val cast_id: Int,
    val character: String,
    val name: String,
    val profile_path: String
)