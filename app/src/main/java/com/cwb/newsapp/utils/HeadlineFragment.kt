package com.cwb.newsapp.utils

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.cwb.newsapp.data.model.Article
import com.cwb.newsapp.databinding.ItemHeadlineBinding
import com.cwb.newsapp.ui.WebViewActivity

class HeadlineFragment : Fragment() {

    private lateinit var article: Article

    companion object {
        private const val ARG_ARTICLE = "article"

        fun newInstance(article: Article): HeadlineFragment {
            val fragment = HeadlineFragment()
            val args = Bundle()
            args.putParcelable(ARG_ARTICLE, article)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = ItemHeadlineBinding.inflate(inflater, container, false)

        article = arguments?.getParcelable(ARG_ARTICLE) ?: return binding.root

        binding.title.text = article.title
        Glide.with(binding.image.context)
            .load(article.urlToImage)
            .centerCrop()
            .into(binding.image)

        // Set up a click listener on the headline
        binding.root.setOnClickListener {
            val intent = Intent(activity, WebViewActivity::class.java)
            intent.putExtra("URL", article.url)  // Pass the article URL to the WebViewActivity
            startActivity(intent)
        }

        return binding.root
    }
}
