package com.adl.ujianretrofit.adapter


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.adl.ujianretrofit.R
import com.adl.ujianretrofit.model.HistoryModel

import java.util.*

class TableRowAdapter(private var hisArrayList: ArrayList<HistoryModel>) :
    RecyclerView.Adapter<TableRowAdapter.ViewHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ViewHolder {
        val v: View = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.table_row_layout, viewGroup, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, i: Int) {
        viewHolder.tvtanggal.text = hisArrayList[i].tanggal
        viewHolder.tvmasuk.text = hisArrayList[i].masuk
        viewHolder.tvkeluar.text = hisArrayList[i].pulang
    }

    override fun getItemCount(): Int {
        return hisArrayList.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvtanggal: TextView = itemView.findViewById(R.id.tv_tanggal)
        val tvmasuk: TextView = itemView.findViewById(R.id.tv_masuk)
        val tvkeluar: TextView = itemView.findViewById(R.id.tv_keluar)


    }
}
