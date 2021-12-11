package com.example.jakewhartongithub.data.internal.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.jakewhartongithub.data.RemoteKeys

/**
 * Created by Asmaa Hassan
 */
@Dao
interface RemoteKeysDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertRemote(list: List<RemoteKeys>)

    @Query("SELECT * FROM remoteKey WHERE repoId = :id")
    fun getRemoteKeys(id:String) : RemoteKeys

    @Query("DELETE FROM remoteKey")
    fun clearAll()
}