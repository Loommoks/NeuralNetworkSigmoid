import java.awt.event.ActionListener;

public class GuiPanelRowArgs {

    public String caption;
    public ActionListener listener;
    public String defaultValue;
    public int defaulTextFieldLenght;

    public GuiPanelRowArgs(String caption, int defaulTextFieldLenght, String defaultValue){
        this.caption = caption;
        this.defaultValue = defaultValue;
        this.defaulTextFieldLenght = defaulTextFieldLenght;
    }

    public GuiPanelRowArgs(String caption, int defaulTextFieldLenght, double defaultValue){
        this.caption = caption;
        this.defaultValue = Double.toString(defaultValue);
        this.defaulTextFieldLenght = defaulTextFieldLenght;
    }

    public GuiPanelRowArgs(String caption, int defaulTextFieldLenght, int defaultValue){
        this.caption = caption;
        this.defaultValue = Integer.toString(defaultValue);
        this.defaulTextFieldLenght = defaulTextFieldLenght;
    }


}
