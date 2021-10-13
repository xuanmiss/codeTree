package com.miss.codetree.component;

import com.miss.codetree.CodeTreeApplication;
import com.miss.codetree.constant.ImageConstant;
import com.miss.codetree.context.ProjectContext;
import com.miss.codetree.controller.ProjectModalController;
import com.miss.codetree.entity.CodeProject;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.Background;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;
import java.util.Objects;

/**
 * @project: CodeTree
 * @package: com.miss.codetree.component
 * @author: miss
 * @date: 2021/9/13 11:22
 * @since:
 * @history: 1.2021/9/13 created by miss
 */
public class ButtonTreeViewCell implements Callback<TreeView<CodeProject>, TreeCell<CodeProject>> {
    private static final DataFormat JAVA_FORMAT = new DataFormat("application/x-java-serialized-object");
    private static final String DROP_HINT_STYLE = "-fx-border-color: #eea82f; -fx-border-width: 0 0 2 0; -fx-padding: 3 3 1 3";
    private TreeCell<CodeProject> dropZone;
    private TreeItem<CodeProject> draggedItem;

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

    @Override
    public TreeCell<CodeProject> call(TreeView<CodeProject> treeView) {
        TreeCell<CodeProject> cell = new TreeCell<CodeProject>() {
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
        };
        cell.setOnDragDetected((MouseEvent event) -> dragDetected(event, cell, treeView));
        cell.setOnDragOver((DragEvent event) -> dragOver(event, cell, treeView));
        cell.setOnDragDropped((DragEvent event) -> drop(event, cell, treeView));
        cell.setOnDragDone((DragEvent event) -> clearDropLocation());

        return cell;
    }

    private void dragDetected(MouseEvent e, TreeCell<CodeProject> cell, TreeView<CodeProject> treeView) {
        draggedItem = cell.getTreeItem();

        // root can't be dragged
        if (draggedItem.getParent() == null) return;
        Dragboard db = cell.startDragAndDrop(TransferMode.MOVE);

        ClipboardContent content = new ClipboardContent();
        content.put(JAVA_FORMAT, draggedItem.getValue());
        db.setContent(content);
        db.setDragView(cell.snapshot(null, null));
        e.consume();
    }

    private void dragOver(DragEvent e, TreeCell<CodeProject> cell, TreeView<CodeProject> treeView) {
        if (!e.getDragboard().hasContent(JAVA_FORMAT)) return;
        TreeItem<CodeProject> thisItem = cell.getTreeItem();

        // can't drop on itself
        if (draggedItem == null || thisItem == null || thisItem == draggedItem) return;
        // ignore if this is the root
        if (draggedItem.getParent() == null) {
            clearDropLocation();
            return;
        }

        e.acceptTransferModes(TransferMode.MOVE);
        if (!Objects.equals(dropZone, cell)) {
            clearDropLocation();
            this.dropZone = cell;
            dropZone.setStyle(DROP_HINT_STYLE);
        }
    }

    private void drop(DragEvent e, TreeCell<CodeProject> cell, TreeView<CodeProject> treeView) {
        Dragboard db = e.getDragboard();
        boolean success = false;
        if (!db.hasContent(JAVA_FORMAT)) return;

        TreeItem<CodeProject> thisItem = cell.getTreeItem();
        TreeItem<CodeProject> droggedItemParent = draggedItem.getParent();

        // remove from previous location, and remove data
        droggedItemParent.getChildren().remove(draggedItem);
        droggedItemParent.getValue().getSubProjectList().remove(draggedItem.getValue());

        // dropping on parent node makes it the first child
        if (Objects.equals(droggedItemParent, thisItem)) {
            thisItem.getChildren().add(0, draggedItem);
            draggedItem.getValue().setParentProjectCode(thisItem.getParent().getValue().getProjectCode());
            thisItem.getValue().getSubProjectList().add(0, draggedItem.getValue());
            treeView.getSelectionModel().select(draggedItem);
        } else {
            if ("catalog".equals(thisItem.getValue().getProjectType())) {
                // 如果拖入了文件夹，则更换parent
                thisItem.getChildren().add(0, draggedItem);
                draggedItem.getValue().setParentProjectCode(thisItem.getValue().getProjectCode());
                thisItem.getValue().getSubProjectList().add(0, draggedItem.getValue());
            } else {
                // add to new location
                int indexInParent = thisItem.getParent().getChildren().indexOf(thisItem);
                thisItem.getParent().getChildren().add(indexInParent + 1, draggedItem);
                draggedItem.getValue().setParentProjectCode(thisItem.getParent().getValue().getProjectCode());
                thisItem.getParent().getValue().getSubProjectList().add(indexInParent + 1, draggedItem.getValue());
            }
        }
        treeView.getSelectionModel().select(draggedItem);
        e.setDropCompleted(success);
        ProjectContext.saveCodeProjectConfig(treeView.getRoot().getValue());
    }

    private void clearDropLocation() {
        if (dropZone != null) dropZone.setStyle("");
    }

}
