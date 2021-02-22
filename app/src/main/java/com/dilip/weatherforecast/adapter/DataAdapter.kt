package com.dilip.weatherforecast.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.dilip.weatherforecast.MainActivity
import com.dilip.weatherforecast.R

class DataAdapter(
        private val mDataset: ArrayList<String>,
        internal var recyclerViewItemClickListener: MainActivity
) : RecyclerView.Adapter<DataAdapter.LocationViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, i: Int): LocationViewHolder {

        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_location, parent, false)

        return LocationViewHolder(v)

    }

    override fun onBindViewHolder(fruitViewHolder: LocationViewHolder, i: Int) {
        fruitViewHolder.mTextView.text = mDataset[i]


    }

    override fun getItemCount(): Int {
        return mDataset.size
    }


    inner class LocationViewHolder(v: View) : RecyclerView.ViewHolder(v), View.OnClickListener {

        var mTextView: TextView

        init {
            mTextView = v.findViewById(R.id.location)
            v.setOnClickListener(this)
        }

        override fun onClick(v: View) {
            //recyclerViewItemClickListener.clickOnItem(mDataset[this.adapterPosition])

        }
    }

    interface RecyclerViewItemClickListener {
        fun clickOnItem(data: String)
    }
}