package com.xyraveil.subshare.data.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.xyraveil.subshare.domain.model.Favourite

object FavouriteRepository {

    private val db = FirebaseFirestore.getInstance()

    private val currentUser
        get() = FirebaseAuth.getInstance().currentUser


    fun addFavourite(
        recipeId: String,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit
    ) {

        val user = currentUser

        if (user == null) {
            onFailure("User not logged in")
            return
        }

        val favourite = Favourite(
            recipeId = recipeId,
            createdAt = System.currentTimeMillis()
        )

        db.collection("users")
            .document(user.uid)
            .collection("favorites")
            .document(recipeId)
            .set(favourite)
            .addOnSuccessListener {
                onSuccess()
            }
            .addOnFailureListener {
                onFailure(it.message ?: "Unknown Error")
            }
    }


    fun removeFavourite(
        recipeId: String,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit
    ) {

        val user = currentUser

        if (user == null) {
            onFailure("User not logged in")
            return
        }

        db.collection("users")
            .document(user.uid)
            .collection("favorites")
            .document(recipeId)
            .delete()
            .addOnSuccessListener {
                onSuccess()
            }
            .addOnFailureListener {
                onFailure(it.message ?: "Unknown Error")
            }
    }

    fun isFavourite(
        recipeId: String,
        onResult: (Boolean) -> Unit
    ) {

        val user = currentUser

        if (user == null) {
            onResult(false)
            return
        }

        db.collection("users")
            .document(user.uid)
            .collection("favorites")
            .document(recipeId)
            .get()
            .addOnSuccessListener { document ->
                onResult(document.exists())
            }
            .addOnFailureListener {
                onResult(false)
            }
    }

    fun getFavourites(
        onSuccess: (List<String>) -> Unit,
        onFailure: (String) -> Unit
    ) {

        val user = currentUser

        if (user == null) {
            onFailure("User not logged in")
            return
        }

        db.collection("users")
            .document(user.uid)
            .collection("favorites")
            .get()
            .addOnSuccessListener { snapshot ->

                val favouriteIds = snapshot.documents.mapNotNull {

                    it.getString("recipeId")

                }
                onSuccess(favouriteIds)

            }
            .addOnFailureListener {

                onFailure(it.message ?: "Unknown Error")

            }

    }



}