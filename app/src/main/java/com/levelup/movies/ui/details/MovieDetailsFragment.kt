package com.levelup.movies.ui.details

import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.levelup.movies.R
import com.levelup.movies.data.model.MovieItemResponse
import com.levelup.movies.ui.home.MovieCategoryAdapterListener
import com.levelup.movies.ui.home.MoviesCategoryAdapter
import com.levelup.movies.ui.main.MainViewModel
import com.levelup.movies.util.MovieCategory
import com.levelup.movies.util.setDialog
import com.levelup.movies.util.loadWithGlide
import kotlinx.android.synthetic.main.fragment_movie_details.*
import kotlinx.android.synthetic.main.movies_category_label.*
import org.koin.android.viewmodel.ext.android.sharedViewModel

class MovieDetailsFragment(private val movieId : Int, private val movieCategory: MovieCategory) : Fragment(), MovieCategoryAdapterListener {

    private val viewModel : MainViewModel by sharedViewModel()
    private val adapter = MoviesCategoryAdapter(this, MovieCategory.SIMILAR)
    private var isMovieFavorite = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_movie_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.singleMovie.observe(viewLifecycleOwner, Observer {
            it ?: return@Observer
            setMovie(it)
        })

        viewModel.similarMovies.observe(viewLifecycleOwner, Observer {
            it ?: return@Observer
            adapter.setMovieList(it)
        })

    }


    override fun onResume() {
        super.onResume()
        setAdapter()
        val movie = viewModel.getMovieFromCategoryById(movieId, movieCategory)
        movie?.let { setMovie(it) }
    }

    private fun setMovie(movie: MovieItemResponse) {
        viewModel.getSimilarMovies(movie.id)
        context.loadWithGlide(movie_details_poster, movie.getPosterUrl())
        movie_details_title.text = movie.title
        movie_details_genre.text = viewModel.getGenresByIds(movie)
        movie_details_release_date.text = movie.releaseDate.substring(0,4)
        movie_details_rating.text = movie.rating.toString().substring(0,3)
        movie_details_overview.text = movie.overview
        movie_details_overview.movementMethod = ScrollingMovementMethod()
        movie_details_button_favorites.apply {
            isMovieFavorite = viewModel.checkIsFavorite(movie)
            context?.loadWithGlide(
                view = this,
                resourceId = if(isMovieFavorite) R.drawable.ic_star_gold_24 else R.drawable.ic_star_gray_24
            )
            setOnClickListener {
                isMovieFavorite = !isMovieFavorite
                viewModel.setFavorite(movie, isMovieFavorite)
                context?.loadWithGlide(
                    view = this,
                    resourceId = if(isMovieFavorite) R.drawable.ic_star_gold_24 else R.drawable.ic_star_gray_24
                )
            }
        }

    }

    override fun onMovieCategoryItemClick(id: Int, movieCategory: MovieCategory) {
        val movie = viewModel.getMovieFromCategoryById(id, movieCategory) ?: return
        context?.setDialog(
            fragmentActivity = activity,
            movie = movie,
            movieCategory = movieCategory,
            genres = viewModel.getGenresByIds(movie),
            isFavorite = viewModel.checkIsFavorite(movie),
            setFavorite = {viewModel.setFavorite(movie, it)}
        )
    }

    private fun setAdapter(){
        category_label_recyclerview.adapter = adapter
        category_label_title.text = getString(R.string.similar_movies)
    }

}