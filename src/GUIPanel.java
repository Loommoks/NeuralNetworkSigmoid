import Neurons.HiddenNeuron;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.function.DoubleConsumer;


public class GUIPanel {
    JFrame frame;
    static JTextArea text;
    Network net;
    ArrayList<Sample> samples;
    //int[] mnistLabelstemp;
    //int[][] mnistImagestemp;
    //LinkedList<double[]> mnistLabels;
    //LinkedList<double[]> mnistImages;
    LinkedList<Sample> mnistSamples = new LinkedList<>();


    public static void main (String[] args) {
        GUIPanel gui = new GUIPanel();
        gui.go();
    }

    public void go(){
        frame = new JFrame();
        JPanel panelRight = new JPanel();
        JPanel panelLeft = GuiFactory.makeLeftColum();
        panelRight.setLayout(new BoxLayout(panelRight, BoxLayout.Y_AXIS));
        //panelRight.setLayout(new GridLayout(10,2,5,20));

        //--Right Panel Buttons
        JButton buttonReadFile = new JButton("Load Learning Samples DB");
        //buttonReadFile.setPreferredSize(new Dimension(100,50));
        buttonReadFile.addActionListener(new fileOpener());

        JButton buttonStartNetworkWithSamples = new JButton("Start Train With Samples");
        buttonStartNetworkWithSamples.addActionListener(new startNetwork());

        JButton buttonLoadTestSample = new JButton("Load Test Sample");
        buttonLoadTestSample.addActionListener(new testSampleLoader());

        JButton buttonSaveNetworkParameters = new JButton("Save Network");
        buttonSaveNetworkParameters.addActionListener(new networkSaver());

        JButton buttonLoadNetwork = new JButton("Load Network");
        buttonLoadNetwork.addActionListener(new networkLoader());

        JButton buttonLoadMnistSamples = new JButton("Load MNIST Samples");
        buttonLoadMnistSamples.addActionListener(new mnistSamplesLoader());

        JButton buttonStartTrainingWithMnist = new JButton("Start Training MNIST");
        buttonStartTrainingWithMnist.addActionListener(new mnistTrainingStarter());

        JButton buttonMnistTestSamplesLoader = new JButton("Load&Start Testing MNIST");
        buttonMnistTestSamplesLoader.addActionListener(new mnistTestSamplesLoader());

        //--
        //--Left Panel Buttons

        JButton buttonContinueMNISTLearning = new JButton("Continue MNIST Learning");
        buttonContinueMNISTLearning.addActionListener(new mnistSamplesLoaderToContinue());


        //--
        JPanel graficPanel = new JPanel();
        graficPanel.setLayout(new BoxLayout(graficPanel, BoxLayout.Y_AXIS));
        pictureConstructor(graficPanel);
        frame.getContentPane().add(BorderLayout.CENTER,graficPanel);

        JPanel conditionPanel = new JPanel();
        conditionPanel.setBackground(Color.DARK_GRAY);
        conditionPanel.setLayout(new BoxLayout(conditionPanel, BoxLayout.Y_AXIS));
        stateConstructor(conditionPanel,"Networkwer v.2.06.08"+"\n");
        writeDefaultSetup();
        frame.getContentPane().add(BorderLayout.SOUTH,conditionPanel);


        panelRight.add(buttonReadFile);
        panelRight.add(buttonStartNetworkWithSamples);
        panelRight.add(buttonLoadTestSample);
        panelRight.add(buttonSaveNetworkParameters);
        panelRight.add(buttonLoadNetwork);
        panelRight.add(buttonLoadMnistSamples);
        panelRight.add(buttonStartTrainingWithMnist);
        panelRight.add(buttonContinueMNISTLearning);
        panelRight.add(buttonMnistTestSamplesLoader);
        panelRight.setBackground(Color.DARK_GRAY);


        frame.getContentPane().add(BorderLayout.EAST,panelRight);
        frame.getContentPane().add(BorderLayout.WEST,panelLeft);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.setSize(900,600);

        //net = new Network();
        //net.addObserver(this);
    }

    public void pictureConstructor(JPanel graficPanel){
        graficPanel.setBackground(Color.GRAY);
    }

    public void stateConstructor(JPanel conditionPanel, String condition){
        text = new JTextArea(11,30);
        text.setBackground(Color.DARK_GRAY);
        text.setLineWrap(false);
        text.setForeground(Color.ORANGE);;
        JScrollPane scroller = new JScrollPane(text);
        scroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        conditionPanel.add(scroller);
        text.append(condition);
    }

    public class startNetwork implements ActionListener {
        public void actionPerformed(ActionEvent a) {
            net = new Network();
            double[][] inputs = ReadAFile.arrayListToArrayConvertorInput(samples);
            double[][] answers = ReadAFile.arrayListToArrayConvertorOutput(samples);
            NetworkStarter.Start(net,inputs,answers);
        }
    }

    public class fileOpener implements ActionListener {
        public void actionPerformed(ActionEvent a) {
            ReadAFile readAFile = new ReadAFile();
            samples = readAFile.read();
            printSamples();
        }
    }

    public class testSampleLoader implements ActionListener {
        public void actionPerformed(ActionEvent a) {
            ReadAFile readAFile = new ReadAFile();
            samples = readAFile.read();

            double[][] input = ReadAFile.arrayListToArrayConvertorInput(samples);
            double[][] output = ReadAFile.arrayListToArrayConvertorOutput(samples);
            for (int k=0; k<input.length;k++) {
                net.startNetworkingWithTestSample(input[k],output[k]);
            }
        }
    }

    public class networkSaver implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            WrireAFile wrireAFile = new WrireAFile();
            wrireAFile.autoSave(net,0);
            //wrireAFile.write(net);
        }
    }

    public class networkLoader implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            LoadNetwork loadNetwork = new LoadNetwork();
            net = loadNetwork.go();
        }
    }

    public class mnistSamplesLoader implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            int[] labelsToConvert = MnistReader.getLabels("C:\\train-labels.idx1-ubyte");
            List<int[][]> imagesToConvert = MnistReader.getImages("C:\\train-images.idx3-ubyte");

            for (int i=0;i<10000;i++){
                double[] temp = new double[10];
                temp[labelsToConvert[i]]=1;
                mnistSamples.add(new Sample());
                mnistSamples.get(i).setSampleOut(temp);
                mnistSamples.get(i).setSampleIn(convert2DIntegerArraryTo1DDoubleArray(imagesToConvert.get(i)));
            }
        }
    }

    public class mnistSamplesLoaderToContinue implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            int[] labelsToConvert = MnistReader.getLabels("C:\\train-labels.idx1-ubyte");
            List<int[][]> imagesToConvert = MnistReader.getImages("C:\\train-images.idx3-ubyte");

            for (int i=0;i<60000;i++){
                double[] temp = new double[10];
                temp[labelsToConvert[i]]=1;
                mnistSamples.add(new Sample());
                mnistSamples.get(i).setSampleOut(temp);
                mnistSamples.get(i).setSampleIn(convert2DIntegerArraryTo1DDoubleArray(imagesToConvert.get(i)));
            }

            WrireAFile wrireAFile = new WrireAFile();
            double[] savePoints =  {0.91, 0.92, 0.93, 0.94, 0.95, 0.955, 0.96, 0.965, 0.97, 0.975, 0.98 };
            for (double offset: savePoints) {
                NetworkStarter.Start(net, mnistSamples, offset, false);
                wrireAFile.autoSave(net, (int) (offset * 1000));
            }
        }
    }

    public class mnistTestSamplesLoader implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            int[] labelsToConvert = MnistReader.getLabels("C:\\t10k-labels.idx1-ubyte");
            List<int[][]> imagesToConvert = MnistReader.getImages("C:\\t10k-images.idx3-ubyte");

            for (int i=0;i<labelsToConvert.length;i++){
                double[] temp = new double[10];
                temp[labelsToConvert[i]]=1;
                mnistSamples.add(new Sample());
                mnistSamples.get(i).setSampleOut(temp);
                mnistSamples.get(i).setSampleIn(convert2DIntegerArraryTo1DDoubleArray(imagesToConvert.get(i)));
            }
            int rightAnswersCounter=0;
            double percentage;

            for (int i=0;i<mnistSamples.size();i++){
                if (net.startNetworkingWithTestSampleAllIn(
                        mnistSamples.get(i).getSampleIn(),
                        mnistSamples.get(i).getSampleOut())
                        ){rightAnswersCounter++;}
            }
            percentage = 100*((double)rightAnswersCounter/mnistSamples.size());
            System.out.println("Процент успешных распознаваний: "+percentage+" "+rightAnswersCounter+"/"+mnistSamples.size());
        }
    }

    public class mnistTrainingStarter implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            net = new Network();
            //NetworkStarter.Start(net,mnistImages,mnistLabels);
            NetworkStarter.Start(net,mnistSamples,0.70,true);
            WrireAFile wrireAFile = new WrireAFile();
            wrireAFile.autoSave(net,700);
            NetworkStarter.Start(net,mnistSamples,0.75,false);
            wrireAFile.autoSave(net,750);
            HiddenNeuron.setALPHA(HiddenNeuron.getAlpha()/5);
            HiddenNeuron.setBETA(HiddenNeuron.getBeta()/10);
            NetworkStarter.Start(net,mnistSamples,0.80,false);
            wrireAFile.autoSave(net,800);
            NetworkStarter.Start(net,mnistSamples,0.85,false);
            wrireAFile.autoSave(net,850);
            HiddenNeuron.setALPHA(HiddenNeuron.getAlpha()/5);
            HiddenNeuron.setBETA(HiddenNeuron.getBeta()/10);
            NetworkStarter.Start(net,mnistSamples,0.90,false);
            wrireAFile.autoSave(net,900);
            NetworkStarter.Start(net,mnistSamples,0.95,false);
            wrireAFile.autoSave(net,950);
            NetworkStarter.Start(net,mnistSamples,0.97,false);
            wrireAFile.autoSave(net,970);
            NetworkStarter.Start(net,mnistSamples,0.98,false);
            wrireAFile.autoSave(net,980);
            NetworkStarter.Start(net,mnistSamples,0.99,false);
            wrireAFile.autoSave(net,990);
            NetworkStarter.Start(net,mnistSamples,0.995,false);
            /*HiddenNeuron.setALPHA(HiddenNeuron.getAlpha()/5);
            HiddenNeuron.setBETA(HiddenNeuron.getBeta()/10);
            NetworkStarter.Start(net,mnistSamples,0.97,false);*/


            wrireAFile.autoSave(net,995);

        }
    }

    public void printSamples(){
        for(int i=0;i<samples.size();i++) {
            System.out.print("Sample["+i+"]: ");
            for (int j=0;j<samples.get(i).getInputDimension();j++){
                System.out.print(samples.get(i).getSampleInI(j)+" ");
            }
            for (int j=0;j<samples.get(i).getOutputDimension();j++){
                System.out.print("Output["+i+"] "+samples.get(i).getSampleOutI(j)+" ");
            }
            System.out.println();
        }
    }

    public void writeDefaultSetup(){
        int[] neuronsInLayers = NetworkStarter.getNeuronsInLayers();
        text.append("Network default settings: \n");
        text.append("Input Layer: "+Integer.toString(neuronsInLayers[0])+"\n");
        for (int i=1;i<neuronsInLayers.length-1;i++) {
            text.append("Hidden Layer ["+i+"]: "+Integer.toString(neuronsInLayers[i])+"\n");
        }
        text.append("Output Layers: "+Integer.toString(neuronsInLayers[neuronsInLayers.length-1])+"\n");
    }

    public class trainBestNetworks implements ActionListener{
        public void actionPerformed(ActionEvent e){

        }
    }

    public static void writeToConsole (String toWrite) {
        text.append(toWrite);
        text.setCaretPosition(text.getDocument().getLength());
    }

    public double[] convert2DIntegerArraryTo1DDoubleArray(int [][] arrayToConvert){
        double[] arrayToReturn = new double[arrayToConvert.length*arrayToConvert[0].length];
        int k=0;
        for(int i=0;i<arrayToConvert.length;i++){
            for (int j=0;j<arrayToConvert[i].length;j++){
                arrayToReturn[k]=arrayToConvert[i][j];
                k++;
            }
        }
        return arrayToReturn;
    }

}
