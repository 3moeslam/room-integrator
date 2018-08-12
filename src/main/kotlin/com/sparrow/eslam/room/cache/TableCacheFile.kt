package com.sparrow.eslam.room.cache

import com.google.gson.Gson
import com.sparrow.eslam.room.entities.Table

class TableCacheFile{

    val file = provideTablesFile()

    val tables : MutableList<Table> get() {
        return  Gson().fromJson(file.readText(), Array<Table>::class.java).toMutableList()
    }
    fun add(table: Table){
        val cachedList = Gson().fromJson(file.readText(), ArrayList<Table>().javaClass)
        cachedList.add(table)
        file.writeText(Gson().toJson(cachedList))
    }

    fun write(tables :List<Table>){
        file.writeText(Gson().toJson(tables))
    }

}