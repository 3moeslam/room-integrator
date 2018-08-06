package main.usecases

import com.intellij.openapi.project.Project
import com.intellij.psi.PsiDirectory
import com.intellij.psi.PsiFileFactory

fun generateEntities(project:Project,directory:PsiDirectory){
    val factory = PsiFileFactory.getInstance(project)
    val psiFile = factory.createFileFromText("test.java","vvvvvvvvvv")

}