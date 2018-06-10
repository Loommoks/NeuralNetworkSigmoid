import Neurons.HiddenNeuron;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GuiStyler {
    public static JPanel makeLeftColum(){
        JPanel panelLeftToReturn = new JPanel();
        panelLeftToReturn.setLayout(new BoxLayout(panelLeftToReturn, BoxLayout.Y_AXIS));
        JPanel panelLeft = new JPanel();
        panelLeft.setBackground(Color.DARK_GRAY);
        //panelLeft.setLayout(new BoxLayout(panelLeft, BoxLayout.Y_AXIS));

        GridBagLayout layout = new GridBagLayout();
        panelLeft.setLayout(layout);
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.anchor = GridBagConstraints.CENTER;
        constraints.ipadx = 5;
        constraints.ipady = 5;
        panelLeft.setBorder(new EmptyBorder(10,10,10,10));

        //--Первая строка сетки
        constraints.gridwidth = GridBagConstraints.REMAINDER;
        //constraints.gridheight =2;
        JLabel one = makeJLabel("Settings");
        panelLeft.add(one,constraints);

        //--Вторая строка сетки

        constraints.gridx = 1;
        constraints.anchor = GridBagConstraints.WEST;
        constraints.gridwidth = 1;
        //constraints.gridheight = 1;
        JLabel two = makeJLabel("Input neurons");
        panelLeft.add(two,constraints);

        constraints.gridx = GridBagConstraints.RELATIVE;
        constraints.gridwidth = GridBagConstraints.REMAINDER;

        JTextField fieldInputCounter = makeTextField(2);
        fieldInputCounter.setBorder(null);
        fieldInputCounter.setText(Integer.toString(NetworkStarter.getInputsCount()));
        fieldInputCounter.addActionListener(e -> NetworkStarter.setInputsCount(Integer.parseInt(fieldInputCounter.getText())));
        panelLeft.add(fieldInputCounter,constraints);


        //--Третья строка сетки
        constraints.gridwidth = 1;
        JCheckBox checkBoxHiddenLayerOne = makeCheckBox();
        panelLeft.add(checkBoxHiddenLayerOne,constraints);

        JLabel labelHiddenLayerOne =makeJLabel("Hidden layer 1 neurons");
        panelLeft.add(labelHiddenLayerOne,constraints);

        constraints.gridwidth = GridBagConstraints.REMAINDER;
        JTextField textFieldHiddenLayerOne = makeTextField(2);
        textFieldHiddenLayerOne.setBorder(null);
        textFieldHiddenLayerOne.setText(Integer.toString(NetworkStarter.getHiddenLayerOneCount()));
        panelLeft.add(textFieldHiddenLayerOne,constraints);

        //--Четвертая строка сетки
        constraints.gridwidth = 1;
        JCheckBox checkBoxHiddenLayerTwo = makeCheckBox();
        panelLeft.add(checkBoxHiddenLayerTwo, constraints);

        JLabel labelHiddenLayerTwo = makeJLabel("Hidden layer 2 neurons");
        panelLeft.add(labelHiddenLayerTwo,constraints);

        constraints.gridwidth = GridBagConstraints.REMAINDER;
        JTextField textFieldHiddenLayerTwo = makeTextField(2);
        textFieldHiddenLayerTwo.setBorder(null);
        textFieldHiddenLayerTwo.setText(Integer.toString(NetworkStarter.getHiddenLayerTwoCount()));
        panelLeft.add(textFieldHiddenLayerTwo,constraints);

        //Пятая строка сетки
        constraints.gridwidth = 1;
        JCheckBox checkBoxHiddenLayerThree = makeCheckBox();
        checkBoxHiddenLayerThree.setSelected(false);
        panelLeft.add(checkBoxHiddenLayerThree, constraints);

        JLabel labelHiddenLayerThree = makeJLabel("Hidden layer 3 neurons");
        panelLeft.add(labelHiddenLayerThree,constraints);

        constraints.gridwidth = GridBagConstraints.REMAINDER;
        JTextField textFieldHiddenLayerThree = makeTextField(2);
        textFieldHiddenLayerThree.setBorder(null);
        panelLeft.add(textFieldHiddenLayerThree,constraints);

        //--Шестая строка сетки
        constraints.gridx = 1;
        constraints.anchor = GridBagConstraints.WEST;
        constraints.gridwidth = 1;
        JLabel labelOutputLayer = makeJLabel("Output neurons");
        panelLeft.add(labelOutputLayer,constraints);

        constraints.gridx = GridBagConstraints.RELATIVE;
        constraints.gridwidth = GridBagConstraints.REMAINDER;
        JTextField fieldOutputCounter = makeTextField(2);
        fieldOutputCounter.setText(Integer.toString(NetworkStarter.getOutputsCount()));
        fieldOutputCounter.setBorder(null);
        panelLeft.add(fieldOutputCounter,constraints);

        //--Seven String of Grid
        constraints.gridwidth = GridBagConstraints.REMAINDER;
        constraints.anchor=GridBagConstraints.CENTER;
        //constraints.gridheight =2;
        JLabel labelLearningSettings = makeJLabel("Learning Settings");
        panelLeft.add(labelLearningSettings,constraints);

        //Eight String of Grid
        constraints.gridwidth = 1;
        constraints.anchor=GridBagConstraints.WEST;
        JCheckBox checkBoxLearningAccuracy = makeCheckBox();
        panelLeft.add(checkBoxLearningAccuracy, constraints);
        JLabel labelLearningAccuracy = makeJLabel("Learning accuracy");
        panelLeft.add(labelLearningAccuracy,constraints);

        constraints.gridwidth = GridBagConstraints.REMAINDER;
        JTextField textFieldLearningAccuracy = makeTextField(4);
        textFieldLearningAccuracy.setText(Double.toString(NetworkStarter.getNetworkLearnAccuracy()));
        textFieldLearningAccuracy.addActionListener(
                e -> NetworkStarter.setNetworkLearnAccuracy(Double.parseDouble(textFieldLearningAccuracy.getText())));

        panelLeft.add(textFieldLearningAccuracy,constraints);

        //--Ninth row
        constraints.gridwidth = 1;
        constraints.anchor=GridBagConstraints.WEST;
        JCheckBox checkBoxLearningSpeed = makeCheckBox();
        panelLeft.add(checkBoxLearningSpeed, constraints);

        JLabel labelLearningSpeed = makeJLabel("Learning speed");
        panelLeft.add(labelLearningSpeed,constraints);

        constraints.gridwidth = GridBagConstraints.REMAINDER;
        JTextField textFieldLearningSpeed = makeTextField(2);
        textFieldLearningSpeed.setText(Double.toString(HiddenNeuron.getAlpha()));
        textFieldLearningSpeed.addActionListener(e -> {
            HiddenNeuron.setALPHA(Double.parseDouble(textFieldLearningSpeed.getText()));
            GUIPanel.writeToConsole("Установлено новая скорость обучения сети: "+Double.toString(HiddenNeuron.getAlpha())+"\n");
        });
        panelLeft.add(textFieldLearningSpeed,constraints);

        //Ten Row
        constraints.gridwidth = 1;
        constraints.anchor=GridBagConstraints.WEST;
        JCheckBox checkBoxInertness = makeCheckBox();
        panelLeft.add(checkBoxInertness, constraints);

        JLabel labelInertness = makeJLabel("Learning Inertness");
        panelLeft.add(labelInertness,constraints);

        constraints.gridwidth = GridBagConstraints.REMAINDER;
        JTextField textFieldInertness = makeTextField(4);
        textFieldInertness.setText(Double.toString(HiddenNeuron.getBeta()));
        textFieldInertness.addActionListener(e -> {
            HiddenNeuron.setBETA(Double.parseDouble(textFieldInertness.getText()));
            GUIPanel.writeToConsole("Установлено новая инерция обучения сети: "+Double.toString(HiddenNeuron.getBeta())+"\n");
        });
        panelLeft.add(textFieldInertness,constraints);

        //--Eleventh row
        constraints.gridwidth = GridBagConstraints.REMAINDER;
        constraints.anchor=GridBagConstraints.CENTER;
        JLabel labelSelectionSettings = makeJLabel("Selection Settings");
        panelLeft.add(labelSelectionSettings,constraints);

        //--Twelve row
        constraints.gridx = 1;
        constraints.anchor = GridBagConstraints.WEST;
        constraints.gridwidth = 1;
        JLabel networksQTY = makeJLabel("Networks Qty");
        panelLeft.add(networksQTY,constraints);

        constraints.gridx = GridBagConstraints.RELATIVE;
        constraints.gridwidth = GridBagConstraints.REMAINDER;
        JTextField fieldnetworksQTY = makeTextField(2);
        fieldnetworksQTY.setText(Integer.toString(BestNetworkTrainer.getNetworksToTestAmount()));
        fieldnetworksQTY.addActionListener(e -> BestNetworkTrainer.setNetworksToTestAmount(Integer.parseInt(fieldnetworksQTY.getText())));
        panelLeft.add(fieldnetworksQTY,constraints);

        //--Threeteen row
        constraints.gridx = 1;
        constraints.anchor = GridBagConstraints.WEST;
        constraints.gridwidth = 1;
        JLabel selectionWinners = makeJLabel("Selection Winners");
        panelLeft.add(selectionWinners,constraints);

        constraints.gridx = GridBagConstraints.RELATIVE;
        constraints.gridwidth = GridBagConstraints.REMAINDER;
        JTextField fieldSelectionWinners = makeTextField(2);
        fieldSelectionWinners.setText(Integer.toString(BestNetworkTrainer.getBestNetworksAmount()));
        fieldSelectionWinners.addActionListener(e -> BestNetworkTrainer.setBestNetworksAmount(Integer.parseInt(fieldSelectionWinners.getText())));
        panelLeft.add(fieldSelectionWinners,constraints);


        /*
        panelLeft.add(makeLabelHolder("Настройки сети"),BorderLayout.NORTH);

        panelLeft.add(makeInputHolder(),BorderLayout.NORTH);

        panelLeft.add(makeHiddenLayerOneHolder(),BorderLayout.NORTH);

        panelLeft.add(makeHiddenLayerTwoHolder(),BorderLayout.NORTH);

        panelLeft.add(makeOutputHolder(),BorderLayout.NORTH);

        panelLeft.add(makeLabelHolder("Настройки обучения"),BorderLayout.NORTH);

        panelLeft.add(makeAlphaHolder(),BorderLayout.NORTH);

        panelLeft.add(makeBetaHolder(),BorderLayout.NORTH);

        panelLeft.add(makeLabelHolder("Настройка выборки"),BorderLayout.NORTH);

        panelLeft.add(makeNetworksAmountHolder(),BorderLayout.NORTH);

        panelLeft.add(makeWinnersAmountHolder(),BorderLayout.NORTH);

        panelLeft.add(makeEndPanel(),BorderLayout.CENTER);
        */

        constraints.gridwidth = GridBagConstraints.REMAINDER;
        JButton buttonTrainBestNetworks = new JButton("Train Best Networks");
        buttonTrainBestNetworks.addActionListener(e -> {
            BestNetworkTrainer trainer = new BestNetworkTrainer();
            trainer.runTest();
        });
        panelLeft.add(buttonTrainBestNetworks,constraints);
        //buttonTrainBestNetworks.addActionListener(new GUIPanel.trainBestNetworks());


        /*JPanel panelLeftShifter = new JPanel();
        panelLeftShifter.setLayout(new BoxLayout(panelLeftShifter, BoxLayout.Y_AXIS));
        panelLeft.add(panelLeftShifter);
        panelLeftShifter.add(labelNetworkSettings);*/

        //panelLeft.add(buttonTrainBestNetworks);
        panelLeftToReturn.add(panelLeft);
        return panelLeftToReturn;
    }

    public static JLabel makeLabelHolder(String string){
        JLabel labelNetworkSettings = new JLabel(string);
        Font fontParagraf = new Font(null,Font.BOLD,14);
        labelNetworkSettings.setFont(fontParagraf);
        labelNetworkSettings.setForeground(Color.GREEN);
        labelNetworkSettings.setHorizontalAlignment(JLabel.CENTER);
        return labelNetworkSettings;
    }

    public static JCheckBox makeCheckBox (){
        JCheckBox hiddenLayerOneUsageCheckBox = new JCheckBox();
        hiddenLayerOneUsageCheckBox.setSelected(true);
        hiddenLayerOneUsageCheckBox.setBackground(Color.DARK_GRAY);
        return hiddenLayerOneUsageCheckBox;
    }

    public static JLabel makeJLabel (String string){
        JLabel label = new JLabel(string);
        label.setForeground(Color.GREEN);
        return label;
    }

    public static JTextField makeTextField (int lenght){
        JTextField textField = new JTextField(lenght);
        textField.setBackground(Color.BLACK);
        textField.setForeground(Color.GREEN);
        Font font = new Font(null,Font.BOLD,14);
        textField.setBorder(null);
        textField.setFont(font);
        textField.setMaximumSize(new Dimension(50,20));
        //textField.setText();
        return textField;
    }

    public static JPanel makeTwoColumsPanel(){
        JPanel twoColumnsPanel = new JPanel();
        //twoColumnsPanel.setLayout(new BoxLayout(twoColumnsPanel, BoxLayout.X_AXIS));
        twoColumnsPanel.setBackground(Color.DARK_GRAY);
        return twoColumnsPanel;
    }

    public static JPanel makeThreeColumsPanel(){
        JPanel threeColumnsPanel = new JPanel();
        //threeColumnsPanel.setLayout(new GridLayout(0,3,5,5));
        threeColumnsPanel.setBackground(Color.DARK_GRAY);
        return threeColumnsPanel;
    }

    public static JPanel makeInputHolder (){
        JPanel inputHolder = makeTwoColumsPanel();

        inputHolder.add(makeJLabel("Входной слой "));

        JTextField fieldInputCounter = makeTextField(3);
        inputHolder.add(fieldInputCounter);

        return inputHolder;
    }

    public static JPanel makeHiddenLayerOneHolder(){
        JPanel hiddenLayer1Holder = makeThreeColumsPanel();

        JCheckBox hiddenLayerOneUsageCheckBox = makeCheckBox();
        hiddenLayer1Holder.add(hiddenLayerOneUsageCheckBox);

        hiddenLayer1Holder.add(makeJLabel("Скрытый слой 1"));

        JTextField fieldInputCounter = makeTextField(3);
        hiddenLayer1Holder.add(fieldInputCounter);

        return hiddenLayer1Holder;
    }

    public static JPanel makeEndPanel(){
        JPanel endPanel = new JPanel();
        endPanel.add(new JTextField());
        //EndPanel.setLayout(new BoxLayout(EndPanel, BoxLayout.X_AXIS));
        endPanel.setBackground(Color.DARK_GRAY);
        return endPanel;
    }

    public static JPanel makeHiddenLayerTwoHolder(){
        JPanel HiddenLayer1Holder = makeThreeColumsPanel();

        JCheckBox hiddenLayerTwoUsageCheckBox = makeCheckBox();
        HiddenLayer1Holder.add(hiddenLayerTwoUsageCheckBox);

        HiddenLayer1Holder.add(makeJLabel("Скрытый слой 2"));

        JTextField fieldInputCounter = makeTextField(3);
        HiddenLayer1Holder.add(fieldInputCounter);

        return HiddenLayer1Holder;
    }

    public static JPanel makeOutputHolder (){
        JPanel outputHolder = makeTwoColumsPanel();

        outputHolder.add(makeJLabel("Выходной слой "));

        JTextField fieldInputCounter = makeTextField(3);
        outputHolder.add(fieldInputCounter);

        return outputHolder;
    }

    public static JPanel makeAlphaHolder (){
        JPanel alphaHolder = makeThreeColumsPanel();

        JCheckBox alphaUsageCheckBox = makeCheckBox();
        alphaHolder.add(alphaUsageCheckBox);

        alphaHolder.add(makeJLabel("Скорость обучения"));

        JTextField alphaMeasure = makeTextField(3);
        alphaHolder.add(alphaMeasure);

        return alphaHolder;
    }

    public static JPanel makeBetaHolder (){
        JPanel betaHolder = makeThreeColumsPanel();

        JCheckBox betaUsageCheckBox = makeCheckBox();
        betaHolder.add(betaUsageCheckBox);

        betaHolder.add(makeJLabel("Инерция"));

        JTextField betaMeasure = makeTextField(3);
        betaHolder.add(betaMeasure);

        return betaHolder;
    }

    public static JPanel makeNetworksAmountHolder (){
        JPanel netAmount = makeTwoColumsPanel();

        netAmount.add(makeJLabel("Количество сетей "));

        JTextField netAmountMeasure = makeTextField(3);
        netAmount.add(netAmountMeasure);

        return netAmount;
    }

    public static JPanel makeWinnersAmountHolder(){
        JPanel winnersAmount = makeTwoColumsPanel();

        winnersAmount.add(makeJLabel("Сохранить сетей "));

        JTextField winnersAmountMeasure = makeTextField(3);
        winnersAmount.add(winnersAmountMeasure);

        return winnersAmount;
    }

}
