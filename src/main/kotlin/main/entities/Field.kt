package main.entities

data class Field(
        var fieldName :String = "",
        var fieldType:String = "",
        var isPrimaryKey :Boolean = false,
        var isIgnore :Boolean = false
)