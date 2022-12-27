package com.example.bce.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.example.bce.R

class StateSpinnerAdapter(context : Context,
                          _spinnerItemLayout : Int,
                          items : List<String> ) : BaseAdapter() {

    private val stateInflater : LayoutInflater = LayoutInflater.from(context)
    private val stateList : List<String> = items;
    private val spinnerItemLayout = _spinnerItemLayout

    override fun getCount(): Int {
        return stateList.size
    }

    override fun getItem(position: Int): Any {
        return stateList.get(position)
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int,
                         convertView: View?,
                         parent: ViewGroup?): View {

        val view = stateInflater.inflate(spinnerItemLayout,
            parent,
            false)

        val textView = view.findViewById<TextView>(R.id.spinnerCurrentState)
        textView.text = stateList[position]

        return view
    }
}