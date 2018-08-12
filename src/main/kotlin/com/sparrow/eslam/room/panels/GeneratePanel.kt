package com.sparrow.eslam.room.panels

import com.sparrow.eslam.room.RoomGeneratorWindow
import com.sparrow.eslam.room.cache.CacheFile
import com.sparrow.eslam.room.cache.DatabaseCacheFile
import com.sparrow.eslam.room.cache.TableCacheFile
import com.sparrow.eslam.room.generators.generateDatabase
import com.sparrow.eslam.room.generators.generateEntities
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
    private lateinit var fileChooser: JFileChooser
    private var gridBagConstraints = GridBagConstraints()
    lateinit var main: JPanel

    private var numOfDao = 0
    private val cacheFile = CacheFile()

    init {
        initializeFileChooser()
        selectPackageBtn.addActionListener { selectPackageFolder()?.run(::generateClasses) }
        addDao.addActionListener { addDao() }
        daoPanel.layout = GridBagLayout()
        initilizeBagConstraints()
        fileChooser = initializeFileChooser()
        //initializeDaos()
    }

    private fun initializeDaos() {
        val tables = TableCacheFile().tables

        for (table in tables) {
            addDao()
        }
    }

    private fun initilizeBagConstraints() = with(gridBagConstraints) {
        gridx = 0
        gridy = 0
        weightx = 1.0
        weighty = 1.0
        fill = GridBagConstraints.HORIZONTAL
        anchor = GridBagConstraints.NORTH
        gridheight = 1
        gridwidth = 1
    }

    private fun addDao() = with(DaoRow().getPanel()) {
        border = BorderFactory.createRaisedBevelBorder()
        gridBagConstraints.gridy = numOfDao++
        daoPanel.add(this, gridBagConstraints)
        daoPanel.updateUI()
    }


    private fun initializeFileChooser() = JFileChooser().apply {
        fileSelectionMode = JFileChooser.DIRECTORIES_ONLY
        currentDirectory = selectedDirectory()
        dialogTitle = "Select room package"
    }

    private fun selectedDirectory() = cacheFile.path
            .takeIf { it.isNotEmpty() }
            ?.run(::File)
            ?: File(RoomGeneratorWindow.projectPath)


    private fun selectPackageFolder() = RoomGeneratorWindow.instanse
            .toolWindow
            .component
            .run(fileChooser::showSaveDialog)
            .takeIf { it == JFileChooser.APPROVE_OPTION }
            ?.let { fileChooser.selectedFile }


    private fun generateClasses(selectedFolder: File) = selectedFolder
            .apply { cacheFile.write(absolutePath) }
            .path
            .apply(::generateEntities)
            .apply { generateDatabase(this, DatabaseCacheFile().database, TableCacheFile().tables) }


}
