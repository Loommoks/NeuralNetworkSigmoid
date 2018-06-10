import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class GUIPanel {
    JFrame frame;
    static JTextArea text;
    Network net;
    ArrayList<Sample> samples;


    public static void main (String[] args) {
        GUIPanel gui = new GUIPanel();
        gui.go();
    }

    public void go(){
        frame = new JFrame();
        JPanel panelRight = new JPanel();
        JPanel panelLeft = GuiStyler.makeLeftColum();
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
        //--
        //--Left Panel Buttons


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
        text = new JTextArea(6,30);
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
            wrireAFile.write(net);
        }
    }

    public class networkLoader implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            LoadNetwork loadNetwork = new LoadNetwork();
            net = loadNetwork.go();
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

}
