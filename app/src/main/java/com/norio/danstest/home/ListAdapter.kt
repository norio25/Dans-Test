package com.norio.danstest.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.norio.danstest.R

class ListAdapter(private var listItem: ArrayList<ListJobResponseItem>) :
    RecyclerView.Adapter<ListAdapter.ListViewHolder>() {

    private var onItemClickListener: OnItemClickListener? = null

    fun setOnItemClickListener(onItemClickListener: OnItemClickListener) {
        this.onItemClickListener = onItemClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val mView =
            LayoutInflater.from(parent.context).inflate(R.layout.recyclerview_job, parent, false)
        return ListViewHolder(mView)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bind(listItem[position])
    }

    override fun getItemCount(): Int = listItem.size

    inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: ListJobResponseItem) {
            val image: ImageView = itemView.findViewById(R.id.iv_job)
            val title: TextView = itemView.findViewById(R.id.tv_job_name)
            val office: TextView = itemView.findViewById(R.id.tv_job_office)
            val location: TextView = itemView.findViewById(R.id.tv_job_location)

            title.text = item.title
            office.text = item.company
            location.text = item.location

            Glide.with(itemView.context).load(item.company_logo).into(image)

            itemView.setOnClickListener {
                item.id?.let { it1 ->
                    onItemClickListener?.onItemClicked(
                        it1
                    )
                }
            }
        }
    }

    interface OnItemClickListener {
        fun onItemClicked(id: String)
    }
}