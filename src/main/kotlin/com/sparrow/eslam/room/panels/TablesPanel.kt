package com.sparrow.eslam.room.panels

import com.sparrow.eslam.room.TableCutomizeDialog
import com.sparrow.eslam.room.cache.TableCacheFile
import com.sparrow.eslam.room.entities.Table
import com.sparrow.eslam.room.views.provideButton
import com.sparrow.eslam.room.views.provideConstrains
import com.sparrow.eslam.room.views.provideJLabel
import com.sparrow.eslam.room.views.providePanel
import java.awt.GridBagConstraints
import java.awt.GridBagLayout
import javax.swing.JButton
import javax.swing.JPanel

class TablesPanel{
    private lateinit var main :JPanel
    private lateinit var addView :JButton
    private lateinit var mainTablesPanel :JPanel
    private lateinit var tablesPanel :JPanel

    private var gridBagConstraints = GridBagConstraints()
    private var numOfTables = 0

    val tablesFile = TableCacheFile()
    val tables :MutableList<Table> = tablesFile.tables

    init {
        addView.addActionListener { addNewTableAction()  }
        mainTablesPanel.layout = GridBagLayout()
        initilizeBagConstraints()

        tablesFile.tables.forEach(::addTableToPanel)
    }

    fun getPanel():JPanel?{
        return main
    }

    private fun addNewTableAction(){
        TableCutomizeDialog(null,::addNewTable).show()
    }

    private fun addNewTable(table: Table){
        tablesFile.add(table)
        addTableToPanel(table)
    }

    private fun addTableToPanel(table: Table) {
        val panel = providePanel(3)
        val label = provideJLabel(table.tableName)
        val configBtn = provideButton("Config")
        val deleteBtn = provideButton("Delete")
        val constraints = provideConstrains()

        configBtn.addActionListener{bindConfigureBtnAction(table)}
        deleteBtn.addActionListener { bindDeleteBtnAction(table) }
        panel.add(label, constraints)
        constraints.gridy = 1
        panel.add(configBtn, constraints)
        constraints.gridy = 2
        panel.add(deleteBtn, constraints)

        with(gridBagConstraints){
            anchor = GridBagConstraints.NORTH
            gridy = numOfTables++
            gridheight = 1
            gridwidth = 1
        }

        with(mainTablesPanel){
            add(panel,gridBagConstraints)
            updateUI()
        }
    }

    private fun bindDeleteBtnAction(table: Table) {
        //TODO ask first
        mainTablesPanel.remove(tables.indexOf(table))
        tables.remove(table)
        tablesFile.write(tables)
        mainTablesPanel.updateUI()
    }


    private fun bindConfigureBtnAction(table: Table){
        TableCutomizeDialog(table).show()
    }

    private fun initilizeBagConstraints(){
        gridBagConstraints.gridx = 0
        gridBagConstraints.gridy = 0
        gridBagConstraints.weightx = 1.0
        gridBagConstraints.weighty = 1.0
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL
        gridBagConstraints.anchor = GridBagConstraints.NORTH
    }
}
