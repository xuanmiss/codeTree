package com.miss.codetree;

import com.miss.codetree.constant.ImageConstant;
import com.miss.codetree.context.ProjectContext;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class CodeTreeApplication extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(CodeTreeApplication.class.getResource("code-tree.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 900, 600);
        primaryStage.setTitle("CodeTree");
        primaryStage.setScene(scene);


        primaryStage.getIcons().add(ImageConstant.codeTreeImage);

        primaryStage.show();
    }
}
