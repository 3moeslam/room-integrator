package forms;

import com.intellij.util.ui.JBUI;
import main.cache.EditTablesFileKt;
import main.entities.Field;
import main.entities.Table;
import main.views.NewFieldPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

public class TableNameDialog extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField tableNameText;
    private JButton addField;
    private JPanel fieldsPane;
    private JLabel errorMessageText;

    private WelcomeWindow welcomeWindow;


    private int row = 0;
    private final GridBagConstraints gbc = new GridBagConstraints();

    private final List<Table> tablesList = new ArrayList<>();

    private final Table table = new Table();
    private final String projectPath;

    TableNameDialog(WelcomeWindow welcomeWindow,String projectPath) {
        this.welcomeWindow = welcomeWindow;

        this.projectPath = projectPath;
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);


        buttonOK.addActionListener(e -> onOK());

        buttonCancel.addActionListener(e -> onCancel());
        addField.addActionListener(this::addTableView);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(e -> onCancel(), KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
        pack();
        initializeTablesLayout();
    }

    private void onOK() {
        if (!isEntriesIsValid()) {
            return;
        }
        table.setTableName(tableNameText.getText());
        EditTablesFileKt.addTableToCache(table,projectPath);
        welcomeWindow.addTableView(tableNameText.getText());
        for (Field f : table.getFields()) {
            System.out.println("Field Name is: " + f.getFieldName());
            System.out.println("Field Type is: " + f.getFieldType());
        }
        dispose();
    }

    private void onCancel() {
        dispose();
    }


    private void initializeTablesLayout() {
        fieldsPane.setLayout(new GridBagLayout());
        gbc.insets = JBUI.insets(10);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.NORTH;
    }


    private void addTableView(ActionEvent event) {
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.gridy = row++;
        gbc.gridheight = 1;
        NewFieldPanel panel = new NewFieldPanel();
        table.getFields().add(panel.getField());
        fieldsPane.add(panel, gbc);
        fieldsPane.updateUI();

        pack();
    }

    private boolean isEntriesIsValid(){
        if(tableNameText.getText().isEmpty()){
            animateDialogError("Please add table Name");
            return false;
        }
        if(table.getFields().size() == 0){
            animateDialogError("You have no fields");
            return false;
        }
        for(Field field : table.getFields()){
            if(field.getFieldName().isEmpty()){
                animateDialogError("Please Add Field name");
                return false;
            }
            if(field.getFieldType().isEmpty()){
                animateDialogError("Please Add field type");
                return false;
            }
        }
        return true;
    }

    private void animateDialogError(String errorMessage) {
        try {
            errorMessageText.setText(errorMessage);
            errorMessageText.setVisible(true);
            pack();
            for (int i = 0; i < 5; i++) {
                move(getX() + 10, getY());
                Thread.sleep(60);
                move(getX() - 10, getY());
                Thread.sleep(60);
            }
        }catch (Throwable th){
            th.printStackTrace();
        }
    }
}
