package com.mypharmacybd.db

import android.content.Context
import androidx.room.Room
import com.mypharmacybd.other.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DBModule {

    @Singleton
    @Provides
    fun providePharmacyDatabase(@ApplicationContext context: Context) = Room.databaseBuilder(
        context,
        PharmacyDatabase::class.java,
        Constants.DB_NAME
    ).build()

    @Singleton
    @Provides
    fun providePharmacyDAO(db: PharmacyDatabase) = db.getPharmacyDAO()
}