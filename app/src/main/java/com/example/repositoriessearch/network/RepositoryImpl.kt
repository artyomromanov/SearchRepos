package com.example.repositoriessearch.network

import com.example.repositoriessearch.retrofit.RetrofitClientInstance
import com.example.repositoriessearch.datamodel.ResultsModel
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class RepositoryImpl : Repository {

    override fun getData(query : String): Observable<ResultsModel>{

        return RetrofitClientInstance.getRetrofitClient()   //Creates and returns Retrofit client with class RepositoriesClient
            .getReposList(query)   //GET annotated API call, @Query(query : String), returns Observable<ResultsModel>
            .subscribeOn(Schedulers.io()) //Asynchronous call
            .observeOn(AndroidSchedulers.mainThread()) //Synchronous observation

    }
}