package com.sparrow.eslam.room.generators

import com.intellij.ide.projectView.ProjectView
import com.intellij.openapi.vfs.LocalFileSystem
import com.sparrow.eslam.room.RoomGeneratorWindow
import com.sparrow.eslam.room.entities.Database
import com.sparrow.eslam.room.entities.Table
import java.io.File

fun generateDatabase(packagePath :String,database: Database,tables : List<Table>){
    val packageName = providePackageName(packagePath)
    val classBuilder = StringBuilder()

    classBuilder.append("package ")
    classBuilder.append(packageName)
    classBuilder.append(newLineParam)
    classBuilder.append(newLineParam)
    classBuilder.append("import androidx.room.Database\n" +
            "import androidx.room.RoomDatabase")
    classBuilder.append(newLineParam)
    classBuilder.append(newLineParam)
    classBuilder.append("@Database(")
    classBuilder.append("version = ${database.version} , entities = [")
    for (table in tables){
        classBuilder.append("${table.tableName}::class,")
    }
    classBuilder.replace(classBuilder.toString().length- 1, classBuilder.toString().length,"")
    classBuilder.append("], exportSchema = ${database.exportSchema} ) $newLineParam")

    classBuilder.append("abstract class Database : RoomDatabase() {\n" +
            "    abstract fun getDao(): DatabaseDAO\n" +
            "}")

    val outputPath = packagePath + File.separatorChar + "Database.kt"
    val file = File(outputPath)
    file.createNewFile()
    file.writeText(classBuilder.toString())
    ProjectView.getInstance(RoomGeneratorWindow.instanse.project).refresh()
    LocalFileSystem.getInstance()
            .findFileByIoFile(File(packagePath))?.refresh(false,true)
}

