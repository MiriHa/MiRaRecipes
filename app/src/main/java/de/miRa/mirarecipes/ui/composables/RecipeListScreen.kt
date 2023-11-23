package de.miRa.mirarecipes.ui.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import de.miRa.mirarecipes.R
import de.miRa.mirarecipes.recipes.Recipe
import de.miRa.mirarecipes.recipes.RecipesViewModel
import de.miRa.mirarecipes.ui.theme.Spacings
import java.util.Locale
import kotlin.math.PI
import kotlin.math.cos

@Composable
fun RecipeListScreen(
    viewModel: RecipesViewModel,
    increment: () -> Unit
    //navController: NavHostController
) {
    /* NavHost(navController = navController, startDestination = "recipeListScreen") {
         //composable("recipeView") { RecipeView(recipe =) }
     }*/
    val uiState by viewModel.uiState.collectAsState()
    val backgroundColor = MaterialTheme.colorScheme.background

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.primary)
            .drawBehind { drawBackgroundWave(backgroundColor) } // TODO drawWithCache?
            .padding(Spacings.m)
    ) {
        var searchInput by remember { mutableStateOf(TextFieldValue("")) }

        Row(modifier = Modifier.fillMaxWidth()) {
            TextField(
                searchInput,
                onValueChange = { searchInput = it }
            )
            IconButton(
                onClick = { /*TODO*/ }
            ) {
                Icon(
                    painterResource(R.drawable.icon_tune),
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
            searchInput = searchInput.text,
            selectedFilterTags = uiState.selectedTags,
            items = uiState.recipesItems
        )
        Button(onClick = increment ){
            
        }
    }
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
        verticalArrangement = Arrangement.Center,
        horizontalArrangement = Arrangement.Start
    ) {
        items.forEach { tag ->
            val isSelected by remember(selectedFilterTags) {
                mutableStateOf(
                    selectedFilterTags.contains(
                        tag
                    )
                )
            }
            TagChip(
                modifier = Modifier.padding(horizontal = Spacings.xxs),
                selected = isSelected,
                onClick = { onTagSelected(tag) },
                label = tag
            )
        }
        if (selectedFilterTags.isNotEmpty()) {
            IconChip(
                onClick = { onRemoveAllTags() },
            )
        }
    }
}

@Composable
fun Header() {

}

@Composable
fun RecipeListItem(recipe: Recipe, onItemClick: (Recipe) -> Unit) {
    Card {

    }
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

/*fun BentleyCard(
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    onClick: () -> Unit = {},
    content: @Composable () -> Unit,
) {
    Surface(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        color = BentleyTheme.colors.background,
        elevation = BentleyElevations.Cards,
        content = content
    )
}*/

fun DrawScope.drawBackgroundWave(color: Color) {
    val rectWidth = size.width
    val rectHeight = size.height

    val path = Path()

    val waveLength = rectWidth * 1.1
    val waveAmplitude = rectHeight / 25

    for (i in 0 until rectWidth.toInt() step 4) {
        val x = i.toFloat()
        val y = rectHeight / 4 + waveAmplitude * cos(2 * PI * i / waveLength + (2 * PI) / 3)
        path.lineTo(x, y.toFloat())
    }

    path.lineTo(size.width, size.height)
    path.lineTo(0f, size.height)
    path.close()

    drawPath(
        path = path,
        color = color
    )
}
