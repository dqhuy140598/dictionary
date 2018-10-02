package Dictionary;
import javafx.scene.control.ListView;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SQLiteDataBase {
    private static String Url = "jdbc:sqlite:C:/Users/Dao Quang Huy/Downloads/dict_hh.db";
    private Connection connection = null;
    private Statement statement;
    public void ConnectionSQLite(){
        try{
            connection = DriverManager.getConnection(Url);
            System.out.println("Connection to Sqlite Succesfully");
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
    public ArrayList<String> inputData(){
        ArrayList<String > myList = new ArrayList<String>();
        try {
            this.statement = connection.createStatement();
            ResultSet Result  =this.statement.executeQuery("SELECT * FROM av");
            while(Result.next()){
                int id = Result.getInt("id");
                String Word = Result.getString("word");
                myList.add(Word);
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return myList;
    }
    public void closeConnection(){
        try{
            connection.close();
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
    }
    public void excuteQuery(String Query){
        try{
            this.statement = connection.createStatement();
            this.statement.executeQuery(Query);
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
    }

    public String getUrl() {
        return Url;
    }

    public void setUrl(String url) {
        Url = url;
    }

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public Statement getStatement() {
        return statement;
    }

    public void setStatement(Statement statement) {
        this.statement = statement;
    }
}
