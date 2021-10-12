package com.miss.codetree.component;

import com.miss.codetree.CodeTreeApplication;
import com.miss.codetree.constant.ImageConstant;
import com.miss.codetree.controller.ProjectModalController;
import com.miss.codetree.entity.CodeProject;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.Border;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * @project: CodeTree
 * @package: com.miss.codetree.component
 * @author: miss
 * @date: 2021/9/13 11:22
 * @since:
 * @history: 1.2021/9/13 created by miss
 */
public class ButtonTreeViewCell extends TreeCell<CodeProject> {

    @Override
    protected void updateItem(CodeProject item, boolean empty) {
        super.updateItem(item, empty);

        // If the cell is empty we don't show anything.
        if (isEmpty()) {
            setGraphic(null);
            setText(null);
        } else {
            // We only show the custom cell if it is a leaf, meaning it has
            // no children.
            if ("catalog".equalsIgnoreCase(item.getProjectType())) {

                // A custom HBox that will contain your check box, label and
                // button.
                HBox cellBox = new HBox(10);
                ImageView imageView = new ImageView(ImageConstant.foldImage);
                if (this.getTreeItem().isExpanded()) {
                    imageView = new ImageView(ImageConstant.expandImage);
                }


//                CheckBox checkBox = new CheckBox();
                Label label = new Label(item.getProjectName());
                Button button = new Button();
                button.setGraphic(new ImageView(ImageConstant.addImage));
                button.setPadding(Insets.EMPTY);
                button.setBackground(Background.EMPTY);
                button.setOnMousePressed(e -> {
                    button.setGraphic(new ImageView(ImageConstant.pressedAddImage));

                });
                button.setOnMouseClicked(e -> {
                    try {
                        TreeItem<CodeProject> currentTreeItem = getTreeItem();
                        openProjectAddModal(currentTreeItem);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                });
                button.setOnMouseReleased(e -> {
                    button.setGraphic(new ImageView(ImageConstant.addImage));
                });


                cellBox.getChildren().addAll(imageView, label, button);
                setGraphic(cellBox);
                setText(null);
            } else {
                HBox cellBox = new HBox(10);
                ImageView imageView = new ImageView(ImageConstant.projectImage);
                Label label = new Label(item.getProjectName());
                cellBox.getChildren().addAll(imageView, label);
                setGraphic(cellBox);
                setText(null);
            }
        }
    }



    public static void openProjectAddModal(TreeItem<CodeProject> codeProject) throws IOException {
        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(CodeTreeApplication.class.getResource("project-modal.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        ProjectModalController projectModalController = fxmlLoader.getController();
        projectModalController.initData(codeProject);
        stage.setTitle("CodeTree");
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();
    }

}
