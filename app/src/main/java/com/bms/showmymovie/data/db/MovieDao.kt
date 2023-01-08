package com.bms.showmymovie.data.db

import androidx.room.*
import com.bms.showmymovie.data.db.entities.CastBrief
import com.bms.showmymovie.data.db.entities.MovieBrief

@Dao
interface MovieDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovieDetails(movieBrief: MovieBrief)

    @Query("SELECT * FROM tbl_movie_details WHERE id = :movie")
    fun getMovieDetails(movie: Int): MovieBrief?

    @Delete
    suspend fun deleteMovieDetails(movieBrief: MovieBrief)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovieCredits(castBrief: CastBrief)

    @Query("SELECT * FROM tbl_movie_credits WHERE id = :movie")
    fun getMovieCredits(movie: Int): CastBrief?

    @Delete
    suspend fun deleteMovieCredits(castBrief: CastBrief)
}