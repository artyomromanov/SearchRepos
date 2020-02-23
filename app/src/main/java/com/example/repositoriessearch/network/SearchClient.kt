package com.example.repositoriessearch.network

import com.example.repositoriessearch.ENDPOINT_SEARCH
import com.example.repositoriessearch.datamodel.ResultsModel
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchClient {

    @GET(ENDPOINT_SEARCH)
    fun getReposList(@Query("q") query : String) : Observable<ResultsModel>

}