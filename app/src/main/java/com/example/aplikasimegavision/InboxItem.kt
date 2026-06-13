package com.example.aplikasimegavision

data class InboxItem(
    val title: String,
    val date: String,
    val description: String,
    val isUnread: Boolean = true
)