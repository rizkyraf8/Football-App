package com.rafcode.schedulefootball.api

import android.util.Log
import com.google.gson.Gson
import com.rafcode.schedulefootball.BuildConfig
import com.rafcode.schedulefootball.api.request.AuthRequest
import com.rafcode.schedulefootball.api.response.*
import io.reactivex.Observable
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.json.JSONObject
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*
import java.util.concurrent.TimeUnit

open class ApiService {

    lateinit var api: Api
    lateinit var retrofit: Retrofit

    fun getService(): Api {
        retrofit = Retrofit.Builder()
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .client(provideOkHttpClient())
            .baseUrl(BuildConfig.BASE_URL)
            .build()

        return retrofit.create(Api::class.java)
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
                .method(original.method, original.body)
                .build()
            chain.proceed(request)
        }
    }

    private fun endSessionInterceptor(): Interceptor {
        return Interceptor { chain ->
            val request = chain.request()
            val response = chain.proceed(request)
            val body = response.body
            if (response.isSuccessful) {
                val responseJson = response.body.toString()
                val gson = Gson()
                Log.e("Tag", gson.toJson(response.isSuccessful))
                Log.e("Tag", gson.toJson(request.body))

                try {
                    val obj = JSONObject(responseJson)

                } catch (e: Exception) {
                    e.localizedMessage?.let {
                        Log.d("Session.Logout", it)
                    }
                }

            }
            response
        }
    }


    interface Api {

        @POST("auth/login")
        fun authLogin(
            @Body body: AuthRequest
        ): Observable<AuthResponse>

        @POST("auth/register")
        fun authRegister(
            @Body body: AuthRequest
        ): Observable<AuthResponse>

        @GET("event/next/{id}")
        fun nextEventLeague(
            @Header("Authorization") auth: String,
            @Path("id") id: String
        ): Observable<EventResponse>

        @GET("event/last/{id}")
        fun lastEventLeague(
            @Header("Authorization") auth: String,
            @Path("id") id: String
        ): Observable<EventResponse>

        @GET("team/detail/{id}")
        fun detailTeam(
            @Header("Authorization") auth: String,
            @Path("id") id: String
        ): Observable<TeamResponse>

        @GET("leagues")
        fun allLeagues(
            @Header("Authorization") auth: String
        ): Observable<LeagueResponse>

        @GET("team/{id}")
        fun teamLeague(
            @Header("Authorization") auth: String,
            @Path("id") id: String
        ): Observable<TeamResponse>

        @GET("team/player/{id}")
        fun listPlayerTeam(
            @Header("Authorization") auth: String,
            @Path("id") id: String
        ): Observable<PlayerResponse>

        @GET("team/search")
        fun searchTeam(
            @Header("Authorization") auth: String,
            @Query("team") team: String
        ): Observable<TeamResponse>

        @GET("event/search")
        fun searchEvent(
            @Header("Authorization") auth: String,
            @Query("event") event: String
        ): Observable<EventsSearch>
    }
}