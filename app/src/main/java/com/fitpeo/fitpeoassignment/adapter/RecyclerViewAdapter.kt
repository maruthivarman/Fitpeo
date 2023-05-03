package com.fitpeo.fitpeoassignment.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.fitpeo.fitpeoassignment.R
import com.fitpeo.fitpeoassignment.model.Album
import com.squareup.picasso.Picasso


class RecyclerViewAdapter(dataList: List<Album>) :
    RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>() {

    private var list = dataList

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val name: TextView = view.findViewById(R.id.textTitle)
        val img: ImageView = view.findViewById(R.id.img)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.recycler_view_item, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = list[position]

        holder.name.text = data.title

        Picasso.get()
            .load(data.url)
            .into(holder.img)
    }

    override fun getItemCount(): Int {
        return list.size
    }
}