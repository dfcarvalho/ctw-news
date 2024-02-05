package br.com.dcarv.criticalchallenge.sourcelist.presentation

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import br.com.dcarv.criticalchallenge.common.theme.CriticalChallengeTheme
import br.com.dcarv.criticalchallenge.sourcelist.domain.model.Headline
import org.junit.Rule
import org.junit.Test

private const val HEADLINE_1 = "title 1"
private const val HEADLINE_2 = "title 2"
private const val HEADLINE_3 = "title 3"

class SourceListScreenKtTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun whenInLoadingStateShouldShowLoadingIndicator() {
        val state = SourceListViewState(isLoading = true)
        renderState(state)

        composeTestRule.onNodeWithTag(SourceListScreenTestTag.LOADING_INDICATOR)
            .assertExists()
    }

    @Test
    fun whenInLoadedStateShouldShowContent() {
        val sourceName = "BBC News"
        val state = SourceListViewState(
            isLoading = false,
            sourceName = sourceName,
            headlines = listOf(
                Headline(
                    title = HEADLINE_1,
                ),
                Headline(
                    title = HEADLINE_2,
                ),
                Headline(
                    title = HEADLINE_3,
                ),
            )
        )
        renderState(state)

        composeTestRule.onNodeWithTag(SourceListScreenTestTag.LOADING_INDICATOR).assertDoesNotExist()
        composeTestRule.onNodeWithText(sourceName).assertExists()
        composeTestRule.onNodeWithText(HEADLINE_1).assertExists()
        composeTestRule.onNodeWithText(HEADLINE_2).assertExists()
        composeTestRule.onNodeWithText(HEADLINE_3).assertExists()
    }

    private fun renderState(state: SourceListViewState) {
        composeTestRule.setContent {
            CriticalChallengeTheme {
                SourceListScreen(state = state)
            }
        }
    }
}
