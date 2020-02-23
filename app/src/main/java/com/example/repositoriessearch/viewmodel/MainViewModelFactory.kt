package com.example.repositoriessearch.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.repositoriessearch.network.Repository

class MainViewModelFactory(private val repository : Repository) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {

        return MainActivityViewModel(repository) as T

    }
}