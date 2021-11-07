package com.levelup.movies.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import com.levelup.movies.R
import com.levelup.movies.ui.main.MainViewModel
import com.levelup.movies.util.MovieCategory
import com.levelup.movies.util.MovieCategory.*
import com.levelup.movies.util.setDialog
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.movie_custom_dialog.*
import kotlinx.android.synthetic.main.movies_category_label.view.*
import org.koin.android.viewmodel.ext.android.sharedViewModel

class HomeFragment : Fragment(), MovieCategoryAdapterListener {
    private val topRatedAdapter = MoviesCategoryAdapter(this, TOP_RATED)
    private val popularAdapter = MoviesCategoryAdapter(this, POPULAR)
    private val favoritesAdapter = MoviesCategoryAdapter(this, FAVORITES)
    private val viewModel: MainViewModel by sharedViewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setAdapters()
        setObservers()
        viewModel.getTopRatedMovies()
        viewModel.getPopularMovies()
    }

    private fun setAdapters() {
        label_top_rated.apply {
            category_label_recyclerview.adapter = topRatedAdapter
            category_label_title.text = context.getString(R.string.label_title_top_rated)
        }
        label_most_popular.apply {
            category_label_recyclerview.adapter = popularAdapter
            category_label_title.text = context.getString(R.string.label_title_most_popular)
        }
        label_favorites.apply {
            category_label_recyclerview.adapter = favoritesAdapter
            category_label_title.text = context.getString(R.string.favorites)
        }

    }

    private fun setObservers() {
        viewModel.topRatedMovies.observe(viewLifecycleOwner, Observer {
            it ?: return@Observer
            topRatedAdapter.setMovieList(it)
        })

        viewModel.popularMovies.observe(viewLifecycleOwner, Observer {
            it ?: return@Observer
            popularAdapter.setMovieList(it)
        })

        viewModel.favoriteMovies.observe(viewLifecycleOwner, Observer {
            it ?: return@Observer
            favoritesAdapter.setMovieList(it)
        })

        viewModel.errorResponse.observe(viewLifecycleOwner, Observer {
            it ?: return@Observer
            Toast.makeText(context, it, Toast.LENGTH_LONG).show()
        })
    }

    override fun onMovieCategoryItemClick(id: Int, movieCategory: MovieCategory) {
        val movie = viewModel.getMovieFromCategoryById(id, movieCategory) ?: return
        context?.setDialog(
            fragmentActivity = activity,
            movie = movie,
            movieCategory = movieCategory,
            genres = viewModel.getGenresByIds(movie),
            isFavorite = viewModel.checkIsFavorite(movie),
            setFavorite = { isFavorite -> viewModel.setFavorite(movie, isFavorite) })
    }

}