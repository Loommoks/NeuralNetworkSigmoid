import Neurons.HiddenNeuron;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class GuiFactory {
    public static JPanel makeLeftColum(){

        JPanel panelLeft = new JPanel();
        panelLeft.setBackground(Color.DARK_GRAY);

        GridBagLayout layout = new GridBagLayout();
        panelLeft.setLayout(layout);
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.anchor = GridBagConstraints.CENTER;
        constraints.ipadx = 5;
        constraints.ipady = 5;
        panelLeft.setBorder(new EmptyBorder(10,10,10,10));

        //--Первая строка сетки
        GuiRowFactory labelSettings = new GuiRowFactory("Settings");
        labelSettings.addChapterTo(panelLeft);

        //--Вторая строка сетки
        createInputNeuronsRow(panelLeft);

        //--Третья строка сетки
        createRowWithTextField(panelLeft, "Hidden layer 1 neurons", NetworkStarter.getHiddenLayerOneCount());

        //--Четвертая строка сетки
        createRowWithTextField(panelLeft, "Hidden layer 2 neurons", NetworkStarter.getHiddenLayerTwoCount());

        //--Шестая строка сетки
        createRowWithTextField(panelLeft, "Output neurons", NetworkStarter.getOutputsCount());

        //--Seven String of Grid
        GuiRowFactory labelLearningSettings = new GuiRowFactory("Learning Settings");
        labelLearningSettings.addChapterTo(panelLeft);

        //Eight String of Grid
        createLearningAccuracyRow(panelLeft);

        //--Ninth row
        createLearningSpeedRow(panelLeft);

        //Ten Row
        createLearningInertnessRow(panelLeft);

        //--Eleventh row
        GuiRowFactory labelSelectionSettings = new GuiRowFactory("Selection Settings");
        labelSelectionSettings.addChapterTo(panelLeft);

        //--Twelve row
        createNetworksToLearnQtyRow(panelLeft);

        //--Therteen row
        createSelectionWinnersQtyRow(panelLeft);

        //--Fourteen row
        createButtonTrainBestNetworks(panelLeft, constraints);

        return panelLeft;
    }

    private static void createButtonTrainBestNetworks(JPanel panelLeft, GridBagConstraints constraints) {
        constraints.gridwidth = GridBagConstraints.REMAINDER;
        JButton buttonTrainBestNetworks = new JButton("Train Best Networks");
        buttonTrainBestNetworks.addActionListener(e -> {
            BestNetworkTrainer trainer = new BestNetworkTrainer();
            trainer.runTest();
        });

        panelLeft.add(buttonTrainBestNetworks,constraints);
    }

    private static void createSelectionWinnersQtyRow(JPanel panelLeft) {
        GuiPanelRowArgs selectionWinnersQtyRowSettings = new GuiPanelRowArgs(
                "Selection winners Qty",
                2,
                BestNetworkTrainer.getBestNetworksAmount()
        );

        GuiRowFactory selectionWinnersQtyRow = new GuiRowFactory(selectionWinnersQtyRowSettings);

        selectionWinnersQtyRowSettings.listener =
                e -> BestNetworkTrainer.setBestNetworksAmount(Integer.parseInt(selectionWinnersQtyRow.getFieldText()));
        selectionWinnersQtyRow.subscribe(selectionWinnersQtyRowSettings.listener);

        selectionWinnersQtyRow.addTo(panelLeft);
    }

    private static void createNetworksToLearnQtyRow(JPanel panelLeft) {
        GuiPanelRowArgs networksQtyRowSettings = new GuiPanelRowArgs(
                "Networks Qty",
                4,
                BestNetworkTrainer.getNetworksToTestAmount()
        );

        GuiRowFactory networksQtyRow = new GuiRowFactory(networksQtyRowSettings);

        networksQtyRowSettings.listener =
                e -> BestNetworkTrainer.setNetworksToTestAmount(Integer.parseInt(networksQtyRow.getFieldText()));
        networksQtyRow.subscribe(networksQtyRowSettings.listener);

        networksQtyRow.addTo(panelLeft);
    }

    private static void createLearningInertnessRow(JPanel panelLeft) {
        GuiPanelRowArgs learningInertnessRowSettings = new GuiPanelRowArgs(
                "Learning Inertness",
                4,
                HiddenNeuron.getBeta()
        );

        GuiRowFactory learningInertnessRow = new GuiRowFactory(learningInertnessRowSettings);

        learningInertnessRowSettings.listener =
                e -> HiddenNeuron.setBETA(Double.parseDouble(learningInertnessRow.getFieldText()));
        learningInertnessRow.subscribe(learningInertnessRowSettings.listener);

        learningInertnessRow.addTo(panelLeft);
    }

    private static void createLearningSpeedRow(JPanel panelLeft) {
        GuiPanelRowArgs learningSpeedRowSettings = new GuiPanelRowArgs(
                "Learning Speed",
                2,
                HiddenNeuron.getAlpha()
        );

        GuiRowFactory learningSpeedRow = new GuiRowFactory(learningSpeedRowSettings);

        learningSpeedRowSettings.listener =
                e -> HiddenNeuron.setALPHA(Double.parseDouble(learningSpeedRow.getFieldText()));
        learningSpeedRow.subscribe(learningSpeedRowSettings.listener);

        learningSpeedRow.addTo(panelLeft);
    }

    private static void createLearningAccuracyRow(JPanel panelLeft) {
        GuiPanelRowArgs learningAccuracyRowSettings = new GuiPanelRowArgs(
                "Learning accuracy",
                4,
                NetworkStarter.getNetworkLearnAccuracy()
        );

        GuiRowFactory learningAccuracyRow = new GuiRowFactory(learningAccuracyRowSettings);

        learningAccuracyRowSettings.listener =
                e -> NetworkStarter.setNetworkLearnAccuracy(Double.parseDouble(learningAccuracyRow.getFieldText()));
        learningAccuracyRow.subscribe(learningAccuracyRowSettings.listener);

        learningAccuracyRow.addTo(panelLeft);
    }

    private static void createRowWithTextField(JPanel panelLeft, String caption, int hiddenLayerOneCount) {
        GuiPanelRowArgs hiddenLayerOneNeuronsRowSettings = new GuiPanelRowArgs(
                caption,
                2,
                hiddenLayerOneCount
        );

        GuiRowFactory hiddenLayerOneNeuronsRow = new GuiRowFactory(hiddenLayerOneNeuronsRowSettings);

        hiddenLayerOneNeuronsRow.addTo(panelLeft);
    }

    private static void createInputNeuronsRow(JPanel panelLeft) {
        GuiPanelRowArgs inputNeuronsRowSettings = new GuiPanelRowArgs(
                "Input neurons",
                2,
                NetworkStarter.getInputsCount()
        );

        GuiRowFactory inputNeuronsRow = new GuiRowFactory(inputNeuronsRowSettings);

        inputNeuronsRowSettings.listener = e -> NetworkStarter.setInputsCount(Integer.parseInt(inputNeuronsRow.getFieldText()));
        inputNeuronsRow.subscribe(inputNeuronsRowSettings.listener);

        inputNeuronsRow.addTo(panelLeft);
    }

}
