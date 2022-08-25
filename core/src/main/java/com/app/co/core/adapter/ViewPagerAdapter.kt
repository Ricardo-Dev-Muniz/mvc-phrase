package com.app.co.core.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.app.co.core.R
import com.app.co.core.data.Page
import com.app.co.core.databinding.ItemAdapterBinding
import com.app.co.core.interfaces.SafeClick
import com.app.co.core.support_ext.launchImage
import com.app.co.core.support_ext.setColouredSpan

class ViewPagerAdapter(
    private val context: Context,
    private val mutable: MutableList<Page?>,
) : RecyclerView.Adapter<ViewPagerAdapter.ViewHolder>() {

    private var onClick: SafeClick? = null

    fun onClick(click: SafeClick) {
        onClick = click
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val itemBinding = ItemAdapterBinding.inflate(
            LayoutInflater.from(context), parent, false
        )
        return ViewHolder(itemBinding, context, onClick)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = mutable[position]
        item?.let { holder.bind(it) }
    }

    override fun getItemCount(): Int = mutable.size

    class ViewHolder(
        private val binding: ItemAdapterBinding,
        private val context: Context,
        private val onClick: SafeClick?,
    ) : RecyclerView.ViewHolder(binding.root) {

        private val color by lazy {
            ContextCompat.getColor(context, R.color.pink)
        }

        fun bind(data: Page) {
            launchImage(data.image, 200, context) {
                binding.ivNoImage.animate().alpha(0f).setDuration(200).withEndAction {
                    binding.ivNoImage.visibility = View.INVISIBLE
                    binding.ivImage.setImageBitmap(it)
                }
            }

            binding.textView.setColouredSpan(
                binding.textView.text.toString(), 0, 5, color
            )
            binding.tvCitation.text = data.citation
        }
    }
}