package com.sparrow.eslam.room.generators

import com.google.gson.Gson
import com.intellij.openapi.project.Project
import com.sparrow.eslam.room.cache.provideDatabaseFile
import com.sparrow.eslam.room.cache.provideTablesFile
import com.sparrow.eslam.room.entities.Table
import java.io.File

class LoadingUseCase constructor(project: Project) {
    val tableFile: File = provideTablesFile()
    val databaseFile :File = provideDatabaseFile()

    fun getTablesList():List<Table>{
        val cachedTables = tableFile.readText()
        return Gson().fromJson(cachedTables, Array<Table>::class.java).toList()
    }
}