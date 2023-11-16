package de.miRa.mirarecipes.recipes

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class RecipesViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(
        RecipesListUIState(
            recipesItems = generateTestRecipeList(),
            filterTags = listOf(favouriteTag, "Essen", "Nudeln"),
            selectedTags = listOf()
        )
    )

    val uiState: StateFlow<RecipesListUIState>
        get() = _uiState

    private fun generateTestRecipeList(): List<Recipe> {
        return mutableStateListOf(
            Recipe(
                title = "Pizza",
                tags = listOf("Essen")
            ),
            Recipe(
                title = "Suppe",
                tags = listOf("Essen", "Nudeln")
            ),
            Recipe(
                title = "Nudeln",
                tags = listOf("Nudeln")
            ),
            Recipe(
                title = "Nudelsalat",
                tags = listOf("Nudeln", favouriteTag)
            )
        )
    }

    fun onTagSelected(tag: String) {
        _uiState.update {
            it.copy(
                selectedTags = if (it.selectedTags.contains(tag)) {
                    it.selectedTags.minus(tag)
                } else {
                    it.selectedTags.plus(tag)
                }
            )
        }
        Log.d("xxx", "onTagSelected Tag $tag")


    }

    fun removeAllSelectedTags() {
        _uiState.update {
            it.copy(
                selectedTags = listOf()
            )
        }
    }

}

data class RecipesListUIState(
    val recipesItems: List<Recipe>,
    val filterTags: List<String>,
    val selectedTags: List<String>
)
