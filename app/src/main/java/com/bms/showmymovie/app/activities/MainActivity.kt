package com.bms.showmymovie.app.activities

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.recyclerview.widget.LinearLayoutManager
import com.bms.showmymovie.R
import com.bms.showmymovie.app.adapters.CastAdapter
import com.bms.showmymovie.app.viewmodels.MovieViewModel
import com.bms.showmymovie.common.Utils
import com.bms.showmymovie.data.api.models.movieCreditDetails.Cast
import com.bms.showmymovie.data.api.models.moviePrimaryDetails.MovieDetail
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.hori_rv_img_data_stub.*
import kotlinx.android.synthetic.main.img_data.view.*
import kotlinx.android.synthetic.main.title_desc_lay.view.*
import java.lang.StringBuilder

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val viewModel : MovieViewModel by viewModels()
    private lateinit var castAdapter: CastAdapter
    private lateinit var castList: ArrayList<Cast>

    override fun onCreate(savedInstanceState: Bundle?) {
        // Handle the splash screen transition.
        val splashScreen = installSplashScreen()

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        handleStatusBar()
        castList = ArrayList()
        castAdapter = CastAdapter(this,castList)

        fetchMovieDetails()
        fetchMovieCredits()

        bookTickets.setOnClickListener{
            val intent = Intent(this@MainActivity,BookTicketActivity::class.java)
            startActivity(intent)
        }
    }

    private fun fetchMovieDetails() {
        bookTickets.visibility = View.GONE
        anim_loader.visibility = View.VISIBLE
        viewModel.getFavMovieDetails(Utils.MY_FAV_MOVIE_ID)
        viewModel.details.observe(this){
            populateDetails(it)
            bookTickets.visibility = View.VISIBLE
            anim_loader.visibility = View.GONE
        }
        viewModel.error.observe(this){
            Toast.makeText(this@MainActivity,it,Toast.LENGTH_SHORT).show()
        }
    }

    private fun populateDetails(movieDetail: MovieDetail){
        movieDetail.backdrop_path?.let {
            Glide.with(this)
                .load(Utils.IMAGE_BASE_URL+Utils.BACKDROP_PROF_IMG_SIZE+it)
                .into(movie_photo)
        } ?: makeViewGone(movie_photo)

        movieDetail.title?.let {
            movie_title.text = it
        } ?: makeViewGone(movie_title)

        val fullDetails = StringBuilder()
        movieDetail.release_date?.let {
            fullDetails.append(it.split("-")[0])
            movieDetail.genres?.let { data ->
                fullDetails.append("  •  ")
                data.forEachIndexed { index, genre ->
                    if(index!=data.size-1){
                        fullDetails.append(genre.name+", ")
                    }else{
                        fullDetails.append(genre.name)
                    }
                }
            }
            movieDetail.runtime?.let { runtime->
                fullDetails.append("  •  ")
                fullDetails.append(convertToUiDuration(runtime))
            }
        } ?: movieDetail.genres?.let {
            it.forEachIndexed { index, genre ->
                if(index!=it.size-1){
                    fullDetails.append(genre.name+", ")
                }else{
                    fullDetails.append(genre.name)
                }
            }
            movieDetail.runtime?.let { runtime->
                fullDetails.append("  •  ")
                fullDetails.append(convertToUiDuration(runtime))
            }
        } ?: movieDetail.runtime?.let {
            fullDetails.append(convertToUiDuration(it))
        } ?: makeViewGone(release_genre_time)
        release_genre_time.text = fullDetails.toString()

        movieDetail.vote_average?.let {
            rating_average_lay.rating_img.setImageDrawable(resources.getDrawable(R.drawable.ic_round_star_24))
            rating_average_lay.rating_txt.text = String.format("%.1f",it)
        } ?: makeViewGone(rating_average_lay)

        movieDetail.vote_count?.let {
            rating_count_lay.rating_img.setImageDrawable(resources.getDrawable(R.drawable.ic_round_person_24))
            rating_count_lay.rating_txt.text = it.toString()
        } ?: makeViewGone(rating_count_lay)

        movieDetail.tagline?.let {
            tagline.text = it
        }?: makeViewGone(tagline)

        movieDetail.overview?.let {
            desc_lay.desc_title.text = "Plot summary :"
            desc_lay.desc.text = it
        } ?: makeViewGone(desc_lay)

        val allLanguages = StringBuilder()
        movieDetail.spoken_languages?.let {
            spoken_lang_lay.desc_title.text = "Spoken languages :"
            it.forEachIndexed { index, lang ->
                if(index!=it.size-1){
                    allLanguages.append(lang.name+", ")
                }else{
                    allLanguages.append(lang.name)
                }
            }
            spoken_lang_lay.desc.text = allLanguages
        } ?: makeViewGone(spoken_lang_lay)
    }

    private fun fetchMovieCredits() {
        castShimmer.startShimmer()
        viewModel.getMovieCredits(Utils.MY_FAV_MOVIE_ID)
        viewModel.credits.observe(this){
            it?.let {
                val list = it.cast.filter { od ->
                    od.order<14
                }
                castList.clear()
                castList.addAll(list)
                castAdapter.notifyDataSetChanged()
                if(castShimmer.isShimmerVisible){
                    castShimmer.stopShimmer()
                    castShimmer.visibility = View.INVISIBLE
                    castRvStub.inflate()
                    castRV.apply {
                        layoutManager = LinearLayoutManager(this@MainActivity,LinearLayoutManager.HORIZONTAL,false)
                        setHasFixedSize(true)
                        adapter = castAdapter
                    }
                }
            }
        }
        viewModel.creditserror.observe(this){
            cast_title.visibility = View.GONE
            castRvStub.visibility = View.GONE
            castShimmer.visibility = View.GONE
        }
    }

    private fun convertToUiDuration(runtime: Int): String {
        return String.format("%dh %dm", runtime/60, runtime%60)
    }

    private fun makeViewGone(view: View) {
        kotlin.run {
            view.visibility = View.GONE
        }
    }

    fun handleStatusBar(){
        //status bar handling
        supportActionBar?.hide()
        window?.decorView?.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
        window?.statusBarColor = Color.TRANSPARENT
    }
}