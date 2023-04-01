package de.miRa.mirarecipes.ui.composables

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import de.miRa.mirarecipes.recipes.Recipe
import de.miRa.mirarecipes.ui.theme.MiRaRecipesTheme
import java.util.Locale
import kotlin.collections.ArrayList

data class TagFilter(
    val title: String,
    var isSelected: Boolean
)


fun createTagList(items: List<String>): SnapshotStateList<TagFilter> {
    val filterTagItems = mutableStateListOf<TagFilter>()
    items.forEach {
        filterTagItems.add(
            TagFilter(it, false),
        )
    }
    return filterTagItems
}

fun createDummyRecepieList(): SnapshotStateList<Recipe> {
    return mutableStateListOf(
        Recipe(
            title = "Pizza",
            tags = listOf("Essen")
        ),
        Recipe(
            title = "Suppe",
            tags = listOf("Essen")
        ),
        Recipe(
            title = "Nudeln",
            tags = listOf("Nudeln")
        )
    )
}

@Composable
fun RecipeListScreen() {
    val tagList = listOf("Essen", "Nudeln")

    Column() {

        val searchInput = remember { mutableStateOf(TextFieldValue("")) }
        val items = remember { createDummyRecepieList() }
        val tagFiltersItems = remember { createTagList(tagList) }

        SearchView(searchInput)
        TagFilters(tagFiltersItems)
        ItemList(
            searchInput = searchInput.value.text,
            filterTags = tagFiltersItems,
            items = items
        )
    }
}

@Composable
fun SearchView(state: MutableState<TextFieldValue>) {
    TextField(
        value = state.value,
        onValueChange = { value ->
            state.value = value
        },
        modifier = Modifier.fillMaxWidth(),
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
    )
}


@Composable
fun ItemList(
    searchInput: String,
    filterTags: List<TagFilter>,
    items: List<Recipe>
) {
    var filteredItems: List<Recipe>
    LazyColumn(modifier = Modifier.fillMaxWidth()) {

        filteredItems = if (searchInput.isEmpty() && filterTags.all { !it.isSelected }) {
            items
        } else {
            val resultList = ArrayList<Recipe>()
            for (item in items) {
                Log.d("xxx", "filterTags $filterTags ${filterTags.filter { it.isSelected }.map { it.title }}")
                if (item.title.lowercase(Locale.getDefault())
                        .contains(searchInput.lowercase(Locale.getDefault())) ||
                    item.tags.any { tag ->
                        tag in filterTags.filter { it.isSelected }.map { it.title }
                    }
                ) {
                    resultList.add(item)
                }
            }
            resultList
        }

        items(filteredItems) { filteredItem ->
            ItemListItem(
                ItemText = filteredItem.title,
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
    items: List<TagFilter>,
) {
    FlowRow(
        modifier = Modifier.fillMaxWidth(),
    ) {
        items.forEach {
            var selected by remember { mutableStateOf(it.isSelected) }
            FilterChip(
                modifier = Modifier.padding(8.dp),
                selected = selected,
                onClick = {
                    selected = !selected
                    it.isSelected = !it.isSelected
                    Log.d("xxx", "selected ${it.title} ${it.isSelected}")
                },
                label = { Text(it.title) },
                leadingIcon = {
                    if (selected) {
                        Icon(
                            imageVector = Icons.Filled.Done,
                            contentDescription = null,
                            modifier = Modifier.size(FilterChipDefaults.IconSize)
                        )
                    }
                }
            )
        }
    }
}

@Composable
fun ItemListItem(ItemText: String, onItemClick: (String) -> Unit) {
    Row(
        modifier = Modifier
            .clickable(onClick = { onItemClick(ItemText) })
            .height(57.dp)
            .fillMaxWidth()
            .padding(PaddingValues(8.dp, 16.dp))
    ) {
        Text(text = ItemText, fontSize = 18.sp, color = Color.White)
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun DefaultPreview() {
    MiRaRecipesTheme {
        RecipeListScreen()
    }
}