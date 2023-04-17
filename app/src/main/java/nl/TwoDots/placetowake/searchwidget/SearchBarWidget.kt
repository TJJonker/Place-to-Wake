package nl.twodots.placetowake.searchwidget

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import nl.twodots.placetowake.models.MainViewModel

@Composable
fun AppSearchBar(mainViewModel: MainViewModel) {
    SearchBar(
        text = mainViewModel.searchTextState.value,
        onTextChange = { mainViewModel.updateSearchTextState(it) },
        modifier = Modifier.padding(top = 72.dp, start = 24.dp, end = 24.dp)
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
            .height(56.dp),
        elevation = AppBarDefaults.TopAppBarElevation,
        color = Color.White,
        shape = RoundedCornerShape(50)
    ) {
        TextField(
            value = text,
            onValueChange = { onTextChange(it) },
            placeholder = {
                Text(
                    modifier = Modifier.alpha(ContentAlpha.medium),
                    text = "Search for a location"
                )
            },
            singleLine = true,
            leadingIcon = {
                IconButton(
                    onClick = { /*TODO*/ }
                ) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Search icon"
                    )
                }
            },
            trailingIcon = {
                IconButton(
                    onClick = { /*TODO*/ }
                ) {
                    Icon(
                        imageVector = Icons.Default.AccountCircle,
                        contentDescription = "Profile Icon",
                        tint = Color.Blue
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
