package com.example.imagesearch.data.database.di

import android.app.Application
import androidx.room.Room
import com.example.imagesearch.data.database.LikeDao
import com.example.imagesearch.data.database.LikeRoomDataBase
import com.example.imagesearch.data.database.RemoteKeyDao
import com.example.imagesearch.data.database.ResponseDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal object DataBaseModule {
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

    @Provides
    fun provideRemoteKeyDao(dataBase: LikeRoomDataBase): RemoteKeyDao{
        return dataBase.remoteKeyDao()
    }
    @Provides
    fun provideResponseDao(dataBase: LikeRoomDataBase): ResponseDao{
        return dataBase.responseDao()
    }
}