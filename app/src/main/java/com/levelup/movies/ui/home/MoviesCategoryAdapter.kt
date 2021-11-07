package com.levelup.movies.ui.home

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.levelup.movies.R
import com.levelup.movies.data.model.MovieItemResponse
import com.levelup.movies.util.MovieCategory
import com.levelup.movies.util.loadWithGlide
import kotlinx.android.synthetic.main.movie_recycler_item.view.*

class MoviesCategoryAdapter(private val listener : MovieCategoryAdapterListener,
                            private val movieCategory: MovieCategory) : RecyclerView.Adapter<MoviesCategoryAdapter.ViewHolder>() {

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
            context.loadWithGlide(holder.backdrop, current.getBackdropUrl())
            bodyContainer.setOnClickListener {
                listener.onMovieCategoryItemClick(current.id, movieCategory)
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
        val backdrop = itemView.item_backdrop
        val bodyContainer = itemView.movie_recycler_item_container

    }

}