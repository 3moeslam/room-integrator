package com.sparrow.eslam.room.generators

import com.google.gson.Gson
import com.intellij.ide.projectView.ProjectView
import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.LocalFileSystem
import com.sparrow.eslam.room.RoomGeneratorWindow
import com.sparrow.eslam.room.cache.provideTablesFile
import com.sparrow.eslam.room.entities.Table
import java.io.File


const val newLineParam = "\n"

fun generateEntities(selectedPath: String) {
    val packageName = providePackageName(selectedPath)
    val entitiesFile = provideTablesFile()
    println(entitiesFile.readText())


    StringBuilder().apply {
        append("package ")
        append(packageName)
        append(newLineParam)
        append(newLineParam)

        val cachedList = Gson().fromJson(entitiesFile.readText(), Array<Table>::class.java).toList()
        cachedList.forEach {table ->
            append("@Entity ")
            append("data class ")
            append(table.tableName)
            append("(")
            append(newLineParam)
            table.fields.forEach {
                append("val ")
                append(it.fieldName)
                append(":")
                append(it.fieldType)

                append(",")
                append(newLineParam)
            }
            replace(toString().length- 2, toString().length,"")
            append(newLineParam)
            append(")")
            append(newLineParam)
            append(newLineParam)
            append(newLineParam)
        }
    }.run {
        createFileFromString(selectedPath, this.toString(), RoomGeneratorWindow.instanse.project)

    }



}

fun createFileFromString(selectedPath: String, classContent: String, project: Project) {
    val outputPath = selectedPath + File.separatorChar + "Entites.kt"
    val file = File(outputPath)
    file.createNewFile()
    file.writeText(classContent)
    ProjectView.getInstance(project).refresh()
    LocalFileSystem.getInstance()
            .findFileByIoFile(File(selectedPath))?.refresh(false, true)
}