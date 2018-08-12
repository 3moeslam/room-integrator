package com.sparrow.eslam.room.cache

import com.google.gson.Gson
import com.sparrow.eslam.room.entities.Database

class DatabaseCacheFile{
    val databaseFile = provideDatabaseFile()
    val database = Gson().fromJson<Database>(databaseFile.readText(),Database::class.java)

    fun write(databse: Database){
        val gson = Gson().toJson(databse)
        databaseFile.writeText(gson)
    }
}