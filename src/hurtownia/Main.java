package hurtownia;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.concurrent.TimeUnit;


public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        System.out.println(getClass().getResource("/hurtownia/view/login.fxml"));
        Parent root = FXMLLoader.load(getClass().getResource("/hurtownia/view/login.fxml"));
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Hurtownia sokow i napojow");
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
