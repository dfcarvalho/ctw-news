package br.com.dcarv.criticalchallenge

import androidx.compose.ui.test.junit4.createEmptyComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.test.espresso.IdlingRegistry
import br.com.dcarv.criticalchallenge.test.IdlingDispatchersTestRule
import br.com.dcarv.criticalchallenge.test.MockedApiTestRule
import br.com.dcarv.criticalchallenge.test.OkHttp3IdlingResource
import br.com.dcarv.criticalchallenge.test.lazyActivityScenarioRule
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import okhttp3.OkHttpClient
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

@HiltAndroidTest
class MainActivityTest {

    @get:Rule(order = 1)
    val hiltRule = HiltAndroidRule(this)
    @get:Rule(order = 2)
    val idlingDispatchersTestRule = IdlingDispatchersTestRule()
    @get:Rule(order = 3)
    val mockedApiTestRule = MockedApiTestRule()
    @get:Rule(order = 4)
    val scenarioRule = lazyActivityScenarioRule<MainActivity>(launchActivity = false)
    @get:Rule(order = 5)
    val composeTestRule = createEmptyComposeRule()

    @Inject
    lateinit var okHttpClient: OkHttpClient

    private lateinit var okHttpIdlingResource: OkHttp3IdlingResource

    @Before
    fun setUp() {
        hiltRule.inject()
        okHttpIdlingResource = OkHttp3IdlingResource.create("OkHttp", okHttpClient)
        IdlingRegistry.getInstance().register(okHttpIdlingResource)
    }

    @After
    fun tearDown() {
        IdlingRegistry.getInstance().unregister(okHttpIdlingResource)
    }

    @Test
    fun whenStartedShouldLoadAndShowHeadlines() {
        mockedApiTestRule.mockResponse(200, Preconditions.HEADLINES_RESPONSE)
        scenarioRule.launch()

        composeTestRule.onNodeWithText(Expectations.firstHeadlineTitle).assertExists()
        composeTestRule.onNodeWithText(Expectations.secondHeadlineTitle).assertExists()
        composeTestRule.onNodeWithText(Expectations.thirdHeadlineTitle).assertExists()
    }

    object Preconditions {

        val HEADLINES_RESPONSE = """
            {
                "status": "ok",
                "totalResults": 10,
                "articles": [
                    {
                        "source": {
                            "id": "bbc-news",
                            "name": "BBC News"
                        },
                        "author": "BBC News",
                        "title": "Nevada caucuses v primary: Why both Trump and Haley may claim victory",
                        "description": "Republicans are holding two separate ballots this week, with the candidates not going head to head.",
                        "url": "https://www.bbc.co.uk/news/world-us-canada-68208325",
                        "urlToImage": "https://ichef.bbci.co.uk/news/1024/branded_news/E213/production/_132557875_gettyimages-1958540092.jpg",
                        "publishedAt": "2024-02-05T21:37:13.57114Z",
                        "content": "Against a backdrop of neon lights and the clink of casino chips, the Nevada caucuses were once a colourful and important stop in the race to become the presidential nominee.\r\nBut there are no such th… [+4079 chars]"
                    },
                    {
                        "source": {
                            "id": "bbc-news",
                            "name": "BBC News"
                        },
                        "author": "BBC News",
                        "title": "The highs and lows of Grammys 2024 - and why Taylor Swift won album of year",
                        "description": "The best (and worst) performances, the memorable moments, and why Swift's big win was inevitable.",
                        "url": "https://www.bbc.co.uk/news/entertainment-arts-68200931",
                        "urlToImage": "https://ichef.bbci.co.uk/news/1024/branded_news/B487/production/_132551264_sza-index-getty.jpg",
                        "publishedAt": "2024-02-05T20:22:12.8220465Z",
                        "content": "The 2024 Grammy Awards ended with a bang: Taylor Swift taking home her fourth album of the year trophy.\r\nIt cements her position as the one of the greatest songwriters of her era - although she was g… [+13725 chars]"
                    },
                    {
                        "source": {
                            "id": "bbc-news",
                            "name": "BBC News"
                        },
                        "author": "BBC News",
                        "title": "Drone attack kills six Kurdish-led fighters at US base in east Syria",
                        "description": "A Kurdish-led force blamed Iran-backed militias for the strike on a training centre in Deir al-Zour.",
                        "url": "https://www.bbc.co.uk/news/world-middle-east-68209007",
                        "urlToImage": "https://ichef.bbci.co.uk/news/1024/branded_news/CDC3/production/_132557625_gettyimages-1645171616.jpg",
                        "publishedAt": "2024-02-05T20:07:18.9473278Z",
                        "content": "A drone attack on the largest US military base in Syria has killed at least six allied Kurdish-led fighters.\r\nThe Syrian Democratic Forces (SDF) said its commando academy at the al-Omar oil field in … [+3187 chars]"
                    }
                ]
            }
        """.trimIndent()
    }

    object Expectations {

        const val firstHeadlineTitle = "Nevada caucuses v primary: Why both Trump and Haley may claim victory"
        const val secondHeadlineTitle = "The highs and lows of Grammys 2024 - and why Taylor Swift won album of year"
        const val thirdHeadlineTitle = "Drone attack kills six Kurdish-led fighters at US base in east Syria"
    }
}
