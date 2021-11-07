package com.levelup.movies.util

import android.content.Context
import android.telecom.Call
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.levelup.movies.R
import com.levelup.movies.data.model.MovieItemResponse
import com.levelup.movies.ui.details.MovieDetailsFragment
import com.levelup.movies.ui.home.HomeFragment
import kotlinx.android.synthetic.main.movie_custom_dialog.*

class Extensions

fun FragmentManager.navigateTo(fragment: Fragment) {

    val transaction = beginTransaction()
    transaction.replace(R.id.fragment_container, fragment)
    transaction.addToBackStack(
        when (fragment) {
            is HomeFragment -> "HOME"
            is MovieDetailsFragment -> "DETAILS"
            else -> ""
        }
    )
    transaction.commit()
}

fun Context?.loadWithGlide(view: ImageView, url: String? = null, resourceId: Int? = null) {
    this ?: return
    Glide.with(this)
        .load(url ?: resourceId)
        .centerCrop()
        .placeholder(
            ResourcesCompat.getDrawable(
                resources,
                R.drawable.movie_poster_placeholder,
                null
            )
        )
        .into(view)
}

fun List<String>.combineStrings(): String {
    var result = ""
    forEachIndexed { index, s ->
        result += when (index) {
            0 -> s
            else -> ", $s"
        }
    }
    return result
}

fun Context.setDialog(
    fragmentActivity: FragmentActivity?,
    movie: MovieItemResponse,
    movieCategory: MovieCategory,
    genres: String,
    isFavorite: Boolean,
    setFavorite: (favorite: Boolean) -> Unit
) {
    var favoriteStatus = isFavorite
    BottomSheetDialog(this).apply {
        setContentView(R.layout.movie_custom_dialog)
        movie_dialog_title.text = movie.title
        movie_dialog_genre.text = genres
        movie_dialog_release_date.text = movie.releaseDate.substring(0, 4)
        movie_dialog_rating.text = movie.rating.toString().substring(0, 3)
        context.loadWithGlide(movie_dialog_poster, movie.getPosterUrl())
        show()
        context.loadWithGlide(
            view = dialog_button_favorites,
            resourceId = if (isFavorite) R.drawable.ic_star_gold_24 else R.drawable.ic_star_gray_24
        )

        movie_dialog_close_icon.setOnClickListener {
            dismiss()
        }
        movie_dialog_container.setOnClickListener {
            dismiss()
            fragmentActivity?.supportFragmentManager?.navigateTo(
                MovieDetailsFragment(
                    movie.id,
                    movieCategory
                )
            )
        }

        dialog_button_favorites.setOnClickListener {
            favoriteStatus = !favoriteStatus
            context.loadWithGlide(
                view = dialog_button_favorites,
                resourceId = if (favoriteStatus) R.drawable.ic_star_gold_24 else R.drawable.ic_star_gray_24
            )
            setFavorite.invoke(favoriteStatus)
        }
    }

}