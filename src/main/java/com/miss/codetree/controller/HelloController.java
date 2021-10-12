package com.miss.codetree.controller;

import javafx.fxml.FXML;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;

public class HelloController {
    public DialogPane dialog;
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
        dialog.setExpanded(true);
    }
}