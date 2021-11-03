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
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.movies_category_label.view.*
import org.koin.android.viewmodel.ext.android.sharedViewModel

class HomeFragment : Fragment() {
    private val topRatedAdapter = MoviesCategoryAdapter()
    private val popularAdapter = MoviesCategoryAdapter()
    private val favoritesAdapter = MoviesCategoryAdapter()
    private val viewModel : MainViewModel by sharedViewModel()

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

    private fun setAdapters(){
        label_top_rated.apply {
            category_label_recyclerview.adapter = topRatedAdapter
            category_label_title.text = "Top rated"
        }
        label_most_popular.apply {
            category_label_recyclerview.adapter = popularAdapter
            category_label_title.text = "Most popular"
        }

    }

    private fun setObservers(){
        viewModel.topRatedMovies.observe(viewLifecycleOwner, Observer {
            it ?: return@Observer
            topRatedAdapter.setMovieList(it)
        })

        viewModel.popularMovies.observe(viewLifecycleOwner, Observer {
            it ?: return@Observer
            popularAdapter.setMovieList(it)
        })

        viewModel.errorResponse.observe(viewLifecycleOwner, Observer {
            it ?: return@Observer
            Toast.makeText(context, it, Toast.LENGTH_LONG).show()
        })
    }

}