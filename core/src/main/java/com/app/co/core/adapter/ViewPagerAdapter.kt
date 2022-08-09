package com.app.co.core.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.co.core.data.Page
import com.app.co.core.databinding.ItemAdapterBinding

class ViewPagerAdapter(
    private val context: Context,
    private val mutable: MutableList<Page?>,
) : RecyclerView.Adapter<ViewPagerAdapter.ViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val itemBinding = ItemAdapterBinding.inflate(
            LayoutInflater.from(context), parent, false
        )
        return ViewHolder(itemBinding, context)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        mutable[position]?.let { holder.bind(it) }
    }

    override fun getItemCount(): Int = mutable.size

    class ViewHolder(
        private val binding: ItemAdapterBinding,
        private val context: Context,
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data: Page) {
            binding.tvCitation.text = data.citation
        }
    }
}