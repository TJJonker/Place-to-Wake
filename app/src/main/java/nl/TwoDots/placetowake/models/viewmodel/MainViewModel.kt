package nl.TwoDots.placetowake.models.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import nl.twodots.placetowake.searchwidget.SearchWidgetState

class MainViewModel : ViewModel() {

    /** Keeps track of the state of the Search Widget */
    private val _searchWidgetState: MutableState<SearchWidgetState> =
        mutableStateOf(value = SearchWidgetState.CLOSED)

    /** Public getter method for the Search Widget State */
    val searchWidgetState: State<SearchWidgetState> = _searchWidgetState

    /** Keeps track of the state of the Search Text */
    private val _searchTextState: MutableState<String> =
        mutableStateOf("")

    /** Public getter method for the Search Text State */
    val searchTextState: State<String> = _searchTextState



    /** Updates the Search Widget State */
    fun updateSearchWidgetState(newValue: SearchWidgetState){
        _searchWidgetState.value = newValue
    }

    /** Updates the Search Text State */
    fun updateSearchTextState(newValue: String){
        _searchTextState.value = newValue
    }
}