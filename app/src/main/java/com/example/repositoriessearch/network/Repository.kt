package com.example.repositoriessearch.network

import com.example.repositoriessearch.datamodel.ResultsModel
import io.reactivex.Observable

interface Repository {

    fun getData(query : String) : Observable<ResultsModel>

}