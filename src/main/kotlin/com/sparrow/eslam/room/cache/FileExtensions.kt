package com.sparrow.eslam.room.cache

import com.google.gson.Gson
import java.io.File

inline fun <reified T> File.readItems() = readText()
        .takeIf { it.isNotEmpty() }
        ?.run { Gson().fromJson(this, Array<T>::class.java) }
        ?.toMutableList()
        ?: mutableListOf()

inline fun <reified T> File.writeItems(items: List<T>) = writeText(Gson().toJson(items))

fun <T> File.appendItem(item: T) = Gson().fromJson(readText(), ArrayList<T>().javaClass)
        ?.add(item)
        ?.run(Gson()::toJson)
        ?.let { writeText(it) }
        ?: with(Gson().toJson(arrayListOf(item))) {
            writeText(this)
        }


