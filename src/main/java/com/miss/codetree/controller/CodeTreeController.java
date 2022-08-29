package com.miss.codetree.controller;

import com.miss.codetree.CodeTreeApplication;
import com.miss.codetree.component.ButtonTreeViewCell;
import com.miss.codetree.constant.CodeProjectConstant;
import com.miss.codetree.constant.ImageConstant;
import com.miss.codetree.context.ProjectContext;
import com.miss.codetree.entity.CodeProject;
import com.miss.codetree.utils.ShellUtil;
import javafx.concurrent.Worker;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.Pane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import javafx.stage.Window;
import netscape.javascript.JSObject;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;


/**
 * @project: CodeTree
 * @package: com.miss.codetree.controller
 * @author: miss
 * @date: 2021/9/10 10:27
 * @since:
 * @history: 1.2021/9/10 created by miss
 */
public class CodeTreeController implements Initializable {


    public static String mdHtmlContent = """
                        <!DOCTYPE html>
                        <html lang="en">
                        <head>
                            <meta charset="UTF-8"/>
                            <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
                            <title>ByteMD example</title>
                            <link rel="stylesheet" href="https://unpkg.com/bytemd/dist/index.min.css"/>
                            <link rel="stylesheet" href="https://unpkg.com/github-markdown-css"/>
                            <script src="https://unpkg.com/bytemd"></script>
                            <script src="https://unpkg.com/@bytemd/plugin-gfm"></script>
                            <script src="https://unpkg.com/@bytemd/plugin-highlight"></script>
                            <style>
                                .bytemd {
                                    height: calc(100vh - 50px);
                                }

                                .footer {
                                    width: 100%;
                                    height: 30px;
                                    left: 0;
                                    position: absolute;
                                    bottom: 0;
                                    text-align: center;
                                }
                            </style>
                        </head>
                        <body>
            //            <div class="footer">
            //                <a href="https://github.com/bytedance/bytemd">bytemd</a>
            //            </div>
                        <script>
                            const plugins = [bytemdPluginGfm(), bytemdPluginHighlight()];
                            const editor = new bytemd.Editor({
                                target: document.body,
                                props: {
                                    value: '',
                                    plugins,
                                },
                            });
                            editor.$on('change', (e) => {
                                editor.$set({value: e.detail.value});
                            });
                            var jsConnector = {
                                    setMdContent: function (content) {
                                        editor.$set({value: content})
                                    }
                                };
                            function getJsConnector() {
                                return jsConnector;
                            }
                        </script>
                        </body>
                        </html>""";
    @FXML
    public Pane contentPane;
    @FXML
    public SplitPane contentSplitPane;
    @FXML
    public AnchorPane treePane;
    @FXML
    public TreeView<CodeProject> treeView;
    @FXML
    public AnchorPane rightContentPane;
    @FXML
    public SplitPane rightSplitPane;
    @FXML
    public AnchorPane rightPropertiesPane;
    @FXML
    public Label projectDirLabel;
    @FXML
    public TextField projectDirField;
    @FXML
    public Label projectBranchLabel;
    @FXML
    public TextField projectBranchField;
    @FXML
    public Label projectRemoteLabel;
    @FXML
    public TextField projectRemoteField;
    @FXML
    public Label projectAbstractLabel;
    @FXML
    public TextArea projectAbstractField;
    @FXML
    public AnchorPane rightReadmePane;
    public TextField projectNameField;
    public Label projectNameLabel;
    public Button deleteButton;
    public Button addCatalog;
    public WebView rightReadmeField;
    public Button openIdeButton;
    public Button expandTreeButton;
    public Button packupTreeButton;
    public Button editOrViewReadmeButton;
    /**
     * 用于与Javascript引擎通信。
     */
    private JSObject javascriptConnector;

    private static final String editorUrl = Objects.requireNonNull(CodeTreeApplication.class.getResource("/static/editor.html")).toExternalForm();
    private static final String viewUrl = Objects.requireNonNull(CodeTreeApplication.class.getResource("/static/viewer.html")).toExternalForm();

    private WebEngine webEngine;

    private boolean editFlag;

    private String readmeText = "";

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        webEngine = rightReadmeField.getEngine();

        webEngine.load(viewUrl);
        editFlag = false;
//        webEngine.loadContent(mdHtmlContent);
        webEngine.getLoadWorker().stateProperty().addListener((observable, oldValue, newValue) -> {
            if (Worker.State.SUCCEEDED == newValue) {
//                JSObject window = (JSObject) webEngine.executeScript("window");
                javascriptConnector = (JSObject) webEngine.executeScript("getJsConnector()");
                javascriptConnector.call("setMdContent", readmeText);
//
//                javascriptConnector.call("setMdContent", "# this is a title~~~");
            }
        });


        TreeItem<CodeProject> rootItem = ProjectContext.treeItem;
        rootItem.setExpanded((true));
        treeView.setBackground(Background.EMPTY);
        treeView.setRoot(rootItem);

        treeView.setShowRoot(false);
        treeView.setCellFactory(new ButtonTreeViewCell());
        treeView.getSelectionModel().selectedItemProperty().addListener(e -> {
            if (Objects.isNull(treeView.getSelectionModel().getSelectedItem())) {
                return;
            }
            CodeProject selectedProject = treeView.getSelectionModel().getSelectedItem().getValue();
            if (CodeProjectConstant.PROJECT_TYPE_CATALOG.equals(selectedProject.getProjectType())) {
                projectNameField.setText(selectedProject.getProjectName());
                projectDirField.setText(selectedProject.getProjectDir());
                projectAbstractField.setText(selectedProject.getProjectAbstract());
            } else {
                this.initGitProperties(selectedProject);
                projectNameField.setText(selectedProject.getProjectName());
                projectDirField.setText(selectedProject.getProjectDir());
                projectBranchField.setText(selectedProject.getProjectBranch());
                projectRemoteField.setText(selectedProject.getProjectRemote());
                projectAbstractField.setText(selectedProject.getProjectAbstract());
                String projectReadme = "";
                try {
                    projectReadme = FileUtils.readFileToString(new File(selectedProject.getProjectDir() + "/README.md"), StandardCharsets.UTF_8);
                } catch (IOException ex) {
//                    ex.printStackTrace();
                }
                readmeText = projectReadme;
                javascriptConnector.call("setMdContent", readmeText);
//                System.out.println(treeView.getSelectionModel().getSelectedItem().getValue().getProjectName());
            }
        });
        expandTreeButton.setGraphic(new ImageView(ImageConstant.expandTreeImage));
        expandTreeButton.setPadding(Insets.EMPTY);
        expandTreeButton.setBackground(Background.EMPTY);
        packupTreeButton.setGraphic(new ImageView(ImageConstant.packupTreeImage));
        packupTreeButton.setPadding(Insets.EMPTY);
        packupTreeButton.setBackground(Background.EMPTY);
    }

    private void initGitProperties(CodeProject codeProject) {
        String dir = codeProject.getProjectDir();
        String branchCmd = String.format("cd %s", dir) + " && " + "git rev-parse --abbrev-ref HEAD";
        try {
            String branch = ShellUtil.runShell(branchCmd);
            codeProject.setProjectBranch(branch);
        } catch (Exception e) {
//            e.printStackTrace();
        }

        String remoteCmd = String.format("cd %s", dir) + " && " + "git remote -v";
        try {
            String res = ShellUtil.runShell(remoteCmd);
            String remote = (res.split("\t")[1]).split("\\(")[0].trim();
            codeProject.setProjectRemote(remote);
        } catch (Exception e) {
//            e.printStackTrace();
        }

    }

    public void saveCodeProject() throws IOException {
        TreeItem<CodeProject> treeItem = treeView.getSelectionModel().getSelectedItem();
        CodeProject codeProject = treeItem.getValue();
        codeProject.setProjectName(projectNameField.getText());
        codeProject.setProjectDir(projectDirField.getText());
        codeProject.setProjectAbstract(projectAbstractField.getText());
        treeItem.setValue(codeProject);
        String text = (String) javascriptConnector.call("getMdContent","");
        // bugfix: 修复编辑README之后，保存之后，不会刷新
        readmeText = text;
        FileUtils.writeStringToFile(new File(codeProject.getProjectDir() + "/README.md"), text, StandardCharsets.UTF_8);
        ProjectContext.updateItem(codeProject);
    }

    public void deleteProject(MouseEvent mouseEvent) throws Exception {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("删除项目");
        alert.setHeaderText("删除项目");
        alert.setContentText("是否确认删除？");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
//            int i = treeView.getSelectionModel().getSelectedIndex();
            TreeItem<CodeProject> treeItem = treeView.getSelectionModel().getSelectedItem();
            CodeProject codeProject = treeItem.getValue();
            if (!treeItem.isLeaf()) {
                treeView.getSelectionModel().getSelectedItem().getParent().getChildren().addAll(treeItem.getChildren());
            }
            treeView.getSelectionModel().getSelectedItem().getParent().getChildren().remove(treeItem);
            ProjectContext.removeCodeProject(codeProject);
        } else {
            System.out.println("cancel");
        }
    }

    public void addProjectCatalog(MouseEvent mouseEvent) throws Exception {
        ButtonTreeViewCell.openProjectAddModal(ProjectContext.treeItem);

    }

    public void openProjectInIDE(MouseEvent mouseEvent) {
        TreeItem<CodeProject> treeItem = treeView.getSelectionModel().getSelectedItem();
        CodeProject codeProject = treeItem.getValue();
        if (CodeProjectConstant.PROJECT_TYPE_PROJECT.equals(codeProject.getProjectType())) {
            String dir = codeProject.getProjectDir();
            String openIdIdeCmd = String.format("cd %s", dir) + " && " + "idea .";
            try {
                ShellUtil.runShell(openIdIdeCmd);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void expandAllTree(MouseEvent mouseEvent) {
        ProjectContext.expandAllNode(ProjectContext.treeItem);
    }

    public void packupAllTree(MouseEvent mouseEvent) {
        ProjectContext.packupAllNode(ProjectContext.treeItem);
        ProjectContext.treeItem.setExpanded(true);
    }

    public void editOrViewReadme(MouseEvent mouseEvent) {

        if (!editFlag) {
            editOrViewReadmeButton.setText("查看");
            editOrViewReadmeButton.setStyle("-fx-background-color: #13911b;");
            webEngine.load(editorUrl);
            editFlag = true;

        }else {
            editOrViewReadmeButton.setText("编辑");
            editOrViewReadmeButton.setStyle("-fx-background-color: #2196f3;");
            webEngine.load(viewUrl);
            editFlag = false;

        }
    }
}
