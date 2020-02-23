package com.example.repositoriessearch

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.repositoriessearch.datamodel.Item
import com.example.repositoriessearch.datamodel.ResultsModel
import com.example.repositoriessearch.network.Repository
import com.example.repositoriessearch.viewmodel.MainActivityViewModel
import io.reactivex.Observable
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class MainViewModelTest {

    @Rule
    @JvmField
    val testSchedulerRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var repository: Repository

    private lateinit var viewModel: MainActivityViewModel

    private lateinit var model : ResultsModel

    @Mock
    private lateinit var resultsObserver: androidx.lifecycle.Observer<ResultsModel>

    @Before
    fun setup() {

        viewModel = MainActivityViewModel(repository)

        viewModel.getMutableLiveData().observeForever(resultsObserver)

        val itemsList = emptyList<Item>()

        model = ResultsModel(3, false, itemsList)

    }

    @Test
    fun `retrieveSearchResults() call returns successful type`(){

        val query = "tetris"

        val itemsList = emptyList<Item>()

        //Given
        Mockito.`when`(repository.getData(query)).thenReturn(Observable.just(ResultsModel(3, false, itemsList)))

        //When
        viewModel.retrieveSearchResults(query)

        //Then
        Assert.assertTrue(viewModel.getMutableLiveData().value is ResultsModel)

    }

    @Test
    fun `retrieveSearchResults() invokes onChanged() call of the LiveData`(){

        val query = "tetris"

        //Given
        Mockito.`when`(repository.getData(query)).thenReturn(Observable.just(model))

        //When
        viewModel.retrieveSearchResults(query)

        //Then
        Mockito.verify(resultsObserver).onChanged(model)

    }
}