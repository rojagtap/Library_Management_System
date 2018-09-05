package sample;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable{

    @FXML
    private JFXTextField nameField;

    @FXML
    private JFXPasswordField passField;

    @FXML
    private Label errorMessage;

    private DetailChecker nameChecker,passwordChecker;

    public static Stage window;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        nameChecker=new DetailChecker("Enter an username",nameField);
        passwordChecker=new DetailChecker("Enter a password",passField);

    }

    @FXML
    public void loggedIn(ActionEvent event){
        validateAndGo();
    }

    @FXML
    public void keyHit(KeyEvent key){
        if(key.getCode()== KeyCode.ENTER){
           validateAndGo();
        }
    }

    private void goNext() throws Exception{

        Main.window.close();
        window=new Stage();
        Parent app= FXMLLoader.load(getClass().getResource("app.fxml"));

        window.setTitle("Library Management System");
        window.setScene(new Scene(app));
        window.setMaximized(true);
        window.getIcons().add(Main.ICON);

        window.show();

    }

    private void validateAndGo(){
        nameChecker.isValid();
        passwordChecker.isValid();

        if(nameChecker.isValid()&&passwordChecker.isValid()){
            if(DetailChecker.verify(nameChecker,passwordChecker)){
                errorMessage.setVisible(false);

                try{
                    Thread.sleep(250);
                    Main.window.close();
                    goNext();
                } catch (Exception ex){
                    ex.printStackTrace();
                    Alert alert=new Alert(Alert.AlertType.ERROR);
                    alert.initOwner(Main.window);
                    alert.setTitle("Not Responding");
                    alert.setHeaderText(null);
                    alert.setContentText("We're sorry, something went wrong.");
                    alert.showAndWait();
                }
            } else{
                errorMessage.setVisible(true);
            }
        } else{
            errorMessage.setVisible(false);
        }

    }

}