package com.example.taskmobile.data.di

import com.example.taskmobile.core.AppConstans
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitClient private constructor(){

    companion object{
        private lateinit var retrofit: Retrofit
        private val BASE_URL = "http://10.0.0.172:3000/"
        private var token: String = ""

        fun getRetrofitInstance(): Retrofit{
            val httpClient = OkHttpClient.Builder()
            httpClient.addInterceptor(object : Interceptor{
                override fun intercept(chain: Interceptor.Chain): Response {
                    val request = chain.request()
                        .newBuilder()
                        .addHeader(AppConstans.HEADER.TOKEN, token)
                        .build()
                    return chain.proceed(request)
                }

            })

            if(!::retrofit.isInitialized){
                retrofit = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(httpClient.build())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
            }

            return retrofit
        }

        fun addHeader(token: String){
            this.token = token
        }

        fun removeHeader(){
            this.token = ""
        }
    }

}