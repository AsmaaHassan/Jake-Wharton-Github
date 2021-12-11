package com.example.jakewhartongithub.data.internal.dao

import androidx.paging.PagingSource
import androidx.room.*
import com.example.jakewhartongithub.models.Repo
/**
 * Created by Asmaa Hassan
 */
@Dao
interface RepoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(list: List<Repo>)

    @Query("SELECT * FROM repo")
    fun getAllRepos(): PagingSource<Int, Repo>

    @Query("DELETE FROM repo")
    fun clearAll()
}