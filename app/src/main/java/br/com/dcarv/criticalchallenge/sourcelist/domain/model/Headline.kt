package br.com.dcarv.criticalchallenge.sourcelist.domain.model

import java.io.Serializable
import java.time.LocalDateTime

data class Headline(
    val title: String,
    val date: LocalDateTime,
    val imageUrl: String? = null,
    val content: String = "",
) : Serializable
