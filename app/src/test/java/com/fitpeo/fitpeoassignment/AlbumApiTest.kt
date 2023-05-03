package com.fitpeo.fitpeoassignment

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.fitpeo.fitpeoassignment.repository.MainRepository
import com.fitpeo.fitpeoassignment.service.ApiService
import com.google.gson.GsonBuilder
import com.google.gson.JsonParser
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.*
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory


@RunWith(JUnit4::class)
class AlbumApiTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private val server = MockWebServer()
    private lateinit var repository: MainRepository
    private lateinit var mockedResponse: String

    private val gson = GsonBuilder()
                          .setLenient()
                          .create()

    @Before
    fun init() {

        server.start(8000)

        val baseURL = server.url("/").toString()

        val okHttpClient = OkHttpClient
            .Builder()
            .build()
        val service = Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create())
            .baseUrl(baseURL)
            .client(okHttpClient)
            .build().create(ApiService::class.java)

        repository = MainRepository(service)
    }

    @Test
    fun testApiSuccess() {
        mockedResponse = MockResponseFileReader("success.json").content

        server.enqueue(
            MockResponse()
                .setResponseCode(200)
                .setBody(mockedResponse)
        )

        val response = runBlocking { repository.apiService.getDetails() }
        val json = gson.toJson(response.body())

        val resultResponse = JsonParser.parseString(json)
        val expectedResponse = JsonParser.parseString(mockedResponse)

        Assert.assertNotNull(response)
        Assert.assertTrue(resultResponse.equals(expectedResponse))
    }

    @Test
    fun testApi404() {
        mockedResponse = MockResponseFileReader("error404.json").content

        server.enqueue(
            MockResponse()
                .setResponseCode(404)
                .setBody(mockedResponse)
        )

        val response = runBlocking { repository.apiService.getDetails() }
        Assert.assertNotNull(response)
        Assert.assertTrue(response.message().equals("Client Error"))
        Assert.assertTrue(response.code()==404)
    }

    @Test
    fun testApi500() {
        mockedResponse = MockResponseFileReader("error500.json").content

        server.enqueue(
            MockResponse()
                .setResponseCode(500)
                .setBody(mockedResponse)
        )

        val response = runBlocking { repository.apiService.getDetails() }
        Assert.assertNotNull(response)
        Assert.assertTrue(response.message().equals("Server Error"))
        Assert.assertTrue(response.code()==500)
    }

    @After
    fun tearDown() {
        server.shutdown()
    }
}