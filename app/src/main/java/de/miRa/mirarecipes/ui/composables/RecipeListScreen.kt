package de.miRa.mirarecipes.ui.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.AssistChip
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import de.miRa.mirarecipes.recipes.Recipe
import de.miRa.mirarecipes.recipes.RecipesViewModel
import java.util.Locale

@Composable
fun RecipeListScreen(
    viewModel: RecipesViewModel,
    //navController: NavHostController
) {
    /* NavHost(navController = navController, startDestination = "recipeListScreen") {
         //composable("recipeView") { RecipeView(recipe =) }
     }*/
    val uiState by viewModel.uiState.collectAsState()

    Column {
        val searchInput = remember { mutableStateOf(TextFieldValue("")) }

        Row {
            SearchView(searchInput)
            IconButton(
                onClick = { /*TODO*/ }
            ) {
                Icon(
                    Icons.Default.Add,
                    contentDescription = null
                )
            }
        }
        TagFilters(
            items = uiState.filterTags,
            selectedFilterTags = uiState.selectedTags,
            onTagSelected = { viewModel.onTagSelected(it) },
            onRemoveAllTags = { viewModel.removeAllSelectedTags() }
        )
        ItemList(
            searchInput = searchInput.value.text,
            selectedFilterTags = uiState.selectedTags,
            items = uiState.recipesItems
        )
    }
}

@Composable
fun SearchView(
    state: MutableState<TextFieldValue>
) {
    TextField(
        value = state.value,
        onValueChange = { value ->
            state.value = value
        },
        modifier = Modifier
            .fillMaxWidth(),
        textStyle = TextStyle(color = Color.White, fontSize = 18.sp),
        leadingIcon = {
            Icon(
                Icons.Default.Search,
                contentDescription = "",
                modifier = Modifier
                    .padding(15.dp)
                    .size(24.dp)
            )
        },
        trailingIcon = {
            if (state.value != TextFieldValue("")) {
                IconButton(
                    onClick = {
                        state.value =
                            TextFieldValue("")
                    }
                ) {
                    Icon(
                        Icons.Default.Close,
                        contentDescription = "",
                    )
                }
            }
        },
        singleLine = true,
        shape = TextFieldDefaults.outlinedShape
    )
}


@Composable
fun ItemList(
    searchInput: String,
    selectedFilterTags: List<String>,
    items: List<Recipe>
) {
    var filteredItems: List<Recipe>
    LazyColumn(modifier = Modifier.fillMaxWidth()) {

        filteredItems = if (searchInput.isEmpty() && selectedFilterTags.isEmpty()) {
            items
        } else {
            val resultList = ArrayList<Recipe>()
            items.forEach { item ->
                val isIncludedInSearchInput = item.title.lowercase(Locale.getDefault())
                    .contains(searchInput.lowercase(Locale.getDefault()))
                    .takeIf { searchInput.isNotEmpty() } ?: true

                val isIncludedInTagInput = selectedFilterTags.all { selectedFilterTagItem ->
                    item.tags.contains(selectedFilterTagItem)
                }.takeIf { selectedFilterTags.isNotEmpty() } ?: true

                if (isIncludedInSearchInput && isIncludedInTagInput) {
                    resultList.add(item)
                }
            }
            resultList
        }

        items(filteredItems) { filteredItem ->
            RecipeListItem(
                recipe = filteredItem,
                onItemClick = {
                    // TODO navigate to subscreen
                }
            )
        }

    }
}

@OptIn(ExperimentalLayoutApi::class, ExperimentalMaterial3Api::class)
@Composable
fun TagFilters(
    items: List<String>,
    selectedFilterTags: List<String>,
    onTagSelected: (String) -> Unit,
    onRemoveAllTags: () -> Unit
) {
    FlowRow(
        modifier = Modifier.fillMaxWidth(),
    ) {
        items.forEach { tag ->
            val isSelected by remember(selectedFilterTags) {
                mutableStateOf(
                    selectedFilterTags.contains(
                        tag
                    )
                )
            }
            FilterChip(
                modifier = Modifier.padding(8.dp),
                selected = selectedFilterTags.contains(tag),
                onClick = {
                    onTagSelected(tag)
                },
                label = { Text(tag) },
                leadingIcon = {
                    if (selectedFilterTags.contains(tag)) {
                        Icon(
                            imageVector = Icons.Filled.Done,
                            contentDescription = null,
                            modifier = Modifier.size(FilterChipDefaults.IconSize)
                        )
                    }
                }
            )
        }
        AssistChip(
            onClick = { onRemoveAllTags() },
            label = {
                Icon(Icons.Default.Close, contentDescription = null)
            }
        )
    }
}

@Composable
fun Header() {

}

@Composable
fun RecipeListItem(recipe: Recipe, onItemClick: (Recipe) -> Unit) {
    Column(
        modifier = Modifier
            .clickable(onClick = { onItemClick(recipe) })
            .fillMaxWidth()
            .padding(PaddingValues(8.dp, 16.dp))
    ) {
        Text(text = recipe.title, fontSize = 18.sp)
        Row(modifier = Modifier.fillMaxWidth()) {
            recipe.tags.forEach {
                Text(text = it, fontSize = 12.sp)
            }
        }
    }
}

/*
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun DefaultPreview() {
    MiRaRecipesTheme {
        RecipeListScreen(RecipesViewModel())
    }
}*/
