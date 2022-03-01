package ru.netology.nmedia.model

import androidx.paging.PagingData
import ru.netology.nmedia.dto.Post

data class FeedModel(
    val posts: PagingData<Post> = PagingData.empty(),
    val empty: Boolean = false,
)
