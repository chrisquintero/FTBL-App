package com.example.ftblappfinal.data.remote

import com.example.ftblappfinal.data.remote.models.MatchesResponse
import com.example.ftblappfinal.util.API_KEY
import com.example.ftblappfinal.util.COMPETITION_ID
//import com.example.ftblappfinal.util.GET_INPLAY_FIXTURES
import com.example.ftblappfinal.util.GET_LIVE_MATCHES
import com.example.ftblappfinal.util.GET_MATCHES
//import com.example.ftblappfinal.util.GET_UPCOMING_MATCHES
import com.example.ftblappfinal.util.SECRET_KEY
import retrofit2.http.GET
import retrofit2.http.Query

interface LiveScoreAPI {
    @GET(GET_LIVE_MATCHES)
    suspend fun getLiveMatches(
        @Query("key") apiKey: String = API_KEY,
        @Query("secret") secretKey: String = SECRET_KEY,
        @Query("competition_id") competitionId: Int = COMPETITION_ID
    ): MatchesResponse

    @GET(GET_MATCHES)
    suspend fun getMatches(
        @Query("key") apiKey: String = API_KEY,
        @Query("secret") secretKey: String = SECRET_KEY,
        @Query("competition_id") competitionId: Int = COMPETITION_ID
    ): MatchesResponse

}