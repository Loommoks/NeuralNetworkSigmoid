import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class GuiRowFactory {
    private JLabel labelName;
    private JTextField textField;

    public String getFieldText(){
        return textField.getText();
    }

    public GuiRowFactory(GuiPanelRowArgs args){
        labelName = makeJLabel(args.caption);
        textField = createTextField(args.defaulTextFieldLenght, args.defaultValue);
    }

    public GuiRowFactory(String chapterName){
        labelName = makeJLabel(chapterName);
        Font chapterfont = new Font(null,Font.BOLD,16);
        labelName.setFont(chapterfont);
    }

    public void addTo(JPanel panel){
        GridBagConstraints constraints = createDefaultConstrains();
        panel.add(labelName,constraints);

        constraints.gridwidth = GridBagConstraints.REMAINDER;
        constraints.anchor = GridBagConstraints.CENTER;
        panel.add(textField,constraints);
    }

    public void addChapterTo(JPanel panel){
        GridBagConstraints constraints = createDefaultConstrains();
        constraints.gridwidth = GridBagConstraints.REMAINDER;
        constraints.anchor = GridBagConstraints.CENTER;
        panel.add(labelName,constraints);
    }

    public static JLabel makeJLabel (String string){
        JLabel label = new JLabel(string);
        label.setForeground(Color.GREEN);
        return label;
    }

    private static GridBagConstraints createDefaultConstrains(){
        GridBagConstraints defaultConstrains = new GridBagConstraints();
        defaultConstrains.anchor = GridBagConstraints.WEST;
        //defaultConstrains.ipadx = 5;
        //defaultConstrains.ipady = 5;
        return defaultConstrains;
    }

    public static JTextField createTextField (int lenght, String defaultValue){
        JTextField textField = new JTextField(lenght);
        textField.setBackground(Color.BLACK);
        textField.setForeground(Color.GREEN);
        Font font = new Font(null,Font.BOLD,14);
        textField.setBorder(null);
        textField.setFont(font);
        textField.setMaximumSize(new Dimension(50,20));
        textField.setText(defaultValue);
        return textField;
    }

    public void subscribe(ActionListener listener){
        textField.addActionListener(listener);
        //todo Тут происходит подписка на уже созданный textField
    }

}
