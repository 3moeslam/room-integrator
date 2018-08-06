package main.cache

import com.google.gson.Gson
import main.entities.Table

fun addTableToCache(table: Table, projectPath: String) {
    val tablesCacheFile = provideTablesFile(projectPath)
    var cachedList = ArrayList<Table>()
    try {
        cachedList = Gson().fromJson(tablesCacheFile.readText(), ArrayList<Table>().javaClass)
    } catch (th: Throwable) {
        th.printStackTrace()
    }
    cachedList.add(table)
    tablesCacheFile.writeText(Gson().toJson(cachedList))

}