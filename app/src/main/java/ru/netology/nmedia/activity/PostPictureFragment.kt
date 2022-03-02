package ru.netology.nmedia.activity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import ru.netology.nmedia.BuildConfig
import ru.netology.nmedia.R
import ru.netology.nmedia.databinding.FragmentPostPictureBinding
import ru.netology.nmedia.view.load
import ru.netology.nmedia.viewmodel.PostViewModel

class PostPictureFragment : Fragment() {

    private var fragmentBinding: FragmentPostPictureBinding? = null

    private val viewModel: PostViewModel by viewModels(
        ownerProducer = ::requireParentFragment,
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentPostPictureBinding.inflate(inflater, container, false)
        fragmentBinding = binding

        binding.toolbar.setNavigationOnClickListener {
            findNavController().navigate(R.id.action_postPictureFragment_to_feedFragment)
        }

        with(viewModel.getAttachment()!!) {
            binding.attachment.apply {
                contentDescription = description
                val imageUrl = "${BuildConfig.BASE_URL}/media/$url"
                load(
                    imageUrl,
                    R.drawable.ic_attachment_placeholder,
                    R.drawable.ic_attachment_not_found
                )
            }
        }

        return binding.root
    }

    override fun onDestroyView() {
        fragmentBinding = null
        super.onDestroyView()
    }
}