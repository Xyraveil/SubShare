package com.xyraveil.subshare.data.repository

import com.google.firebase.auth.FirebaseAuth
import com.xyraveil.subshare.domain.model.User

object AuthRepository {

    private val auth = FirebaseAuth.getInstance()

    fun register(
        username: String,
        email: String,
        password: String,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit
    ) {

        auth.createUserWithEmailAndPassword(email, password)
            .addOnSuccessListener { result ->

                val firebaseUser = result.user!!

                val user = User(
                    uid = firebaseUser.uid,
                    username = username,
                    email = email
                )

                UserRepository.saveUser(
                    user = user,
                    onSuccess = onSuccess,
                    onFailure = onFailure
                )

            }
            .addOnFailureListener {
                onFailure(it.message ?: "Unknown Error")
            }
    }
    fun login(
        email: String,
        password: String,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit
    ) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                onSuccess()
            }
            .addOnFailureListener {
                onFailure(it.message ?: "Unknown Error")
            }
    }
    fun getCurrentUser() = auth.currentUser

    fun logout() {
        auth.signOut()
    }
}