package com.rafcode.schedulefootball.api

import android.util.Log
import com.google.gson.Gson
import com.rafcode.schedulefootball.api.response.*
import io.reactivex.Observable
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.json.JSONObject
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.concurrent.TimeUnit

open class ApiService {

    private val BASE_URL = "https://www.thesportsdb.com/api/v1/json/1/"

    lateinit var api: ApiService.Api
    lateinit var retrofit: Retrofit

    fun getService(): Api {
        retrofit = Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(provideOkHttpClient())
                .baseUrl(BASE_URL)
                .build()

        return retrofit.create(ApiService.Api::class.java)
    }

    private fun provideOkHttpClient(): OkHttpClient {

        return OkHttpClient.Builder()
                .connectTimeout(60 * 1000, TimeUnit.MILLISECONDS)
                .readTimeout(60 * 1000, TimeUnit.MILLISECONDS)
                .addInterceptor(getLoggingInterceptor())
                .addInterceptor(getRequestInterceptor())
                .addInterceptor(endSessionInterceptor())
                .build()
    }

    private fun getLoggingInterceptor(): HttpLoggingInterceptor {
        val interceptor = HttpLoggingInterceptor()
        return interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
    }

    private fun getRequestInterceptor(): Interceptor {
        return Interceptor { chain ->
            val original = chain.request()
            val request = original.newBuilder()
                    .method(original.method(), original.body())
                    .build()
            chain.proceed(request)
        }
    }

    private fun endSessionInterceptor(): Interceptor {
        return Interceptor { chain ->
            val request = chain.request()
            val response = chain.proceed(request)
            val body = response.body()
            if (response.isSuccessful) {
                val responseJson = response.body().toString()
                val gson = Gson()
                Log.e("Tag", gson.toJson(response.isSuccessful))
                Log.e("Tag", gson.toJson(request.body()))

                try {
                    val obj = JSONObject(responseJson)

                } catch (e: Exception) {
                    Log.d("Session.Logout", e.localizedMessage)
                }

            }
            response
        }
    }


    interface Api {

        @GET("eventsnextleague.php")
        fun nextEventLeague(@Query("id") id: String): Observable<Events>

        @GET("eventspastleague.php")
        fun lastEventLeague(@Query("id") id: String): Observable<Events>

        @GET("lookupteam.php")
        fun detailTeam(@Query("id") id: Int): Observable<Teams>

        @GET("all_leagues.php")
        fun allLeagues(): Observable<Leagues>

        @GET("lookup_all_teams.php")
        fun teamLeague(@Query("id") id: String): Observable<Teams>

        @GET("lookup_all_players.php")
        fun listPlayerTeam(@Query("id") id: String): Observable<Players>

        @GET("searchteams.php")
        fun searchTeam(@Query("t") team: String): Observable<Teams>

        @GET("searchevents.php")
        fun searchEvent(@Query("e") event: String): Observable<EventsSearch>


    }
}