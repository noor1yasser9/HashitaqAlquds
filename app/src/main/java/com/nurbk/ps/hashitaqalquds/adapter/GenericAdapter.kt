package com.nurbk.ps.hashitaqalquds.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import kotlin.collections.ArrayList

class GenericAdapter<T>(
    @LayoutRes val layoutId: Int,
    var type: Int,
    val itemclick: OnListItemViewClickListener<T>
) :
    RecyclerView.Adapter<GenericAdapter.GenericViewHolder<T>>() {

    private var onAttach = true
    val data = ArrayList<T>()
    private var inflater: LayoutInflater? = null

    fun addItems(items: List<T>) {
        this.data.clear()
        this.data.addAll(items)
        notifyDataSetChanged()
    }

    val diffCallback = object : DiffUtil.ItemCallback<T>() {
        override fun areItemsTheSame(oldItem: T, newItem: T): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }

        override fun areContentsTheSame(oldItem: T, newItem: T): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }
    }

    val differ = AsyncListDiffer(this, diffCallback)

    fun submitList(list: List<T>) = differ.submitList(list)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenericViewHolder<T> {
        val layoutInflater = inflater ?: LayoutInflater.from(parent.context)
        val binding =
            DataBindingUtil.inflate<ViewDataBinding>(layoutInflater, layoutId, parent, false)
        return GenericViewHolder(binding)
    }

    override fun getItemCount(): Int = differ.currentList.size

    override fun onBindViewHolder(holder: GenericViewHolder<T>, position: Int) {
        val itemViewModel = differ.currentList[position]
        holder.bind(itemViewModel, type)
        holder.itemView.apply {

            setOnClickListener {
                itemclick.onClickItem(itemViewModel,1)
            }
        }
    }


    class GenericViewHolder<T>(private val binding: ViewDataBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(itemViewModel: T, F: Int) {
            binding.setVariable(F, itemViewModel)
            binding.executePendingBindings()
        }

    }


    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                onAttach = false
                super.onScrollStateChanged(recyclerView, newState)
            }
        })
        super.onAttachedToRecyclerView(recyclerView)
    }

    interface OnListItemViewClickListener<T> {
        fun onClickItem(itemViewModel: T, type: Int)
    }

}