package ru.netology.nmedia.viewholder

import android.annotation.SuppressLint
import android.view.View
import android.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import ru.netology.nmedia.BuildConfig
import ru.netology.nmedia.R
import ru.netology.nmedia.databinding.CardPostBinding
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.listeners.OnInteractionListener
import ru.netology.nmedia.repository.PostRepositoryImpl
import ru.netology.nmedia.view.load
import ru.netology.nmedia.view.loadCircleCrop
import ru.netology.nmedia.view.loadCircleCropPlaceholder
import java.text.SimpleDateFormat
import java.util.*

class PostViewHolder(
    private val binding: CardPostBinding,
    private val onInteractionListener: OnInteractionListener,
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(post: Post) {
        binding.apply {
            author.text = post.author
            published.text = Date(post.published * 1000).toFormattedString()
            content.text = post.content
            val avatarUrl = "${BuildConfig.BASE_URL}/avatars/${post.authorAvatar}"
            avatar.loadCircleCrop(
                avatarUrl,
                R.drawable.ic_avatar_placeholder,
                R.drawable.ic_avatar_not_found
            )
            like.isChecked = post.likedByMe
            like.text = "${post.likes}"

            with(post.attachment) {
                if (this != null) {
                    attachment.apply {
                        visibility = View.VISIBLE
                        contentDescription = description
                        val imageUrl = "${BuildConfig.BASE_URL}/images/$url"
                        load(
                            imageUrl,
                            R.drawable.ic_attachment_placeholder,
                            R.drawable.ic_attachment_not_found
                        )
                    }
                } else attachment.visibility = View.GONE
            }

            menu.setOnClickListener {
                PopupMenu(it.context, it).apply {
                    inflate(R.menu.options_post)
                    setOnMenuItemClickListener { item ->
                        when (item.itemId) {
                            R.id.remove -> {
                                onInteractionListener.onRemove(post)
                                true
                            }
                            R.id.edit -> {
                                onInteractionListener.onEdit(post)
                                true
                            }
                            else -> false
                        }
                    }
                }.show()
            }

            like.setOnClickListener {
                onInteractionListener.onLike(post)
            }

            share.setOnClickListener {
                onInteractionListener.onShare(post)
            }
        }
    }

    fun bindPlaceholder(){
        binding.apply{
            author.text = "..."
            published.text = "..."
            content.text = "..."
            avatar.loadCircleCropPlaceholder(R.drawable.ic_avatar_placeholder)
        }
    }

    @SuppressLint("SimpleDateFormat")
    private fun Date.toFormattedString() = PostRepositoryImpl.context.getString(
        R.string.published_at,
        SimpleDateFormat("dd MMM yyyy").format(this),
        SimpleDateFormat("hh:mm:ss").format(this)
    )
}