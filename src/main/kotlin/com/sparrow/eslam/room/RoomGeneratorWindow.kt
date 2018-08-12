package com.sparrow.eslam.room

import com.intellij.openapi.project.Project
import com.intellij.openapi.wm.ToolWindow
import com.intellij.openapi.wm.ToolWindowFactory
import com.intellij.ui.content.ContentFactory
import com.sparrow.eslam.room.panels.DatabasePanel
import com.sparrow.eslam.room.panels.GeneratePanel
import com.sparrow.eslam.room.panels.TablesPanel
import javax.swing.JLabel
import javax.swing.JPanel
import javax.swing.JTabbedPane

class RoomGeneratorWindow : ToolWindowFactory {
    private var MainLabel: JLabel? = null
    private var fieldsPane: JTabbedPane? = null
    private var mainPanel: JPanel? = null
    lateinit var project :Project
    lateinit var toolWindow: ToolWindow
    companion object {
        lateinit var instanse : RoomGeneratorWindow
        lateinit var projectPath :String
    }


    override fun createToolWindowContent(project: Project, toolWindow: ToolWindow) {
        bindCompanion(project.baseDir.path)
        this.project = project
        this.toolWindow = toolWindow
        bindMainWindow(toolWindow)
    }

    private fun bindCompanion(path: String) {
        RoomGeneratorWindow.projectPath = path
        RoomGeneratorWindow.instanse = this
    }

    private fun bindMainWindow(toolWindow: ToolWindow) {
        fieldsPane.apply {
            this!!.addTab("Database", DatabasePanel().getPanel())
            addTab("Tables", TablesPanel().getPanel())
            addTab("Generate", GeneratePanel().getPanel())
        }
        val contentFactory = ContentFactory.SERVICE.getInstance()
        val content = contentFactory.createContent(mainPanel, "", false)
        toolWindow.contentManager.addContent(content)
    }
}
