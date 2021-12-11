package com.example.jakewhartongithub.data

import androidx.room.Entity
import androidx.room.PrimaryKey
/**
 * Created by Asmaa Hassan
 */

@Entity(tableName = "remoteKey")
data class RemoteKeys(
    @PrimaryKey
    val repoId:String,
    val prevKey:Int?,
    val nextKey:Int?
)