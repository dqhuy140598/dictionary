package sample;
import Dictionary.Dictionary;
import Dictionary.SQLiteDataBase;
import com.sun.org.apache.bcel.internal.generic.Select;
import com.sun.org.apache.xml.internal.resolver.readers.ExtendedXMLCatalogReader;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.paint.Color;
import javafx.scene.web.WebView;
import javafx.collections.ObservableList;
import java.io.*;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.function.Predicate;

public class Controller implements Initializable {
    private Dictionary myDictionary = new Dictionary();
    private FilteredList<String> myFilteredList;
    private ObservableList<String> myListWord = FXCollections.observableArrayList();
    @FXML private ImageView imageSearch;
    @FXML private ImageView imageDictionary;
    @FXML private AnchorPane myAnchorPanel;
    @FXML private ListView<String> myListView1 = new ListView<>();
    @FXML private Label myLabel1;
    @FXML private WebView displayDefination;
    @FXML private Button button1;
    @FXML private TextField searchTextField;
    private SQLiteDataBase DataBase;
    public void pressButton(ActionEvent e){
       if(e.getSource()==button1){
            String Word = searchTextField.getText();
            String Defination = myDictionary.getWordsDefination(Word);
            String Temp = convertToDisplayHtml(Defination);
            displayDefination.getEngine().loadContent(Temp);
            writeRecentWordTofile(Word);
            if(!myListView1.getItems().contains(Word))
            myListView1.getItems().add(Word);
        }
    }
    public void initialize(URL url , ResourceBundle rb){
        /**
         * Create DataBase
         */
        DataBase = new SQLiteDataBase();
        DataBase.ConnectionSQLite();
        myDictionary.readDataFormFile();
        myListWord.addAll(myDictionary.getWords());
        myFilteredList = new FilteredList<String>(myListWord);
        /**
         * Load Image Form File
         */
        Image ImageSearch = new Image("image/search.jpg");
        Image ImageDictionary = new Image("image/Dictionary.png");
        /**
         * Create Button
         */
        button1.setGraphic(imageSearch);
        button1.setStyle("-fx-background-color:#219ecc");
        imageSearch.setImage(ImageSearch);
        imageDictionary.setImage(ImageDictionary);
        /**
         * Initalize Default ListView
         */
        myListView1.setEditable(true);
        myListView1.getItems().addAll(ReadRecentWordFormFile());
        myListView1.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        /**
         * Create Label
         */
        myLabel1.setBackground(Background.EMPTY);
        myLabel1.setStyle("-fx-background-color:#219ecc");
        myLabel1.setText("NGHĨA");

        /**
         *
         */

       // getSelectedWord();
    }
    public String convertToDisplayHtml(String Str){
        String Temp = Str;
        int i = Temp.indexOf("<i>");
        Temp = Temp.substring(0, i-1) + "<br/>" + Temp.substring(i);
        Temp = "<html>" + Temp +"</html>";
        return Temp;
    }
    public void getSelectedWord(){
        myListView1.setOnMouseClicked(event -> {
            try{
                displayDefination.getEngine().reload();
                String Word = myListView1.getSelectionModel().getSelectedItem();
                String Defination = myDictionary.getWordsDefination(Word);
                displayDefination.getEngine().loadContent(Defination);
            }
            catch(Exception ex){
                ex.printStackTrace();
            }
        });
    }
    public Set<String> ReadRecentWordFormFile(){
        HashSet<String> Temp = new HashSet<String>();
        FileInputStream fileIn = null;
        try{
            fileIn = new FileInputStream("src/DataBase/RecentWord.txt");
            BufferedReader Read = new BufferedReader(new InputStreamReader(fileIn,"utf-8"));
            String line;
            while((line=Read.readLine())!=null){
                Temp.add(line);
            }
            Read.close();
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return Temp;
    }
    public void writeRecentWordTofile(String Word){
        try{
            File fileOut = new File("src/DataBase/RecentWord.txt");
            if(fileOut.exists()!=true){
                fileOut.createNewFile();
            }
            FileWriter fileWriter = new FileWriter(fileOut.getAbsoluteFile(),true);
            BufferedWriter BufferWrite = new BufferedWriter(fileWriter);
            BufferWrite.write(Word);
            BufferWrite.write("\n");
            BufferWrite.close();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
