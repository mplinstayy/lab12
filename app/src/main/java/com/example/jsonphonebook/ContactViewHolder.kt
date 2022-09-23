package com.example.jsonphonebook

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ContactViewHolder (item: View): RecyclerView.ViewHolder(item) {
    var nameTextView: TextView = item.findViewById(R.id.tvName)
    var phoneTextView: TextView = item.findViewById(R.id.tvPhone)
}