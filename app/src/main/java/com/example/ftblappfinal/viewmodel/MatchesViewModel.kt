package com.example.ftblappfinal.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ftblappfinal.repository.MatchesRepository
import com.example.ftblappfinal.viewmodel.state.MatchesState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import okio.IOException
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel

class MatchesViewModel @Inject constructor(private val matchesRepository: MatchesRepository) : ViewModel() {

    private val _inPlayMatchesState = MutableStateFlow<MatchesState>(MatchesState.Empty)
    val inPlayMatchesState: StateFlow<MatchesState> = _inPlayMatchesState.stateIn(
        initialValue = MatchesState.Empty,
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000)

    )

    private val _upcomingMatchesState = MutableStateFlow<MatchesState>(MatchesState.Empty)
    val upcomingMatchesState: StateFlow<MatchesState> = _upcomingMatchesState.stateIn(
        initialValue = MatchesState.Empty,
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000)

    )

    init {
        getAllinPlayMatches()
        getUpcomingMatches()
    }

    private fun getAllinPlayMatches() {
        _inPlayMatchesState.value = MatchesState.Loading


        viewModelScope.launch(Dispatchers.IO) {
            try {
                val inplayMatchesResponse = matchesRepository.getLiveMatches()
                _inPlayMatchesState.value = MatchesState.Success(listOf(inplayMatchesResponse))

            } catch (exception: HttpException) {
                _inPlayMatchesState.value = MatchesState.Error("NO INTERNET CONNECTION")

            } catch (exception: IOException) {
                _inPlayMatchesState.value = MatchesState.Error("Something went wrong")
            }
        }
    }

    private fun getUpcomingMatches() {
        _upcomingMatchesState.value = MatchesState.Loading


        viewModelScope.launch(Dispatchers.IO) {
            try {
                val inplayMatchesResponse = matchesRepository.getMatches()
                _upcomingMatchesState.value = MatchesState.Success(listOf(inplayMatchesResponse))

            } catch (exception: HttpException) {
                _upcomingMatchesState.value = MatchesState.Error("NO INTERNET CONNECTION")

            } catch (exception: IOException) {
                _upcomingMatchesState.value = MatchesState.Error("Something went wrong")
            }
        }
    }

}



