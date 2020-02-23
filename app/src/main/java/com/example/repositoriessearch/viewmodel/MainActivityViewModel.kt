package com.example.repositoriessearch.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.repositoriessearch.datamodel.ResultsModel
import com.example.repositoriessearch.network.Repository
import io.reactivex.disposables.CompositeDisposable

class MainActivityViewModel(private val repository: Repository) : ViewModel() {

    private val itemsList = MutableLiveData<ResultsModel>()

    private val errorReceived  = MutableLiveData<Throwable>()

    private val progressStatus = MutableLiveData<Boolean>()

    private val compositeDisposable = CompositeDisposable()

    private var enteredQuery = ""

    fun getMutableLiveData() : MutableLiveData<ResultsModel> {

        return itemsList

    }

    fun getMutableLiveDataError() : MutableLiveData<Throwable> {
        return errorReceived

    }
    fun getProgressStatus() : MutableLiveData<Boolean> {

        return progressStatus

    }

    fun getEnteredQuery() : String{

        return enteredQuery

    }

    fun retrieveSearchResults(query : String) {

        enteredQuery = query

        progressStatus.value = true

        compositeDisposable.add(

            repository
                .getData(query)
                .subscribe({

                    itemsList.value = it
                    progressStatus.value = false


                }) { error ->

                    errorReceived.value = error.cause ?: Throwable("null")
                    progressStatus.value = false

                }

        )}

    override fun onCleared() {

        clearData()
        super.onCleared()

    }

    fun clearData(){

        itemsList.value?.totalCount = 0

        enteredQuery = ""

        compositeDisposable.clear()

    }

}