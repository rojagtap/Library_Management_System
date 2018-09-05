package sample;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.net.URL;
import java.util.ResourceBundle;

public class AddData implements Initializable {

    private Book book;
    @FXML
    private TextField uidField;
    @FXML
    private TextField titleField;
    @FXML
    private TextField authorField;
    @FXML
    private Label uidErrorMessage;
    @FXML
    private Label titleRequired;

    @FXML
    void addBook(ActionEvent event) {
        validateAndGo();
    }

    @FXML
    public void keyHit(KeyEvent key){
        if(key.getCode()== KeyCode.ENTER){
            validateAndGo();
        }
    }

    @FXML
    void cancelAdd(ActionEvent event) {
        AppController.window.close();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources){
        book=new Book();
    }

    private void validateAndGo(){
        if(!uidField.getText().equals("")){
            uidErrorMessage.setVisible(false);
        }
        if(!titleField.getText().equals("")){
            titleRequired.setVisible(false);
        }
        if(!uidField.getText().equals("") && !titleField.getText().equals("")){
            if(uidField.getText().length()!=6) {
                uidErrorMessage.setText("UID must be exactly 6 characters");
                uidErrorMessage.setVisible(true);
            }else {
                book.setUid(uidField.getText());
                book.setName(titleField.getText());
                if (!authorField.getText().equals("")) {
                    book.setAuthor(authorField.getText());
                }
                AppController.data.add(book);
                AppController.window.close();
            }
        }else{
            if(uidField.getText().equals("")) {
                uidErrorMessage.setText("Please enter an UID");
                uidErrorMessage.setVisible(true);
            }

            if(titleField.getText().equals("")){
                titleRequired.setVisible(true);
            }
        }
    }

}
