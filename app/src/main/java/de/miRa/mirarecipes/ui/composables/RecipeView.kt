package de.miRa.mirarecipes.ui.composables

import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.google.accompanist.pager.*
import de.miRa.mirarecipes.R
import de.miRa.mirarecipes.recipes.Recipe

@OptIn(ExperimentalPagerApi::class)
@Composable
fun RecipeView(
    recipe: Recipe
) {

    val tabs = listOf(
        Tab(stringResource(id = R.string.recipeView_Ingredients), Icons.Default.Favorite),
        Tab(stringResource(id = R.string.recipeView_Instructions), Icons.Default.Settings)
    )

    val pagerState = rememberPagerState(pageCount = 2)

    Tabs(pagerState = pagerState, tabList = tabs)
    TabsContent(pagerState = pagerState, recipe = recipe)

}



@ExperimentalPagerApi
@Composable
fun TabsContent(pagerState: PagerState, recipe: Recipe) {
    HorizontalPager(state = pagerState) {
            page ->
        when (page) {
            0 -> IngredientsContent(recipe = recipe)
            1 -> InstructionsContent(recipe = recipe)
        }
    }
}

@Composable
fun IngredientsContent(recipe: Recipe){

}

@Composable
fun InstructionsContent(recipe: Recipe){

}

