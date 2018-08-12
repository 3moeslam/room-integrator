package com.sparrow.eslam.room.generators

import com.intellij.openapi.roots.ProjectRootManager
import com.intellij.openapi.vfs.LocalFileSystem
import com.sparrow.eslam.room.RoomGeneratorWindow
import java.io.File

fun providePackageName(selectedPath: String) : String? {
    val file = LocalFileSystem.getInstance()
            .findFileByIoFile(File(selectedPath))
    return ProjectRootManager.getInstance(RoomGeneratorWindow.instanse.project)
            .fileIndex
            .getPackageNameByDirectory(file!!)

}