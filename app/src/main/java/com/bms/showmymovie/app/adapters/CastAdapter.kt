package com.bms.showmymovie.app.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bms.showmymovie.R
import com.bms.showmymovie.common.Utils
import com.bms.showmymovie.data.api.models.movieCreditDetails.Cast
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.textview.MaterialTextView

class CastAdapter (
    private val context: Context,
    private val cast:ArrayList<Cast>
) : RecyclerView.Adapter<CastAdapter.CastViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CastViewHolder {
        return CastViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.cast_item,
                parent,
                false
            ))
    }

    override fun onBindViewHolder(holder: CastViewHolder, position: Int) {
        cast[position].also {
            it.profile_path?.let { img ->
                val requestOptions = RequestOptions().placeholder(context.resources.getDrawable(R.drawable.ic_launcher_foreground))
                Glide.with(context)
                    .load(Utils.IMAGE_BASE_URL+Utils.CAST_PROF_IMG_SIZE+img)
                    .apply(requestOptions)
                    .into(holder.profileImg)
            }
            holder.character_name.text = it.character
            holder.og_name.text = it.name
        }
    }

    override fun getItemCount(): Int {
        return cast.size
    }

    class CastViewHolder (view : View) : RecyclerView.ViewHolder(view){
        val character_name : MaterialTextView = view.findViewById(R.id.character_name)
        val og_name : MaterialTextView = view.findViewById(R.id.original_name)
        val profileImg : ImageView = view.findViewById(R.id.imgPhoto)
    }
}