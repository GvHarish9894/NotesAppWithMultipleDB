package com.techgv.notesappwithmultipledb.di

import android.app.Application
import android.content.Context
import com.techgv.domain.NoteRepository
import com.techgv.domain.NoteRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Singleton
    @Provides
    fun provideContext(application: Application): Context = application.applicationContext

    @Singleton
    @Provides
    fun provideSharedPreference(context: Context): NoteRepository = NoteRepositoryImpl(context)


}