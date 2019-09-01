package br.com.architerure.stv.app.ui.main.domains

data class MoviesTotalsPage(
    val page: Int,
    val total_results: Int,
    val total_pages: Int
)