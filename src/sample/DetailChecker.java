package sample;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.RequiredFieldValidator;
import javafx.collections.ObservableList;

public class DetailChecker {

    private JFXTextField jfxTextField;
    private JFXPasswordField jfxPasswordField;
    private RequiredFieldValidator requiredFieldValidator;

    private DetailChecker(String message){

        requiredFieldValidator=new RequiredFieldValidator();
        requiredFieldValidator.setMessage(message);

    }

    DetailChecker(String message,JFXTextField jfxTextField){

        this(message);
        this.jfxTextField=jfxTextField;
        this.jfxPasswordField=null;
        jfxTextField.getValidators().add(requiredFieldValidator);

    }

    DetailChecker(String message, JFXPasswordField jfxPasswordField){

        this(message);
        this.jfxTextField=null;
        this.jfxPasswordField=jfxPasswordField;
        jfxPasswordField.getValidators().add(requiredFieldValidator);

    }

    public boolean isValid(){

        if(jfxTextField==null){
            return jfxPasswordField.validate();
        } else {
            return jfxTextField.validate();
        }

    }

    public static boolean verify(DetailChecker username,DetailChecker password){

        ObservableList<LoginData> detail=LoginData.getDetail();

        for(LoginData temp:detail){
            if(temp.getUsername().equals(username.jfxTextField.getText())&&
                    temp.getPassword().equals(password.jfxPasswordField.getText())){
                return true;
            }
        }

        return false;

    }

}
