package dev.ztivnick.boredwatch

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface BoredInterface {
    @GET("/api/activity")
    suspend fun getActivity(
//        @Query("participants") participants: Int
    ): Response<BoredActivityModel>
}