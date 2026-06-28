package com.xyraveil.subshare.domain.model


data class Recipe(

    val imageUrl: String = "",

    val recipeId: String = "",

    val title: String = "",

    val bread: String = "",

    val protein: String = "",

    val cheese: String = "",

    val vegetables: List<String> = emptyList(),

    val sauces: List<String> = emptyList(),

    val seasonings: List<String> = emptyList(),

    val toasted: Boolean = false,

    val footlong: Boolean = false,

    val notes: String = "",

    val creatorUid: String = "",

    val creatorUsername: String = "",

    val createdAt: Long = 0L,

    val veg : Boolean = true,

    val rating: Double = 0.0,

    val ratingCount: Int = 0,

)