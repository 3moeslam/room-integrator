package com.sparrow.eslam.room.cache

class CacheFile{
    private val file = provideCacheFile()

    val path :String get() {
        return file.readText()
    }

    fun write(path :String){
        file.writeText(path)
    }


}