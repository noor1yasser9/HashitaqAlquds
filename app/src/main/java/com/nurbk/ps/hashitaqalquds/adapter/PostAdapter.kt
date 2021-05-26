package com.nurbk.ps.hashitaqalquds.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.google.firebase.auth.FirebaseAuth
import com.nurbk.ps.hashitaqalquds.R
import com.nurbk.ps.hashitaqalquds.databinding.ItemPostImageBinding
import com.nurbk.ps.hashitaqalquds.model.Post
import com.nurbk.ps.hashitaqalquds.other.*
import javax.inject.Inject

class PostAdapter @Inject constructor(val glide: RequestManager) :
    RecyclerView.Adapter<PostAdapter.PostViewHolder>() {
    lateinit var onClick: OnListItemViewClickListener


    inner class PostViewHolder(val mBinding: ItemPostImageBinding) :
        RecyclerView.ViewHolder(mBinding.root) {

        fun onBind(post: Post) {
            mBinding.post = post
            with(mBinding) {

                generateColor(header.txtNameLetter, root.context)
                header.btnMore.isVisible = FirebaseAuth.getInstance().uid.toString() == post.userId
                action.commit.setOnClickListener { onClick.onClickItem(post, ACTION_COMMENT) }
                action.btnComment.setOnClickListener { onClick.onClickItem(post, ACTION_COMMENT) }
                action.like.setOnClickListener { onClick.onClickItem(post, ACTION_LIKE) }
                action.btnLike.setOnClickListener { onClick.onClickItem(post, ACTION_LIKE) }
                header.btnImage.setOnClickListener { onClick.onClickItem(post, ACTION_PROFILE) }
                header.txtName.setOnClickListener { onClick.onClickItem(post, ACTION_PROFILE) }
                header.btnMore.setOnClickListener { onClick.onClickItem(post, ACTION_MORE) }
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        return PostViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_post_image,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {

        holder.onBind(data[position])
    }

    override fun getItemCount() = data.size


    interface OnListItemViewClickListener {
        fun onClickItem(post: Post, type: Int)
    }

    val diffCallback = object : DiffUtil.ItemCallback<Post>() {
        override fun areItemsTheSame(oldItem: Post, newItem: Post): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }

        override fun areContentsTheSame(oldItem: Post, newItem: Post): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }
    }
    val differ = AsyncListDiffer(this, diffCallback)

    var data: List<Post>
        get() = differ.currentList
        set(value) = differ.submitList(value)


    fun getImage(context: Context, text: String, imageView: ImageView) {
        Glide.with(context)
            .load(text)
            .into(imageView)
    }
}