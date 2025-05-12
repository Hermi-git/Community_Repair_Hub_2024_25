package com.example.community_repair_hub.data.network.model

data class IssueResponse(
    val _id: String,
    val category: String? = null,
    val locations: Locations? = null,
    val description: String? = null,
    val issueDate: String? = null,
    val imageURL: String? = null,
    val status: String? = null,
    val createdAt: String? = null,
    val updatedAt: String? = null,
    val repairNotes: String? = null
)

data class Locations(
    val city: String? = null,
    val specificArea: String? = null
)

data class UpdateIssueRequest(
    val issueId: String,
    val description: String,
    val status: String,
    val repairNotes: String?
)