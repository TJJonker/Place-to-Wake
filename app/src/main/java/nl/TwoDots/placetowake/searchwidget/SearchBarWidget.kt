package nl.twodots.placetowake.searchwidget

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import nl.twodots.placetowake.R
import nl.twodots.placetowake.models.MainViewModel
import nl.twodots.placetowake.utils.clearFocusOnKeyboardDismiss

@Composable
fun AppSearchBar(mainViewModel: MainViewModel) {
    SearchBar(
        text = mainViewModel.searchTextState.value,
        onTextChange = { mainViewModel.updateSearchTextState(it) },
        modifier = Modifier
            .padding(top = 56.dp, start = 24.dp, end = 24.dp)
    )
}

@Composable
fun SearchBar(
    text: String,
    modifier: Modifier = Modifier,
    onTextChange: (String) -> Unit = {},
    onCloseClicked: () -> Unit = {},
    onSearchClicked: (String) -> Unit = {}

) {
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .height(52.dp)
            .clearFocusOnKeyboardDismiss(),
        elevation = AppBarDefaults.TopAppBarElevation,
        color = Color.White,
        shape = RoundedCornerShape(50)
    ) {
        TextField(
            colors = TextFieldDefaults.textFieldColors(
                textColor = Color.Black,
                focusedIndicatorColor = Color.Transparent
            ),
            value = text,
            onValueChange = { onTextChange(it) },
            placeholder = {
                Text(
                    modifier = Modifier.alpha(ContentAlpha.medium),
                    text = "Search for a location",
                    color = Color.DarkGray
                )
            },
            singleLine = false,
            leadingIcon = {
                IconButton(
                    onClick = { /*TODO*/ }
                ) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Search icon",
                        tint = Color.DarkGray
                    )
                }
            },
            trailingIcon = {
                IconButton(
                    onClick = { /*TODO*/ }
                ) {
                    Icon(
                        modifier = Modifier.height(32.dp).width(32.dp),
                        imageVector = Icons.Default.AccountCircle,
                        contentDescription = "Profile Icon",
                        tint = colorResource(id = R.color.primary)
                    )
                }
            },
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Search
            ),
            keyboardActions = KeyboardActions(
                onSearch = {
                    onSearchClicked(text)
                }
            )
        )
    }
}