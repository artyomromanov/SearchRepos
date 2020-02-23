package com.example.repositoriessearch.recyclerview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.repositoriessearch.R
import com.example.repositoriessearch.datamodel.Item
import kotlinx.android.synthetic.main.item_resultitem.view.*

class ResultsAdapter(val resultsList: List<Item>) :
    RecyclerView.Adapter<ResultsAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val v: View =
            LayoutInflater.from(parent.context).inflate(R.layout.item_resultitem, parent, false)

        return ViewHolder(v)

    }

    override fun getItemCount(): Int {

        return resultsList.size

    }

    override fun onBindViewHolder(holder : ViewHolder, position: Int) {

        holder.bind(position)

    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(position: Int) {

            itemView.tv_name.text = resultsList[position].name

        }

    }

}