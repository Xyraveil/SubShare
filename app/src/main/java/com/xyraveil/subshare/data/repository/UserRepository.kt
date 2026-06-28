package com.xyraveil.subshare.data.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.xyraveil.subshare.domain.model.User

object UserRepository {

    private val db
        get() = FirebaseFirestore.getInstance()

    fun saveUser(
        user: User,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit
    ) {

        db.collection("users")
            .document(user.uid)
            .set(user)
            .addOnSuccessListener {
                onSuccess()
            }
            .addOnFailureListener {
                onFailure(it.message ?: "Unknown Error")
            }

    }

    fun getUser(
        uid: String,
        onSuccess: (User) -> Unit,
        onFailure: (String) -> Unit
    ){
        db.collection("users")
            .document(uid)
            .get()
            .addOnSuccessListener {
                val user = it.toObject(User::class.java)

                if(user !=null)
                {
                    onSuccess(user)
                }
                else {
                    onFailure("User not found")
                }

            }
            .addOnFailureListener { onFailure(it.message ?: "Unknown Error") }
    }
}