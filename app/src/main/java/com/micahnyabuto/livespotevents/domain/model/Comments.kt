package com.micahnyabuto.livespotevents.domain.model

data class Comments(
    val id: String? = null,
    val postId: String,
    val userId: String,
    val content: String,
)