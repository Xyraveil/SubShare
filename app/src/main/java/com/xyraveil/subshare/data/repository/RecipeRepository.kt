package com.xyraveil.subshare.data.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.xyraveil.subshare.domain.model.Recipe

object RecipeRepository {

    private val db
        get() = FirebaseFirestore.getInstance()

    fun uploadRecipe(
        recipe: Recipe,
        onSuccess: (String) -> Unit,
        onFailure: (String) -> Unit
    ) {

        db.collection("recipes")
            .document(recipe.recipeId)
            .set(recipe)
            .addOnSuccessListener {
                onSuccess(recipe.recipeId)
            }
            .addOnFailureListener {
                onFailure(it.message ?: "Unknown Error")
            }

    }
    fun deleteRecipe(
        recipeId: String,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit
    ) {

        db.collection("recipes")
            .document(recipeId)
            .delete()
            .addOnSuccessListener {

                onSuccess()

            }
            .addOnFailureListener {

                onFailure(it.message ?: "Unknown Error")

            }

    }
    fun getUserRecipes(
        userId: String,
        onSuccess: (List<Recipe>) -> Unit,
        onFailure: (String) -> Unit
    ) {

        db.collection("recipes")
            .whereEqualTo("creatorUid", userId)
          //  .orderBy("createdAt")
            .get()
            .addOnSuccessListener {

                onSuccess(it.toObjects(Recipe::class.java))

            }
            .addOnFailureListener {

                onFailure(it.message ?: "Unknown Error")

            }

    }
    fun getRecipe(
        recipeId: String,
        onSuccess: (Recipe) -> Unit,
        onFailure: (String) -> Unit
    ) {

        db.collection("recipes")
            .document(recipeId)
            .get()
            .addOnSuccessListener { document ->

                val recipe = document.toObject(Recipe::class.java)

                if (recipe != null) {
                    onSuccess(recipe)
                } else {
                    onFailure("Recipe not found")
                }

            }
            .addOnFailureListener {
                onFailure(it.message ?: "Unknown Error")
            }
    }
    fun getRecipes(
        onSuccess: (List<Recipe>) -> Unit,
        onFailure: (String) -> Unit
    ) {

        db.collection("recipes")
            .get()
            .addOnSuccessListener { snapshot ->

                val recipes = snapshot.toObjects(Recipe::class.java)

                onSuccess(recipes)

            }
            .addOnFailureListener {
                onFailure(it.message ?: "Unknown Error")
            }

    }
    fun getRecipesByIds(
        recipeIds: List<String>,
        onSuccess: (List<Recipe>) -> Unit,
        onFailure: (String) -> Unit
    ) {

        if (recipeIds.isEmpty()) {
            onSuccess(emptyList())
            return
        }

        val recipes = mutableListOf<Recipe>()
        var completed = 0

        recipeIds.forEach { id ->

            getRecipe(

                recipeId = id,

                onSuccess = { recipe ->

                    recipes.add(recipe)
                    completed++

                    if (completed == recipeIds.size) {
                        onSuccess(recipes)
                    }

                },

                onFailure = { error ->

                    completed++

                    if (completed == recipeIds.size) {
                        onSuccess(recipes)
                    }

                }

            )

        }

    }

}

