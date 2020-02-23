package com.example.repositoriessearch

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Editable
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.repositoriessearch.R.string.total_hits
import com.example.repositoriessearch.network.RepositoryImpl
import com.example.repositoriessearch.datamodel.ResultsModel
import com.example.repositoriessearch.recyclerview.ResultsAdapter
import com.example.repositoriessearch.viewmodel.MainActivityViewModel
import com.example.repositoriessearch.viewmodel.MainViewModelFactory
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.IllegalArgumentException

class MainActivity : AppCompatActivity() {

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val mainViewModel = ViewModelProvider(this, MainViewModelFactory(RepositoryImpl())).get(
            MainActivityViewModel::class.java)

        rv_results.layoutManager = LinearLayoutManager(this)

        et_query.addTextChangedListener { text ->

            //Check if query field is blank

            if (!text.isNullOrBlank()) {

                //Check if query entered is already on screen(in case activity was restarted) and initialize list from existing data

                if(text.toString() == mainViewModel.getEnteredQuery()){

                    //println("equals")

                    initializeListAndHitCount(mainViewModel.getMutableLiveData().value ?: throw IllegalArgumentException())

                } else {

                    //println("not equals")

                    mainViewModel.apply {

                        //Make the call with passed query : String if not done already

                        retrieveSearchResults(text.toString())

                        //Observe Results Changes

                        getMutableLiveData().observe(this@MainActivity, Observer<ResultsModel> {

                                t ->

                           initializeListAndHitCount(t)

                        })

                        //Observe Errors

                        getMutableLiveDataError().observe(this@MainActivity, Observer<Throwable> {

                                error ->

                            showError(this, error.message, text.toString())

                        })

                        //get status of the call - loading or loaded(Boolean)

                        getProgressStatus().observe(this@MainActivity, Observer<Boolean> {

                                show -> showProgress(show)

                        })
                    }
                }

                //if query field is empty - hide the recyclerview and reset hit count, and clear the compositeDisposable

            } else {

                mainViewModel.clearData()
                showProgress()
                rv_results.visibility = GONE
                tv_hits.visibility = GONE

            }

        }
    }

    private fun showError(viewModel : MainActivityViewModel, message: String?, query : String) {

        error_container.visibility = VISIBLE

        error_container.setOnClickListener {

            viewModel.retrieveSearchResults(query)

        }
        println(message)

        tv_error.text = "${message}\n${getString(R.string.txt_retry)}"

        tv_hits.visibility = GONE

    }

    private fun showProgress(show : Boolean = false){

        if(show){

            error_container.visibility = GONE
            pb_progress.visibility = VISIBLE
            tv_hits.visibility = GONE
            rv_results.visibility = GONE

        } else {

            error_container.visibility = GONE
            pb_progress.visibility = GONE
            tv_hits.visibility = VISIBLE
            rv_results.visibility = VISIBLE

        }

    }
    private fun initializeListAndHitCount(results : ResultsModel) {

        val resultsAdapter = ResultsAdapter(results.items)

        rv_results.adapter = resultsAdapter

        tv_hits.text = "${getString(total_hits)} - ${results.totalCount}"

        tv_hits.visibility = VISIBLE

    }

}
