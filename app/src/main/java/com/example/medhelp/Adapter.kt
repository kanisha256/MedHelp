package com.example.medhelp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.util.ArrayList

class Adapter(var datalist: ArrayList<Model?>?) :
    RecyclerView.Adapter<Adapter.myviewholder>() {

    // Define a listener interface
    interface OnItemClickListener {
        fun onItemClick(item: Model?,fiopac:String,palata:String)
    }

    // Declare a listener variable
    private var listener: OnItemClickListener? = null

    // Set the listener
    fun setOnItemClickListener(listener: OnItemClickListener?) {
        this.listener = listener
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): myviewholder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.singlerow, parent, false)
        return myviewholder(view)
    }

    override fun onBindViewHolder(holder: myviewholder, position: Int) {
        holder.t1.setText("Пациент "+datalist!![position]!!.fiopac)
        holder.t3.setText("Палата "+datalist!![position]!!.palata)
    }

    override fun getItemCount(): Int {
        return datalist!!.size
    }

    inner class myviewholder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var t1: TextView
        var t3: TextView

        init {
            t1 = itemView.findViewById<TextView>(R.id.t1)
            t3 = itemView.findViewById<TextView>(R.id.t3)
            itemView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val item = datalist?.get(position)
                    listener?.onItemClick(datalist?.get(position), item!!.fiopac.toString(),item!!.palata.toString())
                }
            }
        }
    }
}