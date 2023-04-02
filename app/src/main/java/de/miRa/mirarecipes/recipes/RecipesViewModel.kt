package de.miRa.mirarecipes.recipes

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel

class RecipesViewModel : ViewModel() {

    var selectedFilterTags = mutableStateListOf<String>()
        private set

    val items = mutableStateListOf(
        Recipe(
            title = "Pizza",
            tags = listOf("Essen")
        ),
        Recipe(
            title = "Suppe",
            tags = listOf("Essen, Nudeln")
        ),
        Recipe(
            title = "Nudeln",
            tags = listOf("Nudeln")
        )
    )

    fun onTagSelected(tag: String, isSelected: Boolean) {
        if (isSelected) {
            selectedFilterTags.add(tag)
        } else if (selectedFilterTags.contains(tag)) {
            selectedFilterTags.remove(tag)
        }
        Log.d("xxx", "onTagSelected Tag $tag $selectedFilterTags")
    }

    fun removeAllSelectedTags(){
        selectedFilterTags = mutableStateListOf()
    }

}