module com.miss.codetree {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.fasterxml.jackson.databind;
    requires com.fasterxml.jackson.annotation;
    requires com.fasterxml.jackson.core;
    requires org.apache.commons.io;
    requires javafx.web;
    requires jdk.jsobject;


    opens com.miss.codetree to javafx.fxml;
    exports com.miss.codetree;
    exports com.miss.codetree.controller;
    exports com.miss.codetree.entity;
    opens com.miss.codetree.controller to javafx.fxml;
}