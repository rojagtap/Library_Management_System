package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;


public class Main extends Application {

    public static Stage window;
    public static final Image ICON=new Image("image/icon.png");

    @Override
    public void start(Stage primaryStage) throws Exception{

        window=primaryStage;
        Parent root = FXMLLoader.load(getClass().getResource("login.fxml"));

        window.setTitle("Library Management System");
        window.setScene(new Scene(root, 790, 600));
        window.setResizable(false);
        window.getIcons().add(ICON);

        window.show();

    }


    public static void main(String[] args) {

        launch(args);

    }
}
