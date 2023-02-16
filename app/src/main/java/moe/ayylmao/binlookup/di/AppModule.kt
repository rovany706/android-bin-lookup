package moe.ayylmao.binlookup.di

import android.app.Application
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import moe.ayylmao.binlookup.data.data_source.binlist.BinlistApi
import moe.ayylmao.binlookup.data.data_source.database.BinQueryDatabase
import moe.ayylmao.binlookup.data.repository.BinQueryRepositoryImpl
import moe.ayylmao.binlookup.data.repository.BinlistRepositoryImpl
import moe.ayylmao.binlookup.domain.repository.BinQueryRepository
import moe.ayylmao.binlookup.domain.repository.BinlistRepository
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideDatabase(application: Application): BinQueryDatabase {
        return Room.databaseBuilder(
            application,
            BinQueryDatabase::class.java,
            BinQueryDatabase.DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideBinQueryRepository(db: BinQueryDatabase): BinQueryRepository {
        return BinQueryRepositoryImpl(db.binQueryDao)
    }

    @Provides
    @Singleton
    fun provideBinlistApi(): BinlistApi {
        return Retrofit.Builder()
            .baseUrl(BinlistApi.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(BinlistApi::class.java)
    }

    @Provides
    @Singleton
    fun provideBinlistRepository(binlistApi: BinlistApi): BinlistRepository {
        return BinlistRepositoryImpl(binlistApi)
    }
}