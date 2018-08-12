package com.sparrow.eslam.room.views

import com.sparrow.eslam.room.entities.Field
import java.awt.GridLayout
import java.awt.event.ItemEvent
import javax.swing.JCheckBox
import javax.swing.JPanel
import javax.swing.JTextField
import javax.swing.event.DocumentEvent
import javax.swing.event.DocumentListener

class NewFieldPanel constructor(val fileldName: String = ""
                                , val filedType: String = ""
                                , val isPrimary: Boolean = false
                                , val isIgnore: Boolean = false) : JPanel() {

    val field = Field()

    init {
        layout = GridLayout()
        add(provideFieldView())
    }


    private fun provideFieldView(): JPanel {
        val panel = providePanel(4)
        val fieldNameText = provideTextField("Field Name", fileldName)
        val fieldTypeText = provideTextField("Field Type", filedType)
        val primaryKeyCheck = proivdeCheckBox("Primary Key")
        val ignoreCheck = proivdeCheckBox("Ignore")
        val constraints = provideConstrains()


        initializeFromConstructor(fieldNameText, fieldTypeText, primaryKeyCheck, ignoreCheck)


        fieldNameText.document.addDocumentListener(provideTextChangListner {
            println(fieldNameText.text)
            field.fieldName = fieldNameText.text
        })

        fieldTypeText.document.addDocumentListener(provideTextChangListner {
            println(fieldTypeText.text)
            field.fieldType = fieldTypeText.text
        })

        primaryKeyCheck.addItemListener {
            field.isPrimaryKey = it.stateChange == ItemEvent.SELECTED
        }

        ignoreCheck.addItemListener {
            field.isIgnore = it.stateChange == ItemEvent.SELECTED
        }

        panel.add(fieldNameText, constraints)
        constraints.gridy = 1
        panel.add(fieldTypeText, constraints)
        constraints.gridy = 2
        panel.add(primaryKeyCheck, constraints)
        constraints.gridy = 3
        panel.add(ignoreCheck, constraints)
        return panel
    }

    fun initializeFromConstructor(fieldNameText: JTextField, fieldTypeText: JTextField, primaryKeyCheck: JCheckBox, ignoreCheck: JCheckBox) {
        if (fileldName.isNotEmpty())
            fieldNameText.text = fileldName
        if (filedType.isNotEmpty())
            fieldTypeText.text = filedType

        primaryKeyCheck.isSelected = isPrimary
        ignoreCheck.isSelected = isIgnore
    }


    private fun provideTextChangListner(action: () -> Unit): DocumentListener {
        return object : DocumentListener {
            override fun changedUpdate(e: DocumentEvent?) {
                action()
            }

            override fun insertUpdate(e: DocumentEvent?) {
                action()
            }

            override fun removeUpdate(e: DocumentEvent?) {
                action()
            }
        }
    }
}