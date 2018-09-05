package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import java.io.BufferedReader;
import java.io.FileReader;

public class LoginData {


    private String username,password;

    private void setUsername(String username) {
        this.username = username;
    }

    private void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public static ObservableList<LoginData> getDetail(){

        ObservableList<LoginData> data= FXCollections.observableArrayList();

        try {
            String line;
            FileReader fileReader = new FileReader("src//database//login.csv");
            BufferedReader bufferedReader=new BufferedReader(fileReader);

            while((line=bufferedReader.readLine())!=null){
                LoginData temp=new LoginData();
                String[] value=line.split(",");
                temp.setUsername(value[0]);
                temp.setPassword(value[1]);
                data.add(temp);
            }

        }catch(Exception ex){
            ex.printStackTrace();
            Alert alert=new Alert(Alert.AlertType.ERROR);
            alert.initOwner(Main.window);
            alert.setTitle("Not Responding");
            alert.setHeaderText(null);
            alert.setContentText("We're sorry, something went wrong.");
            alert.showAndWait();
        }

        return data;

    }

}
