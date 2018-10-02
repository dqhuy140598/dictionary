package Dictionary;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;

public class Dictionary {
    private HashMap<String,String> dictionary = new HashMap<String,String>();
    private ArrayList<String> Words = new ArrayList<String>();
    private ArrayList<String> Defination = new ArrayList<String>();
    public void readDataFormFile(){
        FileInputStream fileIn = null;
        try{
            fileIn = new FileInputStream("src/DataBase/E_V.txt");
            BufferedReader Read = new BufferedReader(new InputStreamReader(fileIn,"utf-8"));
            String Line;
            int numOfWords = 0;
            while((Line = Read.readLine())!=null){
                int index = Line.indexOf("<i>");
                if(index!=-1){
                    String Html = "<html>";
                    String Word = Line.substring(0,index - Html.length());
                    Word.trim();
                    String Defination = Line.substring(index);
                    Defination = "<html>" + Defination;
                    dictionary.put(Word,Defination);
                    Words.add(Word);
                    numOfWords++;
                }
            }
            System.out.println(numOfWords);
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
    public String getWordsDefination(String Word){
        if(Words.indexOf(Word)==-1) return "Invalid";
        else{
            String Defination = dictionary.get(Word);
            return Defination;
        }
    }
    public HashMap<String, String> getDictionary() {
        return dictionary;
    }

    public void setDictionary(HashMap<String, String> dictionary) {
        this.dictionary = dictionary;
    }

    public ArrayList<String> getWords() {
        return Words;
    }

    public void setWords(ArrayList<String> words) {
        Words = words;
    }

    public ArrayList<String> getDefination() {
        return Defination;
    }

    public void setDefination(ArrayList<String> defination) {
        Defination = defination;
    }
}
