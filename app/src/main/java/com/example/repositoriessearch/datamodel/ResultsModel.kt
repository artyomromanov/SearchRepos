package com.example.repositoriessearch.datamodel

import com.google.gson.annotations.SerializedName

data class ResultsModel(

    @SerializedName("total_count")
    var totalCount: Int,

    @SerializedName("incomplete_results")
    val incompleteResults: Boolean,

    @SerializedName("items")
    val items: List<Item>

)