package hurtownia.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.*;



public class HurtowniaController {

    
    @FXML
    public void insertData() {
        try {
            Stage insert = new Stage();
            FXMLLoader loader = new FXMLLoader();
            Parent root = loader.load(getClass().getResource("/hurtownia/view/insert.fxml").openStream());

            InsertController insertController = (InsertController) loader.getController();

            Scene scene = new Scene(root);
            insert.setScene(scene);
            insert.setTitle("Hurtownia sokow i napojow");
            insert.setResizable(false);
            insert.show();
        }  catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    public void modifyData() {

    }

    public void deleteData() {

    }
}
