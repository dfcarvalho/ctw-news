package br.com.dcarv.criticalchallenge.test

import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.QueueDispatcher
import org.junit.rules.TestRule
import org.junit.runner.Description
import org.junit.runners.model.Statement

class MockedApiTestRule : TestRule {

    private val mockWebServer = MockWebServer()
    private val dispatcher = QueueDispatcher().also {
        mockWebServer.dispatcher = it
    }

    override fun apply(base: Statement?, description: Description?): Statement {
        return object : Statement() {
            override fun evaluate() {
                mockWebServer.start(port = 8181)

                try {
                    base?.evaluate()
                } finally {
                    mockWebServer.shutdown()
                }
            }
        }
    }

    fun mockResponse(statusCode: Int, body: String) {
        val response = MockResponse()
            .setResponseCode(statusCode)
            .setHeader("Content-Type", "application/json")
            .setBody(body)
        dispatcher.enqueueResponse(response)
    }
}
