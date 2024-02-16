package com.example.ftblappfinal.repository

import com.example.ftblappfinal.data.remote.LiveScoreAPI
import com.example.ftblappfinal.data.remote.models.Data
import javax.inject.Inject

class MatchesRepository @Inject constructor(private val liveScoreAPI: LiveScoreAPI) {

    suspend fun getMatches(): Data = liveScoreAPI.getMatches().data
    suspend fun getLiveMatches(): Data = liveScoreAPI.getLiveMatches().data

}

