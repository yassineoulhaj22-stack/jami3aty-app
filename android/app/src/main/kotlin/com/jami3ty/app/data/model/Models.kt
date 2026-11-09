package com.jami3ty.app.data.model

data class Student(
    val id: String = "",
    val name: String = "",
    val email: String = "",
    val university: String = "",
    val createdAt: Long = 0L
)

data class Report(
    val id: String = "",
    val authorId: String = "",
    val authorName: String = "",
    val title: String = "",
    val body: String = "",
    val createdAt: Long = 0L
)
