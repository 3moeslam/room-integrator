package main.entities

data class Database(
        val version: Int = 1,
        val name :String = "",
        val useRxJava :Boolean = false,
        val userLiveData:Boolean = true,
        val exportSchema :Boolean = true

)