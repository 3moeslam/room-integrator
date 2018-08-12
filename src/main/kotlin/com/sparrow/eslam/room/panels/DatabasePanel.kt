package com.sparrow.eslam.room.panels

import com.sparrow.eslam.room.cache.DatabaseCacheFile
import com.sparrow.eslam.room.entities.Database
import java.awt.Color
import javax.swing.*

class DatabasePanel {

    private lateinit var main :JPanel
    private lateinit var save: JButton
    private lateinit var useRxJavaCheckBox: JCheckBox
    private lateinit var useLiveDataCheckBox: JCheckBox
    private lateinit var exportSchemaComboBox: JComboBox<Any>
    private lateinit var databaseVersionTextBox: JTextField
    private lateinit var databaseNameTextBox: JTextField
    private lateinit var errorText :JLabel

    private val databaseFile = DatabaseCacheFile()

    init{
        save.addActionListener{onSaveBtnClicked()}

        exportSchemaComboBox.apply {
            addItem("True")
            addItem("False")
        }
        bindWithCached()
    }

    private fun bindWithCached() {
        useRxJavaCheckBox.isSelected = databaseFile.database.useRxJava
        useLiveDataCheckBox.isSelected = databaseFile.database.userLiveData
        databaseNameTextBox.text = databaseFile.database.name
        databaseVersionTextBox.text = databaseFile.database.version.toString()
    }

    private fun onSaveBtnClicked() {
        if(isEntriesValid()) {
            val database = Database(
                    name = databaseNameTextBox.text,
                    version = Integer.parseInt(databaseVersionTextBox.text),
                    exportSchema = exportSchemaComboBox.selectedItem == "True",
                    userLiveData = useLiveDataCheckBox.isSelected,
                    useRxJava = useRxJavaCheckBox.isSelected
            )
            databaseFile.write(database)
            showConfirm("Done!")
        }else {
            if(databaseNameTextBox.text.isEmpty()) {
               showError("Please enter database name")
                return
            }
            if(databaseVersionTextBox.text.isEmpty())
                showError("Please enter database version")
        }
    }

    private fun isEntriesValid() :Boolean {
        return databaseNameTextBox.text.isNotEmpty() && databaseVersionTextBox.text.isNotEmpty()
    }


    private fun showError(txt:String){
        errorText.text = txt
        errorText.isVisible = true
        errorText.foreground = Color.RED
    }

    private fun showConfirm(txt:String){
        errorText.text = txt
        errorText.isVisible = true
        errorText.foreground = Color.GREEN
    }

    fun getPanel ():JPanel?{
        return main
    }
}
