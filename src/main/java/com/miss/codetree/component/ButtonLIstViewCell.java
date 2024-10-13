package com.miss.codetree.component;

import com.miss.codetree.constant.CodeProjectConstant;
import com.miss.codetree.constant.ImageConstant;
import com.miss.codetree.entity.CodeProject;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.HBox;
import javafx.util.Callback;

public class ButtonLIstViewCell implements Callback<ListView<CodeProject>, ListCell<CodeProject>> {
    @Override
    public ListCell<CodeProject> call(ListView<CodeProject> tListView) {
        return new ListCell<CodeProject>() {
            @Override
            protected void updateItem(CodeProject item, boolean empty) {
                super.updateItem(item, empty);
                if (isEmpty()) {
                    setGraphic(null);
                    setText(null);
                }else {
                    if (CodeProjectConstant.PROJECT_TYPE_CATALOG.equalsIgnoreCase(item.getProjectType())) {
                        Button button = new Button();
                        button.setGraphic(new ImageView(ImageConstant.addImage));
                        button.setPadding(Insets.EMPTY);
                        button.setBackground(Background.EMPTY);
                        button.setOnMousePressed(e -> button.setGraphic(new ImageView(ImageConstant.pressedAddImage)));
                    }
                }
            }
        };
    }
}
