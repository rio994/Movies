package com.levelup.movies.ui.home

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.levelup.movies.R
import com.levelup.movies.data.model.MovieItemResponse
import kotlinx.android.synthetic.main.movie_recycler_item.view.*

class MoviesCategoryAdapter : RecyclerView.Adapter<MoviesCategoryAdapter.ViewHolder>() {

    private var moviesList : List<MovieItemResponse> = listOf()
    private var context : Context? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.movie_recycler_item, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val current = moviesList[position]
        holder.apply {
            title.text = current.title
            context?.let {
                Glide.with(this.itemView)
                    .load(current.getImageUrl())
                    .centerCrop()
                    .placeholder(it.resources.getDrawable(R.drawable.movie_poster_placeholder,null))
                    .into(poster)
            }
        }
    }

    override fun getItemCount(): Int {
        return moviesList.size
    }

    fun setMovieList(moviesList : List<MovieItemResponse>){
        this.moviesList = moviesList
        notifyDataSetChanged()
    }

    inner class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        val title = itemView.item_title
        val poster = itemView.item_poster

    }

}