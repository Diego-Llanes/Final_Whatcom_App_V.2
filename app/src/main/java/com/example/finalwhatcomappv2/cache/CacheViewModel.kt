package com.example.finalwhatcomappv2.cache

import android.app.Application
import androidx.lifecycle.*
import com.example.whatcomapp.cache.CacheDatabaseDao
import com.example.whatcomapp.cache.CacheDatabase
import com.example.whatcomapp.cache.CacheEntity
import kotlinx.coroutines.launch


class CacheViewModel(val database: CacheDatabaseDao, application: Application) : AndroidViewModel(application) {

    private val _navigateToCache = MutableLiveData<Boolean?>()

    val navigateToCache: LiveData<Boolean?>
        get() = _navigateToCache

    fun doneNavigating() {
        _navigateToCache.value = null
    }

    fun addEntity(entity: CacheEntity) {
        viewModelScope.launch {
            database.add(entity)
            _navigateToCache.value = true
        }
    }
}

