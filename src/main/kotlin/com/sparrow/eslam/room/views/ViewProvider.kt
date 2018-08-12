package com.sparrow.eslam.room.views

import java.awt.Color
import java.awt.GridBagConstraints
import java.awt.GridLayout
import java.awt.event.FocusEvent
import java.awt.event.FocusListener
import javax.swing.*
import javax.swing.event.ChangeEvent


fun provideJLabel(labelText: String): JLabel {
    return JLabel().apply {
        text = labelText
        horizontalAlignment = SwingConstants.CENTER
    }
}

fun provideButton(buttonText: String): JButton {
    return JButton().apply {
        text = buttonText
//        addActionListener{
//            if(table.tableName.isNotEmpty())
//                TableCutomizeDialog().show()
//        }
    }
}

@JvmOverloads
fun providePanel(columnNum:Int = 2): JPanel {
    return JPanel().apply {
        layout = GridLayout(1, columnNum, 3, 3)
        border = BorderFactory.createRaisedBevelBorder()
    }
}

fun provideConstrains(): GridBagConstraints {
    return GridBagConstraints().apply {
        gridx = 0
        gridy = 0
    }
}

fun provideTextField(placeHolder: String = "",txt:String=""): JTextField {
    return JTextField().apply {

        if(txt.isEmpty())
            foreground = Color.GRAY

        text = if(txt.isEmpty()) placeHolder else txt

        addFocusListener(object : FocusListener {
            override fun focusGained(e: FocusEvent) {
                if (text == placeHolder) {
                    text = ""
                    foreground = Color.BLACK
                }
            }

            override fun focusLost(e: FocusEvent) {
                if (text.isEmpty()) {
                    foreground = Color.GRAY
                    text = placeHolder
                }
            }
        })
    }
}

@JvmOverloads
fun proivdeCheckBox(title: String, callback: (ChangeEvent) -> Unit = {}): JCheckBox {
    return JCheckBox().apply {
        text = title
        addChangeListener(callback)
    }
}