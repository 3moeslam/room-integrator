package com.sparrow.eslam.room.cache

import com.google.gson.Gson
import com.sparrow.eslam.room.RoomGeneratorWindow
import com.sparrow.eslam.room.entities.Database
import java.io.File


private fun initializeDatabaseCache(cacheFile: File) {
    val databaseObject = Database()
    val databaseJsonObject = Gson().toJson(databaseObject)
    cacheFile.writeText(databaseJsonObject)
}

fun provideDatabaseFile(): File {
    val baseFilesPath = RoomGeneratorWindow.projectPath + File.separatorChar + "app" + File.separatorChar + "build" + File.separatorChar + "room-generator"
    val databaseFile = File(baseFilesPath+File.separatorChar+"database")
    if(databaseFile.parentFile.mkdirs()){
        initializeDatabaseCache(databaseFile)
    }
    return databaseFile
}

fun provideCacheFile(): File {
    val baseFilesPath = RoomGeneratorWindow.projectPath + File.separatorChar + "app" + File.separatorChar + "build" + File.separatorChar + "room-generator"
    val cacheFile = File(baseFilesPath+File.separatorChar+"cache")
    cacheFile.parentFile.mkdirs()
    if(!cacheFile.exists()) cacheFile.createNewFile()
    return cacheFile
}

fun provideTablesFile():File{
    val baseFilesPath = RoomGeneratorWindow.projectPath + File.separatorChar + "app" + File.separatorChar + "build" + File.separatorChar + "room-generator"
    val tablesFile = File(baseFilesPath+File.separatorChar+"tables")
    tablesFile.parentFile.mkdirs()
    if(!tablesFile.exists())
        tablesFile.createNewFile()
    return tablesFile
}