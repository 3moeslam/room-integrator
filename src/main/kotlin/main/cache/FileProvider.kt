package main.cache

import com.google.gson.Gson
import main.entities.Database
import java.io.File


private fun initializeDatabaseCache(cacheFile: File) {
    val databaseObject = Database()
    val databaseJsonObject = Gson().toJson(databaseObject)
    cacheFile.writeText(databaseJsonObject)
}

fun provideDatabaseFile(projectPath: String): File {
    val baseFilesPath = projectPath + File.separatorChar + "app" + File.separatorChar + "build" + File.separatorChar + "room-generator"
    val databaseFile = File(baseFilesPath+File.separatorChar+"database")
    if(databaseFile.parentFile.mkdirs()){
        initializeDatabaseCache(databaseFile)
    }
    Gson().fromJson<>()
    return databaseFile
}

fun provideTablesFile(projectPath: String):File{
    val baseFilesPath = projectPath + File.separatorChar + "app" + File.separatorChar + "build" + File.separatorChar + "room-generator"
    val tablesFile = File(baseFilesPath+File.separatorChar+"tables")
    if(tablesFile.parentFile.mkdirs()){
        //TODO Create tables initializer method
    }
    return tablesFile
}