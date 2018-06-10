import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class GuiRow {

    private final JLabel label;
    private final GridBagConstraints labelSetting;

    public GuiRow(GuiPanelRowArgs args){
        labelSetting = createDefaulsConstrains();
        labelSetting.gridx = 1;
        labelSetting.anchor = GridBagConstraints.WEST;
        labelSetting.gridwidth = 1;
        label =  makeJLabel(args.caption);


    }

    public void AddTo(JPanel panel){
        panel.add(label, labelSetting);
    }

    public void Subscribe(ActionListener listener){

    }

    private static void addRowTo(JPanel panel, GuiPanelRowArgs args){
        GridBagConstraints labelSetting = createDefaulsConstrains();
        labelSetting.gridx = 1;
        labelSetting.anchor = GridBagConstraints.WEST;
        labelSetting.gridwidth = 1;

        panel.add(,labelSetting);

        GridBagConstraints textFieldSettings = createDefaulsConstrains();
        textFieldSettings.gridx = GridBagConstraints.RELATIVE;
        textFieldSettings.gridwidth = GridBagConstraints.REMAINDER;

        JTextField fieldInputCounter = makeTextField(args.defaulTextFieldLenght);
        fieldInputCounter.setText(args.defaultValue);
        fieldInputCounter.addActionListener(args.listener);
        panel.add(fieldInputCounter,textFieldSettings);

    }

    public static JLabel makeJLabel (String string){
        JLabel label = new JLabel(string);
        label.setForeground(Color.GREEN);
        return label;
    }

    private static GridBagConstraints createDefaulsConstrains(){
        GridBagConstraints defaultConstrains = new GridBagConstraints();
        defaultConstrains.anchor = GridBagConstraints.CENTER;
        defaultConstrains.ipadx = 5;
        defaultConstrains.ipady = 5;
        return defaultConstrains;
    }

}
