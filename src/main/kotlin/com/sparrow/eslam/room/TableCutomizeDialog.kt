package com.sparrow.eslam.room

import com.intellij.util.ui.JBUI
import com.sparrow.eslam.room.entities.Field
import com.sparrow.eslam.room.entities.Table
import com.sparrow.eslam.room.views.NewFieldPanel
import java.awt.GridBagConstraints
import java.awt.GridBagLayout
import java.awt.event.KeyEvent
import java.awt.event.WindowAdapter
import java.awt.event.WindowEvent
import javax.swing.*

class TableCutomizeDialog internal constructor(table: Table?, val addTableView :(Table)->Unit ={}) : JDialog() {

    private lateinit var contentPane: JPanel
    private lateinit var buttonOK: JButton
    private lateinit var buttonCancel: JButton
    private lateinit var tableNameText: JTextField
    private lateinit var addField: JButton
    private lateinit var fieldsPane: JPanel
    private lateinit var errorMessageText: JLabel


    private var row = 0
    private val gbc = GridBagConstraints()


    private val table = Table()

    private val isEntriesIsValid: Boolean
        get() {
            if (tableNameText!!.text.isEmpty()) {
                animateDialogError("Please add table Name")
                return false
            }
            if (table.fields.isEmpty()) {
                animateDialogError("You have no fields")
                return false
            }
            for ((fieldName, fieldType) in table.fields) {
                if (fieldName.isEmpty()) {
                    animateDialogError("Please Add Field name")
                    return false
                }
                if (fieldType.isEmpty()) {
                    animateDialogError("Please Add field type")
                    return false
                }
            }
            return true
        }

    init {
        setContentPane(contentPane)
        isModal = true
        getRootPane().defaultButton = buttonOK

        tableNameText.text = table?.tableName ?: ""

        buttonOK.addActionListener { onOK() }

        buttonCancel.addActionListener { onCancel() }
        addField.addActionListener { addTableView() }

        defaultCloseOperation = WindowConstants.DO_NOTHING_ON_CLOSE

        addWindowListener(object : WindowAdapter() {
            override fun windowClosing(e: WindowEvent?) {
                onCancel()
            }
        })

        contentPane.registerKeyboardAction({ onCancel() }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT)
        pack()

        initializeTablesLayout()


        addFieldsIfExist(table)
    }

    private fun addFieldsIfExist(table: Table?) {
        if (table?.fields?.isNotEmpty() == true) {
            for (field in table.fields)
                addTableView(field)
        }
    }

    private fun onOK() {
        if (!isEntriesIsValid) {
            return
        }
        table.tableName = tableNameText.text
        addTableView(table)
        dispose()
    }

    private fun onCancel() {
        dispose()
    }


    private fun initializeTablesLayout() {
        fieldsPane.layout = GridBagLayout()
        gbc.insets = JBUI.insets(4)
        gbc.gridx = 0
        gbc.gridy = 0
        gbc.weightx = 1.0
        gbc.weighty = 1.0
        gbc.fill = GridBagConstraints.HORIZONTAL
        gbc.anchor = GridBagConstraints.NORTH
    }


    private fun addTableView(field: Field = Field()) {
        gbc.anchor = GridBagConstraints.NORTH
        gbc.gridy = row++
        gbc.gridheight = 1

        val panel = if (field.fieldName.isEmpty())
            NewFieldPanel()
                    else
            NewFieldPanel(field.fieldName, field.fieldType, field.isPrimaryKey, field.isIgnore)
        table.fields += panel.field
        fieldsPane.add(panel, gbc)
        fieldsPane.updateUI()

        pack()
    }

    private fun animateDialogError(errorMessage: String) {
        try {
            errorMessageText.text = errorMessage
            errorMessageText.isVisible = true
            pack()
            for (i in 0..4) {
                move(x + 10, y)
                Thread.sleep(60)
                move(x - 10, y)
                Thread.sleep(60)
            }
        } catch (th: Throwable) {
            th.printStackTrace()
        }

    }
}
