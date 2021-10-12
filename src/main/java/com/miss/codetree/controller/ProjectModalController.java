package com.miss.codetree.controller;

import com.miss.codetree.context.ProjectContext;
import com.miss.codetree.entity.CodeProject;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;
import java.util.UUID;

/**
 * @project: CodeTree
 * @package: com.miss.codetree.controller
 * @author: miss
 * @date: 2021/9/13 17:08
 * @since:
 * @history: 1.2021/9/13 created by miss
 */
public class ProjectModalController implements Initializable {


    public TextField projectDirField;
    public TextArea projectAbstractField;
    public TextField projectBranchField;
    public Button addProjectButton;
    public TextField projectNameField;
    public Button cancelButton;
    public ChoiceBox<String> projectTypeChoice;

    private TreeItem<CodeProject> parentCodeProjectItem;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        projectTypeChoice.setItems(FXCollections.observableList(Arrays.asList("catalog", "project")));
    }


    @FXML
    public void addProject() {
        CodeProject codeProject = new CodeProject();
        codeProject.setParentProjectCode(parentCodeProjectItem.getValue().getProjectCode());
        codeProject.setProjectCode(UUID.randomUUID().toString());
        codeProject.setProjectName(projectNameField.getText());
        codeProject.setProjectDir(projectDirField.getText());
        codeProject.setProjectAbstract(projectAbstractField.getText());
        codeProject.setProjectType(projectTypeChoice.getValue());
        parentCodeProjectItem.getChildren().add(new TreeItem<>(codeProject));
        ProjectContext.refreshProjects(codeProject);
        Stage stage = (Stage) addProjectButton.getScene().getWindow();
        stage.close();



    }

    public void initData(TreeItem<CodeProject> codeProject) {
        this.parentCodeProjectItem = codeProject;
    }

    public void cancelProject(MouseEvent mouseEvent) {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }
}
