package dev.ztivnick.boredwatch

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitObject {
    private const val baseUrl = "http://www.boredapi.com/"
    private fun getRetrofitInstance(): Retrofit {
        return Retrofit.Builder().baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val retrofitInstance: BoredInterface = getRetrofitInstance().create(BoredInterface::class.java)
}