package com.example.finalwhatcomappv2.cache

import android.app.Application
import androidx.lifecycle.*
import com.example.whatcomapp.cache.CacheDatabaseDao
import com.example.whatcomapp.cache.CacheDatabase
import com.example.whatcomapp.cache.CacheEntity
import kotlinx.coroutines.launch
import kotlin.coroutines.coroutineContext


class CacheViewModel(val database: CacheDatabaseDao, application: Application) : AndroidViewModel(application) {

    private val _navigateToCache = MutableLiveData<Boolean?>()

    val navigateToCache: LiveData<Boolean?>
        get() = _navigateToCache

    private val names = database.getAllnames()

//    val cacheString = Transformations.map(names)

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


