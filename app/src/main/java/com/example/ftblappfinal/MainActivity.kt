package com.example.ftblappfinal

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.SuggestionChipDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ExperimentalComposeApi
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ftblappfinal.data.remote.models.Data
import com.example.ftblappfinal.data.remote.models.Match
import com.example.ftblappfinal.ui.theme.FTBLAPPFinalTheme
import com.example.ftblappfinal.viewmodel.MatchesViewModel
import com.example.ftblappfinal.viewmodel.state.MatchesState
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.Locale

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FTBLAPPFinalTheme {
                Column(modifier = Modifier.padding(10.dp)) {
                    TopAppBar()
                    FetchData()
                }
            }
        }
    }
}


@OptIn(ExperimentalComposeApi::class)
@Composable
fun FetchData(matchesViewModel: MatchesViewModel = viewModel()) {
    val upcomingMatchesState = matchesViewModel.upcomingMatchesState.collectAsState()

    val inplayMatchesState = matchesViewModel.inPlayMatchesState.collectAsState()

    Column {
        when (val state = inplayMatchesState.value) {
            is MatchesState.Empty -> Text(text = "Nothing Available")
            is MatchesState.Loading -> Text(text = "Loading")
            is MatchesState.Success -> LiveMatches(liveMatches = state.data)
            is MatchesState.Error -> Text(text = state.message)  //checks the state and gives the correct error message (InplayMatchesViewModel class)

        }
        when (val state = upcomingMatchesState.value) {
            is MatchesState.Empty -> Text(text = "Nothing Available")
            is MatchesState.Loading -> Text(text = "Loading")
            is MatchesState.Success -> UpcomingMatches(upcomingMatches = state.data)
            is MatchesState.Error -> Text(text = state.message)
        }
    }
}

@Composable
fun LiveMatches(liveMatches: List<Data>) {
    Column(modifier = Modifier.padding(15.dp)) {

        Text(
            text = "Live Matches",
            modifier = Modifier.padding(top = 12.dp),
        )

        if (liveMatches.isEmpty()) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {

                Icon(
                    imageVector = Icons.Default.Info,
                    contentDescription = "No Matches Available"
                )
                Text(text = "No Live Matches At the Moment")

            }

        } else {
            LazyRow(
                modifier = Modifier.padding(top = 15.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(liveMatches.size) {
                    LiveMatchItem(match = liveMatches[it])

                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LiveMatchItem(match: Data) {
    Card(
        shape = RoundedCornerShape(24.dp),
        modifier = Modifier
            .width(300.dp)
            .height(150.dp)
    ) {
        Column(modifier = Modifier.padding(10.dp)) {
            Text(
                text = "League Name",
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                val homeScore = "0"//match.team_home_90min_goals
                val awayScore = "0"//match.team_away_90min_goals


                Text(text = "HOME TEAM")//match.homeName
                Text(text = "$homeScore:$awayScore")
                Text(text = "AWAY TEAM")//match.awayName


            }
            SuggestionChip(
                onClick = { /*TODO*/ },
                label = { /*TODO*/ },
                colors = SuggestionChipDefaults.suggestionChipColors(
                    iconContentColor = Color.White,
                    containerColor = Color.Green
                ),
                modifier = Modifier
                    .align(
                        Alignment.CenterHorizontally
                    )
                    .padding(top = 20.dp)
            )

        }
    }

}


fun matchStatus(match: Match): String {
    //return match status or min of the match from the api
    return when (match.status) {
        "IN PLAY" -> "${match.time}"
        "HALF TIME BREAK" -> "Halftime"
        "NOT STARTED" -> "Not Started"
        "ADDED TIME" -> "Added Time of ${match.added}"
        "FINISHED" -> "FT"
        "INSUFFICIENT DATA " -> "INSUFFICIENT DATA "
        else -> "suspended"

    }

}

@Composable
fun UpcomingMatches(upcomingMatches: List<Data>) {
    Column(modifier = Modifier.padding(15.dp)) {

        Text(
            text = "Scheduled Matches",
            modifier = Modifier.padding(top = 12.dp),
        )

        if (upcomingMatches.isEmpty()) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {

                Icon(
                    imageVector = Icons.Default.Info,
                    contentDescription = "No Upcoming Matches Currently"
                )
                Text(text = "No Upcoming Matches At the Moment")

            }

        } else {
            LazyColumn(
                modifier = Modifier.padding(top = 15.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                items(upcomingMatches.size) {
                    UpcomingMatchItem(match = upcomingMatches[it])

                }
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpcomingMatchItem(match: Data) {
    Card(
        shape = RoundedCornerShape(24.dp),
        modifier = Modifier
            .fillMaxWidth()
            .height(150.dp)
            .padding(bottom = 10.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 0.dp
        )
    ) {


        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            //val month = getMatchDayAndMonth(match.date)
            //val time = getMatchTime(match.scheduled)

            Text(text = "HOME TEAM")//match.homeName
            Text(
                text = "12:00PM 12/01/23",
                color = Color.Blue,
                textAlign = TextAlign.Center
            )
            Text(text = "AWAY TEAM")


        }

    }
}


fun getMatchDayAndMonth(date: String): String? {
    val parser = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH)
    val formatter = SimpleDateFormat("d MMM", Locale.ENGLISH)
    return date.let { it -> parser.parse(it)?.let { formatter.format(it) } }
}

fun getMatchTime(date: String): String? {
    val parser = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH)
    val formatter = SimpleDateFormat("hh:mm a", Locale.ENGLISH)//06:30 pm
    return date.let { it -> parser.parse(it)?.let { formatter.format(it) } }
}

@Composable
fun TopAppBar() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {

        IconButton(onClick = {/*TODO*/ }) {
            Icon(imageVector = Icons.Default.Refresh, contentDescription = "Refresh Icon")
        }

        Text(text = "FTBLSCORES")

        IconButton(onClick = {/*TODO*/ }) {
            Icon(
                imageVector = Icons.Default.Star,
                contentDescription = "Toggle Theme"
            )
        }
    }
}