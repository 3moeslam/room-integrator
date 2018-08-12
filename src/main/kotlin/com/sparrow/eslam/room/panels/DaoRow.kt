package com.sparrow.eslam.room.panels

import javax.swing.*

class DaoRow {
    private lateinit var main: JPanel
    private lateinit var daoName: JTextField
    private lateinit var generateSelect: JCheckBox
    private lateinit var generateUpdate: JCheckBox
    private lateinit var tableCombo: JComboBox<*>
    private lateinit var generateDelete: JCheckBox
    private lateinit var generateInsert: JCheckBox

    fun getPanel():JPanel{
        return main
    }
}
