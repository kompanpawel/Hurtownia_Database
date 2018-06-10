package hurtownia.controller;

import hurtownia.model.LoginModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.concurrent.ExecutionException;


public class LoginController { /// kontroler odpowiedzialny za logowanie

    LoginModel loginModel = new LoginModel();

    @FXML
    private Label dbstatus;
    @FXML
    private TextField username;
    @FXML
    private PasswordField password;
    @FXML
    private Button loginButton;

    @FXML
    private void initialize() { /// sprawdzenie czy mamy połączenie z bazą danych
        if (this.loginModel.isDbConnected())
            this.dbstatus.setText("Connected");
        else
            this.dbstatus.setText("Not connected to database");
    }

    @FXML
    public void Login(ActionEvent event) {
        try {
            if (this.loginModel.isLogin(this.username.getText(), this.password.getText())) {
                Stage stage = (Stage) this.loginButton.getScene().getWindow();
                stage.close();
                success();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText("Username or password incorrect");
                alert.showAndWait();
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void success() { ///wejście do hurtowni
        try {
            Stage hurtownia = new Stage();
            FXMLLoader loader = new FXMLLoader();
            Parent root = loader.load(getClass().getResource("/hurtownia/view/hurtownia.fxml").openStream());

            HurtowniaController hurtowniaController = (HurtowniaController)loader.getController();

            Scene scene = new Scene(root);
            hurtownia.setScene(scene);
            hurtownia.setTitle("Hurtownia sokow i napojow");
            hurtownia.setResizable(false);
            hurtownia.show();

        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }
}