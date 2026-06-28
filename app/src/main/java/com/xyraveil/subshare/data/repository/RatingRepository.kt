package com.xyraveil.subshare.data.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.xyraveil.subshare.domain.model.Rating

object RatingRepository {

    private val db = FirebaseFirestore.getInstance()

    private val currentUser
        get() = FirebaseAuth.getInstance().currentUser

    fun submitRating(
        recipeId: String,
        rating: Int,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit
    ) {

        val user = currentUser

        if (user == null) {
            onFailure("User not logged in")
            return
        }

        val ratingObject = Rating(
            rating = rating
        )

        db.collection("recipes")
            .document(recipeId)
            .collection("ratings")
            .document(user.uid)
            .set(ratingObject)
            .addOnSuccessListener {
                updateAverageRating(
                    recipeId,

                    onSuccess = {
                        onSuccess()
                    },

                    onFailure = {
                        onFailure(it)
                    })
                onSuccess()
            }
            .addOnFailureListener {
                onFailure(it.message ?: "Unknown Error")
            }

    }

    fun getUserRating(
        recipeId: String,
        onSuccess: (Int) -> Unit,
        onFailure: (String) -> Unit
    ) {

        val user = currentUser

        if (user == null) {
            onFailure("User not logged in")
            return
        }

        db.collection("recipes")
            .document(recipeId)
            .collection("ratings")
            .document(user.uid)
            .get()
            .addOnSuccessListener {

                val rating =
                    it.toObject(Rating::class.java)

                onSuccess(rating?.rating ?: 0)

            }
            .addOnFailureListener {

                onFailure(it.message ?: "Unknown Error")

            }

    }

    private fun updateAverageRating(
        recipeId: String, onSuccess: () -> Unit,
        onFailure: (String) -> Unit
    ) {

        db.collection("recipes")
            .document(recipeId)
            .collection("ratings")
            .get()
            .addOnSuccessListener { snapshot ->

                val ratings = snapshot.toObjects(Rating::class.java)

                if (ratings.isEmpty()) {

                    db.collection("recipes")
                        .document(recipeId)
                        .update(
                            mapOf(
                                "rating" to 0.0,
                                "ratingCount" to 0
                            )
                        )
                        .addOnSuccessListener {
                            onSuccess()
                        }

                    return@addOnSuccessListener
                }

                val average =
                    ratings.map { it.rating }.average()

                db.collection("recipes")
                    .document(recipeId)
                    .update(
                        mapOf(
                            "rating" to average,
                            "ratingCount" to ratings.size
                        )
                    )
                    .addOnSuccessListener {
                        onSuccess()
                    }
                    .addOnFailureListener {
                        onFailure(it.message ?: "Unknown Error")
                    }

            }

    }

}