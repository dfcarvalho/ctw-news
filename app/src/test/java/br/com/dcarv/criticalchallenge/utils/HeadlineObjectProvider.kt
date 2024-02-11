package br.com.dcarv.criticalchallenge.utils

import br.com.dcarv.criticalchallenge.sourcelist.domain.model.Headline
import java.time.LocalDateTime

object HeadlineObjectProvider {

    private const val HEADLINE_1 = "title 1"
    private const val HEADLINE_2 = "title 2"
    private const val HEADLINE_3 = "title 3"
    private val BASE_DATE = LocalDateTime.now()
    private val DATE_1 = BASE_DATE
    private val DATE_2 = BASE_DATE.minusHours(1)
    private val DATE_3 = BASE_DATE.minusDays(1)

    fun getUnorderedHeadlinesList() = listOf(
        Headline(title = HEADLINE_2, date = DATE_2),
        Headline(title = HEADLINE_3, date = DATE_3),
        Headline(title = HEADLINE_1, date = DATE_1),
    )

    fun getOrderedHeadlinesList() = listOf(
        Headline(title = HEADLINE_1, date = DATE_1),
        Headline(title = HEADLINE_2, date = DATE_2),
        Headline(title = HEADLINE_3, date = DATE_3),
    )

    fun getHeadline() = Headline(title = HEADLINE_1, date = DATE_1)
}
