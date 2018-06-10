import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class ReadAFile {

    ArrayList<Sample> sample = new ArrayList<>();
    Sample tempSample;

    public ArrayList<Sample> read (){
        try {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.showOpenDialog(new JFrame());
            //File myFile = new File("MyTextSamplesDB.txt");
            FileReader fileReader = new FileReader(fileChooser.getSelectedFile());
            BufferedReader reader = new BufferedReader(fileReader);

            String line;
            while ((line = reader.readLine()) !=null){

                String[] separated = line.split("/");
                //Разберем строки семпла иначе для формирования объекта Sample

                for(int index=0;index<separated.length;index++){
                    readConvertor(separated[index],index);
                }

            }
            reader.close();

        } catch (Exception ex){
            ex.printStackTrace();
        }
        return sample;
    }

    public void readConvertor(String dataToConvert, int index){
        if (index==0){
            String[] inputI = dataToConvert.split(",");
            double[] dataToTransfer = new double[inputI.length];
            for (int i=0;i<inputI.length;i++){
                dataToTransfer[i] = Double.parseDouble(inputI[i]);
            }
            Sample sampleI = new Sample();
            sampleI.setSampleIn(dataToTransfer);
            tempSample = sampleI;
        } else {
            String[] outputI = dataToConvert.split(",");
            double[] dataToTransfer = new double[outputI.length];
            for (int i=0;i<outputI.length;i++){
                dataToTransfer[i] = Double.parseDouble(outputI[i]);
            }
            tempSample.setSampleOut(dataToTransfer);
            sample.add(tempSample);
        }
        //System.out.println("Длина массива семплов: "+sample.size());

    }

    public static double[][] arrayListToArrayConvertorInput(ArrayList<Sample> samplesToConvert){
        double[][] samplesInputs = new double[samplesToConvert.size()][];
        for(int i=0;i<samplesToConvert.size();i++) {
            samplesInputs[i]=new double[samplesToConvert.get(i).getInputDimension()];
            for (int j=0;j<samplesToConvert.get(i).getInputDimension();j++){
                samplesInputs[i][j]=samplesToConvert.get(i).getSampleInI(j);
            }
            //System.out.println();
            /*for (int j=0;j<samples.get(i).getOutputDimension();j++){
                System.out.print("Output["+j+"] "+samples.get(i).getSampleOutI(j)+" ");
            }
            System.out.println();*/
        }
        return samplesInputs;
    }

    public static double[][] arrayListToArrayConvertorOutput(ArrayList<Sample> samplesToConvert){
        double[][] samplesOutputs = new double[samplesToConvert.size()][];
        for(int i=0;i<samplesToConvert.size();i++) {
            samplesOutputs[i]=new double[samplesToConvert.get(i).getOutputDimension()];
            for (int j=0;j<samplesToConvert.get(i).getOutputDimension();j++){
                samplesOutputs[i][j]=samplesToConvert.get(i).getSampleOutI(j);
            }
        }
        return samplesOutputs;
    }

    /*public void actionPerformed(ActionEvent a) {
        this.read();
    }*/
}
