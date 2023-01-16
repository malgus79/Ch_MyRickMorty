package com.myrickmorty.accessdata

//import com.google.gson.Gson
//import com.myrickmorty.data.model.RickMorty
//import com.myrickmorty.data.model.RickMortyList
//import okhttp3.mockwebserver.MockResponse
//import okhttp3.mockwebserver.MockWebServer
//import org.hamcrest.MatcherAssert.assertThat
//import org.hamcrest.Matchers.*
//import org.junit.After
//import org.junit.Before
//import org.junit.Test
//import org.junit.runner.RunWith
//import org.mockito.junit.MockitoJUnitRunner
//import java.net.HttpURLConnection
//
//
//@RunWith(MockitoJUnitRunner::class)
//class ResponseServerTest {
//    private lateinit var mockWebServer: MockWebServer
//
//    @Before
//    fun setup() {
//        mockWebServer = MockWebServer()
//        mockWebServer.start()
//    }
//
//    @After
//    fun tearDown() {
//        mockWebServer.shutdown()
//    }
//
//    @Test
//    fun `read json file success`() {
//        val reader = JSONFileLoader().loadJSONString("character_response_success")
//        assertThat(reader, `is`(notNullValue()))
//        assertThat(reader, containsString("Rick Sanchez"))
//    }
//
//    @Test
//    fun `get character and check name exist`() {
//        val response = MockResponse()
//            .setResponseCode(HttpURLConnection.HTTP_OK)
//            .setBody(
//                JSONFileLoader().loadJSONString("character_response_success")
//                    ?: "{errorCode:34}"
//            )
//        mockWebServer.enqueue(response)
//
//        //validar que tenga la propiedad "timezone"
//        assertThat(response.getBody()?.readUtf8(), containsString("\"name\""))
//    }
//
//    @Test
//    fun `get character and check fail response`() {
//        val response = MockResponse()
//            .setResponseCode(HttpURLConnection.HTTP_OK)
//            .setBody(
//                JSONFileLoader().loadJSONString("character_response_fail")
//                    ?: "{errorCode:34}"
//            )
//        mockWebServer.enqueue(response)
//
//        assertThat(
//            response.getBody()?.readUtf8(),
//            containsString(
//                "{\"error\":\"There is nothing here.\"}"
//            )
//        )
//    }
//
//    @Test
//    fun `get characters and check contains result list no empty`() {
//        val response = MockResponse()
//            .setResponseCode(HttpURLConnection.HTTP_OK)
//            .setBody(
//                JSONFileLoader().loadJSONString("character_response_success")
//                    ?: "{errorCode:34}"
//            )
//        mockWebServer.enqueue(response)
//
//        assertThat(response.getBody()?.readUtf8(), containsString("name"))
//
//        val json = Gson().fromJson(response.getBody()?.readUtf8() ?: "", RickMortyList::class.java)
//        assertThat(json.results.isEmpty(), `is`(false))
//    }
//}