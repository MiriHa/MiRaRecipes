package de.miRa.mirarecipes.recipes

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class RecipesViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(
        RecipesListUIState(
            recipesItems = generateTestRecipeList(),
            filterTags = listOf(
                FilterTag("Essen", false), FilterTag("Nudeln", false)
            )
        )
    )

    val uiState: StateFlow<RecipesListUIState>
        get() = _uiState

    private fun generateTestRecipeList(): List<Recipe> {
        return mutableStateListOf(
            Recipe(
                title = "Pizza",
                tags = listOf(FilterTag("Essen", false))
            ),
            Recipe(
                title = "Suppe",
                tags = listOf(FilterTag("Essen", false), FilterTag("Nudeln", false))
            ),
            Recipe(
                title = "Nudeln",
                tags = listOf(FilterTag("Nudeln", false))
            )
        )
    }

    fun onTagSelected(tag: FilterTag) {
        tag.isSelected = !tag.isSelected
    }

    fun removeAllSelectedTags() {
        _uiState.value.filterTags.forEach { it.isSelected = false }
    }

}

data class RecipesListUIState(
    val recipesItems: List<Recipe>,
    val filterTags: List<FilterTag>
)


data class FilterTag(
    val title: String,
    var isSelected: Boolean
)