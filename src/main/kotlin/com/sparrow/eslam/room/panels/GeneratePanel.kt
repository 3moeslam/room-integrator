package com.sparrow.eslam.room.panels

import com.intellij.psi.JavaPsiFacade
import com.sparrow.eslam.room.RoomGeneratorWindow
import com.sparrow.eslam.room.cache.CacheFile
import com.sparrow.eslam.room.cache.DatabaseCacheFile
import com.sparrow.eslam.room.cache.TableCacheFile
import com.sparrow.eslam.room.generators.generateDatabase
import com.sparrow.eslam.room.generators.generateEntities
import com.sparrow.eslam.room.generators.providePackageName
import java.awt.GridBagConstraints
import java.awt.GridBagLayout
import java.io.File
import javax.swing.BorderFactory
import javax.swing.JButton
import javax.swing.JFileChooser
import javax.swing.JPanel

class GeneratePanel {
    private lateinit var mainGeneratePanel: JPanel
    private lateinit var selectPackageBtn: JButton
    private lateinit var daoPanel: JPanel
    private lateinit var addDao: JButton
    private lateinit var main: JPanel

    private var numOfDao = 0
    private var gridBagConstraints = GridBagConstraints()
    private lateinit var fileChooser: JFileChooser
    private val cacheFile = CacheFile()

    init {
        initializeFileChooser()
        selectPackageBtn.addActionListener { selectPackageFolder()?.run(::generateClasses) }
        addDao.addActionListener { addDao() }
        daoPanel.layout = GridBagLayout()
        initilizeBagConstraints()

        //initializeDaos()
    }

    private fun initializeDaos() {
        val tables = TableCacheFile().tables

        for (table in tables) {
            addDao()
        }
    }

    private fun initilizeBagConstraints() {
        gridBagConstraints.gridx = 0
        gridBagConstraints.gridy = 0
        gridBagConstraints.weightx = 1.0
        gridBagConstraints.weighty = 1.0
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL
        gridBagConstraints.anchor = GridBagConstraints.NORTH
        gridBagConstraints.gridheight = 1
        gridBagConstraints.gridwidth = 1
    }

    private fun addDao() {
        val daoRow = DaoRow().getPanel()
        daoRow.border = BorderFactory.createRaisedBevelBorder()

        gridBagConstraints.gridy = numOfDao++
        daoPanel.add(daoRow, gridBagConstraints)
        daoPanel.updateUI()
    }

    private fun initializeFileChooser() {
        fileChooser = JFileChooser()
        fileChooser.fileSelectionMode = JFileChooser.DIRECTORIES_ONLY
        fileChooser.currentDirectory = if (cacheFile.path.isNotEmpty()) File(cacheFile.path) else File(RoomGeneratorWindow.projectPath)
        fileChooser.dialogTitle = "Select room package"
    }


    fun getPanel(): JPanel? {
        return main
    }

    private fun selectPackageFolder() =
            RoomGeneratorWindow.instanse
                    .toolWindow
                    .component
                    .run(fileChooser::showSaveDialog)
                    .takeIf { it == JFileChooser.APPROVE_OPTION }
                    ?.let { fileChooser.selectedFile }


    private fun generateClasses(selectedFolder: File) {
        selectedFolder.apply { cacheFile.write(absolutePath) }
                .path
                .apply(::generateEntities)
                .apply { generateDatabase(this, DatabaseCacheFile().database, TableCacheFile().tables) }


    }
}
