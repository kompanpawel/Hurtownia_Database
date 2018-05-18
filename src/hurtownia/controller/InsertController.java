package hurtownia.controller;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;


public class InsertController {

    @FXML
    private ComboBox<InsertOption> choice;



    public void initialize() {
        this.choice.setItems(FXCollections.observableArrayList(InsertOption.values()));
    }
}
