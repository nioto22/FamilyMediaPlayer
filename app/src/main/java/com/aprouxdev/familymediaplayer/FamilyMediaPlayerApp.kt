package com.aprouxdev.familymediaplayer

import android.app.Application
import androidx.media3.common.util.UnstableApi
import androidx.media3.database.StandaloneDatabaseProvider
import androidx.media3.datasource.cache.LeastRecentlyUsedCacheEvictor
import androidx.media3.datasource.cache.SimpleCache


@UnstableApi
class FamilyMediaPlayerApp : Application() {
    companion object {
        lateinit var cache: SimpleCache
    }

    private val cacheSize: Long = 90 * 1024 * 1024
    private lateinit var cacheEvictor: LeastRecentlyUsedCacheEvictor
    private lateinit var exoPlayerDatabaseProvider: StandaloneDatabaseProvider


    override fun onCreate() {
        super.onCreate()
        cacheEvictor = LeastRecentlyUsedCacheEvictor(cacheSize)
        exoPlayerDatabaseProvider = StandaloneDatabaseProvider(this)
        cache = SimpleCache(cacheDir, cacheEvictor, exoPlayerDatabaseProvider)

    }
}