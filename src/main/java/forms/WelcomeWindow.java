package forms;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowFactory;
import com.intellij.ui.content.Content;
import com.intellij.ui.content.ContentFactory;
import com.intellij.util.ui.JBUI;
import com.intellij.util.ui.UI;
import com.trilead.ssh2.crypto.cipher.AES;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class WelcomeWindow implements ToolWindowFactory {
    private JLabel MainLabel;
    private JPanel MainPanel;
    private JTabbedPane fieldsPane;
    private JTextField databaseNameTextBox;
    private JComboBox exportSchemaComboBox;
    private JButton save;
    private JTextField databaseVersionTextBox;
    private JButton addView;
    private JPanel tablesPanel;
    private JPanel mainTablesPanel;
    private JCheckBox useRxJavaCheckBox;
    private JCheckBox useLiveDataCheckBox;
    private JScrollPane scrollPane;
    private ToolWindow toolWindow;
    private int row = 0;
    final GridBagConstraints gbc = new GridBagConstraints();

    @Override
    public void createToolWindowContent(@NotNull Project project, @NotNull ToolWindow toolWindow) {
        addView.addActionListener(this::addView);
        this.toolWindow = toolWindow;
        initializeComboBox();
        initializeDatabaseVersion();
        bindMainWindow(toolWindow);
        System.out.println(project.getBaseDir().getPath());
        initializeTablesLayout();
    }

    private void initializeTablesLayout() {
        mainTablesPanel.setLayout(new GridBagLayout());
        gbc.insets = JBUI.insets(10);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty= 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.NORTH;
    }

    private void bindMainWindow(@NotNull ToolWindow toolWindow) {
        ContentFactory contentFactory = ContentFactory.SERVICE.getInstance();
        Content content = contentFactory.createContent(MainPanel, "", false);
        toolWindow.getContentManager().addContent(content);
    }

    private void initializeDatabaseVersion() {
        databaseVersionTextBox.setText("1");
    }

    private void initializeComboBox() {
        exportSchemaComboBox.addItem("True");
        exportSchemaComboBox.addItem("False");
    }

    private void addView(ActionEvent event) {
        System.out.println("Action Clicked");
        new TableNameDialog(this).show();
    }


    void addNewTableView(String tableName){
        System.out.println("Action Clicked");
        JPanel viewPanel = new JPanel();
        GridBagConstraints c = new GridBagConstraints();
        viewPanel.setLayout(new GridLayout(1, 2,3, 3));
        viewPanel.setBorder(BorderFactory.createRaisedBevelBorder());
        JLabel fieldName = new JLabel();
        JButton btn = new JButton();
        fieldName.setText("Table Name:"+tableName);
        btn.setText("Configure");
        btn.setHorizontalAlignment(SwingConstants.CENTER);
        fieldName.setHorizontalAlignment(SwingConstants.CENTER);

        c.gridx = 0;
        c.gridy = 0;
        viewPanel.add(fieldName,c);
        c.gridx= 1;

        viewPanel.add(btn,c);

        viewPanel.revalidate();
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.gridy = row++;
        gbc.gridheight = 1;
        mainTablesPanel.add(viewPanel,gbc);
        mainTablesPanel.updateUI();
        mainTablesPanel.revalidate();
    }
}
