import Neurons.HiddenNeuron;

import javax.swing.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class WrireAFile {
    private Network net;

    public void write(Network net){
        this.net = net;
        JFileChooser fileSave = new JFileChooser();
        fileSave.showSaveDialog(new JFrame());
        saveFile(fileSave.getSelectedFile());
    }

    public void saveFile(File file){
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(file));
            System.out.println("Пробую сохранить");
            //Сохраняем информацию о количестве слоев в сети и нейронов в каждом слое
            saveNetworkDimension(writer);
            //Сохраняем параметры каждого нейрона входного слоя
            saveInputLayer(writer);
            //Сохраняем параметры каждого нейрона каждого скрытого слоя
            saveHiddenLayers(writer);
            writer.close();
        } catch (IOException ex){
            System.out.println("couldn't write the network out");
            ex.printStackTrace();
        }
    }

    private void saveNetworkDimension(BufferedWriter writer) throws IOException {
        int[] neuronsInLayers = net.getNetworkDimension();
        writer.write("NetworkDimensions/");
        writer.write(Integer.toString(neuronsInLayers.length)+"/");
        for(int k=0;k<neuronsInLayers.length;k++){
            if(k<neuronsInLayers.length-1){
            writer.write(Integer.toString(neuronsInLayers[k])+",");
            } else {
                writer.write(Integer.toString(neuronsInLayers[k]));
            }
        }
        writer.write("\n");
    }

    public void saveInputLayer(BufferedWriter writer) throws IOException {
        for (int j=0;j<net.neuronNet[0].length;j++){
            //Параметры NeuronBase
            saveLayerLevelAndNeuronNumber(writer,0,j);
            saveInputsWeightArray(writer,0,j);
            saveInputsValueArray(writer,0,j);
            saveOut(writer,0,j);
            saveSum(writer,0,j);
            saveInputsSigma(writer,0,j);
            saveSigmaSum(writer,0,j);
            saveSigmaToTransfer(writer,0,j);
            saveVector(writer,0,j);
            saveDW(writer,0,j);
            writer.write("\n");
        }
    }

    public void saveHiddenLayers(BufferedWriter writer) throws IOException {
        for (int i=1;i<net.neuronNet.length;i++){
            for (int j=0;j<net.neuronNet[i].length;j++){
                //Параметры NeuronBase
                saveLayerLevelAndNeuronNumber(writer,i,j);
                saveInputsWeightArray(writer,i,j);
                saveInputsValueArray(writer,i,j);
                saveOut(writer,i,j);
                saveSum(writer,i,j);
                saveInputsSigma(writer,i,j);
                saveSigmaSum(writer,i,j);
                saveSigmaToTransfer(writer,i,j);
                saveVector(writer,i,j);
                saveDW(writer,i,j);
                //Параметры HiddenNeuron
                saveLastDW(writer,i,j);
                //Завершаем строку с параметрами данного нейрона
                writer.write("\n");
            }

        }
    }

    public void saveLayerLevelAndNeuronNumber(BufferedWriter writer, int i,int j) throws IOException {
        writer.write(Integer.toString(i));
        writer.write(",");
        writer.write(Integer.toString(j));
        writer.write("/");
    }

    public void saveInputsWeightArray (BufferedWriter writer, int i, int j) throws IOException{
        writer.write("inputsWeightArray/");
        double[] inputsWeight = net.neuronNet[i][j].getInputWeight();
        for (int k=0;k<inputsWeight.length;k++){
            if (k<inputsWeight.length-1){
                writer.write(Double.toString(inputsWeight[k])+",");
            } else {
                writer.write(Double.toString(inputsWeight[k]));
            }
        }
        writer.write("/");
    }

    public void saveInputsValueArray (BufferedWriter writer, int i, int j) throws IOException{
        writer.write("inputsValueArray/");
        double[] inputsValue = net.neuronNet[i][j].getInputsValue();
        for (int k=0;k<inputsValue.length;k++){
            if (k<inputsValue.length-1){
                writer.write(Double.toString(inputsValue[k])+",");
            } else {
                writer.write(Double.toString(inputsValue[k]));
            }
        }
        writer.write("/");
    }

    public void saveOut (BufferedWriter writer, int i, int j) throws IOException {
        writer.write("out/");
        writer.write(Double.toString(net.neuronNet[i][j].getOut())+"/");
    }

    public void saveSum (BufferedWriter writer, int i, int j) throws IOException {
        writer.write("sum/");
        writer.write(Double.toString(net.neuronNet[i][j].getSum())+"/");
    }

    public void saveInputsSigma (BufferedWriter writer, int i, int j) throws IOException{
        writer.write("inputsSigma/");
        double[] inputsSigma = net.neuronNet[i][j].getInputsSigma();
        for (int k=0;k<inputsSigma.length;k++){
            if (k<inputsSigma.length-1){
                writer.write(Double.toString(inputsSigma[k])+",");
            } else {
                writer.write(Double.toString(inputsSigma[k]));
            }
        }
        writer.write("/");
    }

    public void saveSigmaSum (BufferedWriter writer, int i, int j) throws IOException {
        writer.write("sigmaSum/");
        writer.write(Double.toString(net.neuronNet[i][j].getSigmaSum())+"/");
    }

    public void saveSigmaToTransfer (BufferedWriter writer, int i, int j) throws IOException {
        writer.write("sigmaToTransfer/");
        writer.write(Double.toString(net.neuronNet[i][j].getSigmaToTrasfer())+"/");
    }

    public void saveVector (BufferedWriter writer, int i, int j) throws IOException{
        writer.write("vector/");
        double[] vector = net.neuronNet[i][j].getVector();
        for (int k=0;k<vector.length;k++){
            if (k<vector.length-1){
                writer.write(Double.toString(vector[k])+",");
            } else {
                writer.write(Double.toString(vector[k]));
            }
        }
        writer.write("/");
    }

    public void saveDW (BufferedWriter writer, int i, int j) throws IOException{
        writer.write("dW/");
        double[] dW = net.neuronNet[i][j].getdW();
        for (int k=0;k<dW.length;k++){
            if (k<dW.length-1){
                writer.write(Double.toString(dW[k])+",");
            } else {
                writer.write(Double.toString(dW[k]));
            }
        }
        writer.write("/");
    }

    public void saveLastDW (BufferedWriter writer, int i, int j) throws IOException{
        writer.write("lastDW/");
        HiddenNeuron hiddenNeuron = (HiddenNeuron) net.neuronNet[i][j];
        double[] lastDW = hiddenNeuron.getLastDW();
        for (int k=0;k<lastDW.length;k++){
            if (k<lastDW.length-1){
                writer.write(Double.toString(lastDW[k])+",");
            } else {
                writer.write(Double.toString(lastDW[k]));
            }
        }
        writer.write("/");
    }

}
