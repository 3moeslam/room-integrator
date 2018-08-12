package com.sparrow.eslam.room.entities

data class Database(
        val version: Int = 1,
        val name :String = "",
        val useRxJava :Boolean = false,
        val userLiveData:Boolean = true,
        val exportSchema :Boolean = true
)

data class Field(
        var fieldName :String = "",
        var fieldType:String = "",
        var isPrimaryKey :Boolean = false,
        var isIgnore :Boolean = false
)


data class Table(
        var tableName : String = "",
        var fields :List<Field> = ArrayList()
)