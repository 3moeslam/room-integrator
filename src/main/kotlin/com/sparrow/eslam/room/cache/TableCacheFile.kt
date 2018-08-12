package com.sparrow.eslam.room.cache

import com.google.gson.Gson
import com.sparrow.eslam.room.entities.Table

class TableCacheFile {

    private val file by lazy {  provideTablesFile()}

    val tables by lazy {
        file.readText()
                .takeIf { it.isNotEmpty() }
                ?.run { Gson().fromJson(this, Array<Table>::class.java) }
                ?.toMutableList()
                ?: mutableListOf()
    }

    fun add(table: Table) {
        val cachedList = Gson().fromJson(file.readText(), ArrayList<Table>().javaClass)
        cachedList.add(table)
        file.writeText(Gson().toJson(cachedList))
    }

    fun write(tables: List<Table>) {
        file.writeText(Gson().toJson(tables))
    }

}