package joseph.example.myapplication.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import joseph.example.myapplication.R
import joseph.example.myapplication.modals.NewsData

class NewsAdapter(private val newsList: ArrayList<NewsData>):
    RecyclerView.Adapter<NewsAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsAdapter.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.news_card, parent, false)
        return NewsAdapter.ViewHolder(v) //returns news adapter
    }
    override fun onBindViewHolder(holder: NewsAdapter.ViewHolder, position: Int) {
        holder.bindItems(newsList[position]) //binds the view to viewholder
    }
    override fun getItemCount(): Int {
       return newsList.size //get the item size
    }
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindItems(news: NewsData) {
            val newsIcon = itemView.findViewById(R.id.news_icon) as ImageView
            val newsTitle = itemView.findViewById(R.id.news_title) as TextView
            val newsDescription = itemView.findViewById(R.id.news_description) as TextView
            newsTitle.text = news.newsTitle //set the text of news title
            newsDescription.text = news.newsDescription //set the description of news
            newsIcon.setImageResource(news.newsPhoto) // set the image of news

        }
    }

}