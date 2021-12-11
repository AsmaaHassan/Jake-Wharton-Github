package com.example.jakewhartongithub.data.internal

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.jakewhartongithub.data.RemoteKeys
import com.example.jakewhartongithub.data.internal.dao.RemoteKeysDao
import com.example.jakewhartongithub.data.internal.dao.RepoDao
import com.example.jakewhartongithub.models.Repo
/**
 * Created by Asmaa Hassan
 */
@TypeConverters(TypeConvertorClass::class)
@Database(entities = [Repo::class, RemoteKeys::class], version = 2, exportSchema = false)
abstract class RepoDatabase : RoomDatabase() {

     abstract val getRepoDao: RepoDao
    abstract val remoteKeyDao:RemoteKeysDao

    companion object {
        @Volatile
        private var INSTANCE: RepoDatabase? = null

        fun getInstance(context: Context): RepoDatabase {
            synchronized(this) {
                var instance: RepoDatabase? = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        RepoDatabase::class.java,
                        "repo_data_database"
                    ).fallbackToDestructiveMigration().build()
                }
                return instance
            }
        }
    }
}