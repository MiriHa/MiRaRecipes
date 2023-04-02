package de.miRa.mirarecipes.ui.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import de.miRa.mirarecipes.recipes.Recipe
import de.miRa.mirarecipes.recipes.RecipesViewModel
import java.util.*

@Composable
fun RecipeListScreen(
    viewModel: RecipesViewModel,
    //navController: NavHostController
) {
   /* NavHost(navController = navController, startDestination = "recipeListScreen") {
        //composable("recipeView") { RecipeView(recipe =) }
    }*/

    val tagList = listOf("Essen", "Nudeln")

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
            items = tagList,
            onTagSelected = { tag, isSelected ->
                viewModel.onTagSelected(tag, isSelected)
            },
            onRemoveAllTags = {
                viewModel.removeAllSelectedTags()
            }
        )
        ItemList(
            searchInput = searchInput.value.text,
            selectedFilterTags = viewModel.selectedFilterTags,
            items = viewModel.items
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
    selectedFilterTags: SnapshotStateList<String>,
    items: List<Recipe>
) {
    var filteredItems: List<Recipe>
    LazyColumn(modifier = Modifier.fillMaxWidth()) {

        filteredItems = if (searchInput.isEmpty() && selectedFilterTags.isEmpty()) {
            items
        } else {
            val resultList = ArrayList<Recipe>()
            items.forEach { item ->
                if (item.title.lowercase(Locale.getDefault())
                        .contains(searchInput.lowercase(Locale.getDefault())) &&
                    item.tags.any(selectedFilterTags::contains) // TODO when more than one tag its not working
                ) {
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
    onTagSelected: (String, Boolean) -> Unit,
    onRemoveAllTags: () -> Unit
) {
    FlowRow(
        modifier = Modifier.fillMaxWidth(),
    ) {
        items.forEach { tag ->
            var isSelected by remember { mutableStateOf(false) }
            FilterChip(
                modifier = Modifier.padding(8.dp),
                selected = isSelected,
                onClick = {
                    isSelected = !isSelected
                    onTagSelected(tag, isSelected)
                },
                label = { Text(tag) },
                leadingIcon = {
                    if (isSelected) {
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
