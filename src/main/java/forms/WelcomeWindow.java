package forms;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowFactory;
import com.intellij.psi.JavaPsiFacade;
import com.intellij.ui.content.Content;
import com.intellij.ui.content.ContentFactory;
import com.intellij.util.ui.JBUI;
import main.cache.FileProviderKt;
import main.views.ViewProviderKt;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;

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
    private JButton selectPackageBtn;
    private JScrollPane scrollPane;
    private ToolWindow toolWindow;
    private int row = 0;
    private final GridBagConstraints gbc = new GridBagConstraints();
    private String projectPath;
    private JFileChooser fileChooser;
    private File roomPackage;


    private Project project;
    @Override
    public void createToolWindowContent(@NotNull Project project, @NotNull ToolWindow toolWindow) {
        addView.addActionListener(this::addView);
        this.toolWindow = toolWindow;
        initializeComboBox();
        initializeDatabaseVersion();
        bindMainWindow(toolWindow);
        System.out.println(project.getBaseDir().getPath());
        initializeTablesLayout();
        FileProviderKt.provideDatabaseFile(project.getBaseDir().getPath());
        projectPath = project.getBaseDir().getPath();
        selectPackageBtn.addActionListener(this::selectPackageEvent);
        initializeFileChooser();
        this.project = project;
    }

    private void initializeFileChooser() {
        fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        fileChooser.setCurrentDirectory(new File(projectPath));
        fileChooser.setDialogTitle("Select room package");
    }

    private void initializeTablesLayout() {
        mainTablesPanel.setLayout(new GridBagLayout());
        gbc.insets = JBUI.insets(10);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 1;
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

    private void selectPackageEvent(ActionEvent actionEvent){
        int returnVal = fileChooser.showSaveDialog(toolWindow.getComponent());
        if(returnVal == JFileChooser.APPROVE_OPTION){
            roomPackage = fileChooser.getSelectedFile();
            System.out.println(roomPackage.getName());
            JavaPsiFacade.getInstance(this.project).findPackage(roomPackage.getName());
//            PsiDirectory directory = PsiManager.getInstance(this.project).findDirectory();
//            EntitiesGeneratorKt.generateEntities(this.project,);
        }
    }

    private void addView(ActionEvent event) {
        System.out.println("Action Clicked");
        new TableNameDialog(this,projectPath).show();
    }



    void addTableView(String lblText){
        JPanel panel = ViewProviderKt.providePanel();
        JLabel label = ViewProviderKt.provideJLabel(lblText);
        JButton button = ViewProviderKt.provideButton("Config");
        GridBagConstraints constraints = ViewProviderKt.provideConstrains();

        panel.add(label,constraints);
        constraints.gridy = 1;
        panel.add(button,constraints);

        gbc.anchor = GridBagConstraints.NORTH;
        gbc.gridy = row++;
        gbc.gridheight = 1;

        mainTablesPanel.add(panel,gbc);
        mainTablesPanel.updateUI();

    }
}
