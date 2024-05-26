package com.example.imagesearch.data.database.di

import android.app.Application
import androidx.room.Room
import com.example.imagesearch.data.database.LikeDao
import com.example.imagesearch.data.database.LikeRoomDataBase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DataBaseModule {
    @Provides
    fun provideLikeDataBase(application: Application): LikeRoomDataBase {
        return Room.databaseBuilder(
            application,
            LikeRoomDataBase::class.java,
            "LikeDataBase"
        ).build()
    }

    @Provides
    fun provideLikeDao(dataBase: LikeRoomDataBase): LikeDao{
        return dataBase.likeDao()
    }
}