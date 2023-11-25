package de.miRa.mirarecipes.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import de.miRa.mirarecipes.R
import de.miRa.mirarecipes.recipes.Recipe
import de.miRa.mirarecipes.recipes.RecipesViewModel
import de.miRa.mirarecipes.recipes.favouriteTag
import de.miRa.mirarecipes.ui.composables.IconChip
import de.miRa.mirarecipes.ui.composables.ItemCard
import de.miRa.mirarecipes.ui.composables.StaticChip
import de.miRa.mirarecipes.ui.composables.TagChip
import de.miRa.mirarecipes.ui.composables.TextField
import de.miRa.mirarecipes.ui.theme.Spacings
import java.util.Locale
import kotlin.math.PI
import kotlin.math.cos

val listItemHeight = 100.dp

@Composable
fun Cookbook(
    viewModel: RecipesViewModel,
    navigateToRecipeDetails: (String) -> Unit,
    navigateToRecipeEdit: (String) -> Unit,
    navigateToRecents: () -> Unit
) {
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

        Header(
            searchInput = searchInput,
            onSearchChange = { searchInput = it }
        )
        Spacer(modifier = Modifier.height(Spacings.xl))
        TagFilters(
            items = uiState.filterTags,
            selectedFilterTags = uiState.selectedTags,
            onTagSelected = { viewModel.onTagSelected(it) },
            onRemoveAllTags = { viewModel.removeAllSelectedTags() }
        )

        ItemList(
            searchInput = searchInput.text,
            selectedFilterTags = uiState.selectedTags,
            items = uiState.recipesItems,
            uiState.recentRecipe,
            openRecents = navigateToRecents,
            openRecipeDetails = navigateToRecipeDetails
        )
    }
}

@Composable
fun ItemList(
    searchInput: String,
    selectedFilterTags: List<String>,
    items: List<Recipe>,
    recentRecipe: Recipe,
    openRecents: () -> Unit,
    openRecipeDetails: (String) -> Unit
) {
    var filteredItems: List<Recipe> = if (searchInput.isEmpty() && selectedFilterTags.isEmpty()) {
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
    } // TODO move search and filteredItems in viewmodel uistate

    if (filteredItems.isEmpty()) {
        EmptyList()
    } else {
        LazyColumn(modifier = Modifier.fillMaxWidth()) {
            item {
                Spacer(modifier = Modifier.height(Spacings.s))
                Recent(recentRecipe, openRecipeDetails, openRecents)
                Spacer(modifier = Modifier.height(Spacings.s))
                Text(
                    text = stringResource(R.string.cookbook_title_all),
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.inverseOnSurface
                )
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
}

@Composable
fun Recent(
    recentRecipe: Recipe,
    onItemClick: (String) -> Unit,
    openRecents: () -> Unit
) {
    Column {
        Row(
            modifier = Modifier.clickable { openRecents() },
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(R.string.cookbook_title_recents),
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.inverseOnSurface
            )
            Icon(
                painter = painterResource(id = R.drawable.icon_forward),
                modifier = Modifier.height(Spacings.m),
                tint = MaterialTheme.colorScheme.inverseOnSurface,
                contentDescription = null
            )
        }
        Spacer(modifier = Modifier.height(Spacings.xs))
        RecipeListItem(recipe = recentRecipe, onItemClick = onItemClick)
    }
}

@Composable
fun TagFilters(
    items: List<String>,
    selectedFilterTags: List<String>,
    onTagSelected: (String) -> Unit,
    onRemoveAllTags: () -> Unit
) {
    Column {
        Text(
            text = stringResource(R.string.cookbook_title_find),
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.inverseOnSurface
        )
        Spacer(modifier = Modifier.height(Spacings.xs))
        // TODO keep as flow row or make it scrollable
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState()),
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
}

@Composable
fun Header(
    searchInput: TextFieldValue,
    onSearchChange: (TextFieldValue) -> Unit,
) {
    Column {
        Spacer(modifier = Modifier.height(Spacings.m))
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { /*TODO*/ }) {
                Icon(
                    painterResource(R.drawable.icon_menu),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onPrimary
                )
            }
            Text(
                text = stringResource(id = R.string.cookbook_title),
                style = MaterialTheme.typography.headlineLarge,
                color = MaterialTheme.colorScheme.onPrimary
            )
        }
        Spacer(modifier = Modifier.height(Spacings.xs))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextField(
                searchInput,
                onValueChange = onSearchChange,
                modifier = Modifier.weight(1f),
                hint = stringResource(id = R.string.cookbook_search)
            )
            IconButton(onClick = { /*TODO*/ }) {
                Icon(
                    painterResource(R.drawable.icon_tune),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onPrimary
                )
            }
        }
        Spacer(modifier = Modifier.height(Spacings.m))
    }
}

@Composable
fun EmptyList() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(id = R.string.cookbook_title_no_items),
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.inverseOnSurface
        )
    }
}

@Composable
fun RecipeListItem(
    recipe: Recipe,
    onItemClick: (String) -> Unit
) {
    val isFavourite by remember(recipe.isFavourite.value) { recipe.isFavourite }

    ItemCard(
        modifier = Modifier
            .clickable(onClick = { onItemClick(recipe.recipeID) })
            .height(listItemHeight)
    ) {
        Row(
            modifier = Modifier.padding(Spacings.s),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                modifier = Modifier
                    .size(75.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.background),
                painter = painterResource(id = R.drawable.icon_add),
                contentDescription = null
            )
            Spacer(modifier = Modifier.width(Spacings.s))
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f),
                verticalArrangement = Arrangement.Bottom,
                horizontalAlignment = Alignment.Start
            ) {
                // TODO space nicer
                Text(
                    modifier = Modifier.weight(1f),
                    text = recipe.title,
                    style = MaterialTheme.typography.titleSmall
                )
                recipe.description?.let {
                    Text(
                        text = recipe.description,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.inverseOnSurface,
                        maxLines = 2
                    )
                }
                ItemTagList(recipe.tags)
            }
            IconButton(
                onClick = {
                    recipe.updateFavouriteState()
                })
            {
                Icon(
                    painterResource(if (isFavourite) R.drawable.icon_favorite_filled else R.drawable.icon_favorite),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.inverseOnSurface
                )
            }
        }
    }
    Spacer(modifier = Modifier.height(Spacings.s))

}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ItemTagList(
    tagItems: List<String>
) {
    FlowRow {
        tagItems.filter { it != favouriteTag }.forEach { tag ->
            StaticChip(title = tag, modifier = Modifier.padding(end = Spacings.xs))
        }
    }
}

fun DrawScope.drawBackgroundWave(color: Color) {
    val rectWidth = size.width
    val rectHeight = size.height

    val path = Path()

    val waveLength = rectWidth * 1.1
    val waveAmplitude = rectHeight / 35

    for (i in 0 until rectWidth.toInt() step 4) {
        val x = i.toFloat()
        val y = rectHeight / 4.5 + waveAmplitude * cos(2 * PI * i / waveLength + (2 * PI) / 3)
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

/*
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ScreenPreview() {
    MiRaRecipesTheme {
        Cookbook(RecipesViewModel())
    }
}
*/
