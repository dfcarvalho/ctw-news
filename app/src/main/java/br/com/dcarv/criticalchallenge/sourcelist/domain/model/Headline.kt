package br.com.dcarv.criticalchallenge.sourcelist.domain.model

import java.time.LocalDateTime

data class Headline(
    val title: String,
    val date: LocalDateTime,
)
