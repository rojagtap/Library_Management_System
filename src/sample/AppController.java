package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.controlsfx.control.textfield.TextFields;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.net.URL;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.ResourceBundle;

public class AppController implements Initializable{

    static Stage window;
    public static final DateTimeFormatter FORMATTER=DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private ObservableList<String> keywords;
    static ObservableList<Book> data;

    @FXML
    GridPane gridPane;
    @FXML
    private TextField searchBox;
    @FXML
    private TableView<Book> bookData;
    @FXML
    private TableColumn<Book, String> uid;
    @FXML
    private TableColumn<Book, String> name;
    @FXML
    private TableColumn<Book, String> author;
    @FXML
    private TableColumn<Book, LocalDate> issueDate;
    @FXML
    private TableColumn<Book, LocalDate> returnDate;
    @FXML
    private TableColumn<Book, String> availability;
    @FXML
    private TableColumn<Book,String> issuer;
    @FXML
    private Label errorMessage;

    @FXML
    void searched(ActionEvent event) {

        String search=searchBox.getText();
        boolean isError=true;
        for(String temp:keywords){
            if(search.equals(temp)){
                isError=false;
                errorMessage.setVisible(false);
                bookData.scrollTo(keywords.indexOf(temp)/3);
                bookData.getSelectionModel().select(keywords.indexOf(temp)/3);
            }
            else if(search.equals("")){
                isError=false;
                errorMessage.setVisible(false);
            }
        }
        if(isError){
            errorMessage.setVisible(true);
        }
    }

    @FXML
    void clearIssued(ActionEvent event){
        Book selectedBook=bookData.getSelectionModel().getSelectedItem();
        System.out.println(data.indexOf(selectedBook));
        if(selectedBook!=null && selectedBook.getAvailability().equals("no")){
            if(LocalDate.parse(selectedBook.getReturnDate(),FORMATTER)
                    .compareTo(LocalDate.now())<=0){

                Alert alert=new Alert(Alert.AlertType.CONFIRMATION);
                alert.initOwner(Controller.window);
                alert.setTitle("Late Submission");
                alert.setHeaderText(null);
                alert.setContentText("Please collect Rs. " +
                        (5+(Period.between(LocalDate.parse(selectedBook.getReturnDate(),FORMATTER),LocalDate.now()).getDays()/7)*5)
                        +" from the student");
                Optional<ButtonType> clickedButton=alert.showAndWait();
                ButtonType buttonType=clickedButton.orElse(ButtonType.CANCEL);

                if(buttonType==ButtonType.OK){
                    clear(selectedBook);
                }
            }else{
                clear(selectedBook);
            }

        }
    }

    @FXML
    void addBook(ActionEvent event) throws Exception{
        window=new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("add.fxml"));

        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Add Book");
        window.setScene(new Scene(root, 450, 400));
        window.setResizable(false);
        window.getIcons().add(Main.ICON);

        window.showAndWait();
        setBook();
        bookData.setItems(getBook());
    }

    @FXML
    void deleteBook(ActionEvent event){
        Book selectedBook=bookData.getSelectionModel().getSelectedItem();
        if(selectedBook!=null && selectedBook.getAvailability().equals("yes")) {
            data.remove(data.indexOf(selectedBook));
            setBook();
            bookData.setItems(getBook());
        }
    }

    @FXML
    void issueBook(TableColumn.CellEditEvent<Book,String> cellEditEvent){

        LocalDate temp;
        Book selectedBook=bookData.getSelectionModel().getSelectedItem();
        if(selectedBook.getAvailability().equals("yes")){
            selectedBook.setIssuer(cellEditEvent.getNewValue());
            selectedBook.setIssueDate((temp=LocalDate.now()).format(FORMATTER));
            selectedBook.setReturnDate(temp.plusDays(7).format(FORMATTER));
            selectedBook.setAvailability("no");
            setBook();
            bookData.setItems(getBook());
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        getKeywords();

        uid.setCellValueFactory(new PropertyValueFactory<>("uid"));
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        author.setCellValueFactory(new PropertyValueFactory<>("author"));
        issueDate.setCellValueFactory(new PropertyValueFactory<>("issueDate"));
        returnDate.setCellValueFactory(new PropertyValueFactory<>("returnDate"));
        availability.setCellValueFactory(new PropertyValueFactory<>("availability"));
        issuer.setCellValueFactory(new PropertyValueFactory<>("issuer"));

        bookData.setItems(getBook());

        bookData.setEditable(true);
        issuer.setCellFactory(TextFieldTableCell.forTableColumn());

    }

    private ObservableList<Book> getBook(){

        data= FXCollections.observableArrayList();
        String line;

        try {
            FileReader fileReader = new FileReader("src//database//books.csv");
            BufferedReader bufferedReader=new BufferedReader(fileReader);

            while ((line = bufferedReader.readLine())!=null){
                String[] value=line.split(",");
                data.add(new Book(value[0],value[1],value[2],value[3],value[4],value[5],value[6]));
            }

            FXCollections.sort(data);

        }catch(Exception ex){
            ex.printStackTrace();
            Alert alert=new Alert(Alert.AlertType.ERROR);
            alert.initOwner(Controller.window);
            alert.setTitle("Not Responding");
            alert.setHeaderText(null);
            alert.setContentText("We're sorry, something went wrong.");
            alert.showAndWait();
            Controller.window.close();
        }

        return data;

    }

    private void setBook(){
        FXCollections.sort(data);
        StringBuilder str=new StringBuilder();
        BufferedWriter bufferedWriter;
        try {
            bufferedWriter = new BufferedWriter(new FileWriter("src//database//books.csv",false));
            for(Book temp:data) {
                str.append(temp.getUid()).append(",").append(temp.getName())
                        .append(",").append(temp.getAuthor()).append(",")
                        .append(temp.getIssueDate()).append(",").append(temp.getReturnDate())
                        .append(",").append(temp.getAvailability()).append(",").append(temp.getIssuer()).append("\n");
            }
            bufferedWriter.write(str.toString());
            bufferedWriter.close();
            getKeywords();
        }catch(Exception ex) {
            ex.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initOwner(Controller.window);
            alert.setTitle("Not Responding");
            alert.setHeaderText(null);
            alert.setContentText("We're sorry, something went wrong.");
            alert.showAndWait();
        }
    }

    private void getKeywords(){

        ObservableList<Book> books=getBook();
        keywords=FXCollections.observableArrayList();

        for(Book temp:books){
            keywords.addAll(temp.getUid(),temp.getName(),temp.getAuthor());
        }

        TextFields.bindAutoCompletion(searchBox,keywords);
    }

    private void clear(Book selectedBook){
        selectedBook.setAvailability("yes");
        selectedBook.setIssueDate("N/A");
        selectedBook.setReturnDate("N/A");
        selectedBook.setIssuer("N/A");
        setBook();
        bookData.setItems(getBook());
    }

}