package main.entities

data class Table(
        var tableName : String = "",
        val fields :List<Field> = ArrayList()
)