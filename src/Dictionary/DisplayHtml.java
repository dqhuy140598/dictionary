package Dictionary;

import javafx.scene.control.Label;

public class DisplayHtml {
    private String Str;
    private Label labelHtml;
    public String convertToDisplayHtml(){
        String Temp = Str;
        int i = Str.indexOf("<i>");
        Str = Str.substring(0, i-1) + "<br/>" + Str.substring(i);
        Str = "<html>" + Str + "</html>";
        return Temp;
    }
}
