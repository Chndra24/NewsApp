package com.cwb.newsapp.ui.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.cwb.newsapp.data.model.Article
import com.cwb.newsapp.databinding.ItemNewsBinding
import com.cwb.newsapp.ui.WebViewActivity
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.TimeZone

class NewsAdapter(private var articles: List<Article>) :
    RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {

    fun updateData(newArticles: List<Article>) {
        articles = newArticles
        notifyDataSetChanged()
    }

    // Keep the formatDate function inside the adapter as it's fine here.
    private fun formatDate(isoDate: String?): String {
        return try {
            val parser = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())
            parser.timeZone = TimeZone.getTimeZone("UTC")
            val date = parser.parse(isoDate ?: "")
            val formatter = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
            formatter.format(date!!)
        } catch (e: Exception) {
            isoDate ?: ""
        }
    }

    class NewsViewHolder(private val binding: ItemNewsBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(article: Article, formatDate: (String?) -> String) {
            binding.title.text = article.title
            binding.author.text = article.author
            binding.publishedAt.text = formatDate(article.publishedAt)
            Glide.with(binding.image.context).load(article.urlToImage).into(binding.image)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        NewsViewHolder(ItemNewsBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val article = articles[position]
        holder.bind(article, ::formatDate)

        // Set up the click listener to open WebViewActivity
        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context, WebViewActivity::class.java)
            intent.putExtra("URL", article.url)  // Pass the article URL to the WebViewActivity
            holder.itemView.context.startActivity(intent)
        }
    }

    override fun getItemCount() = articles.size
}


