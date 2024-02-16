package com.example.ftblappfinal.data.remote.models

data class Data(
    val match: List<Match>
) {
    fun isEmpty(): Boolean {
        return true

    }
}