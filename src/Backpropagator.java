import Neurons.HiddenNeuron;

import java.util.Collections;
import java.util.LinkedList;

public class Backpropagator {
    protected Network net;
    protected int[] networkDimensions;
    double networkError;
    int learningIterations=0;


    public Backpropagator (Network net) {
        this.net = net;
        networkDimensions = net.getNetworkDimension();
    }

    //--1-- Считаем вектор весов ошибок для нейрона j
    public double[] calcEWVectorForNeuron (int layerIndex, int neuronIndex){
        if (layerIndex>networkDimensions.length-2){
            return null;
        }else {
            double[] v = new double[networkDimensions[layerIndex + 1]];
            for (int k = 0; k < v.length; k++) {
                v[k] = net.getInputWeightForNeuron(layerIndex+1,k,neuronIndex);
            }
            return v;
        }
    }

    //--2.1-- Считаем вектор весов для всех нейронов i-го слоя
    public void calcEWVectorForLayer (int layerIndex){
        int levelLength = networkDimensions[layerIndex];
        for (int j=0;j<levelLength;j++){
            net.setEWVectorForNeuron(layerIndex,j,calcEWVectorForNeuron(layerIndex,j));
        }
    }

    //--3.1-- Считаем взвешанную сумму ошибок для всех нейронов i-го слоя
    public void calcSigmaSumForLayer (int layerIndex){
        if(layerIndex>networkDimensions.length-2) {

        }else {
            for (int j = 0; j < networkDimensions[layerIndex]; j++) {
                net.calcSigmaSumForNeuron(layerIndex,j);
            }
        }
    }

    //--3.2-- Считаем взвешанную сумму ошибок для всех нейронов выходного слоя
    public void calcSigmaSumForOutputLayer(double[] answer){
        int i = networkDimensions.length-1;
        for(int j=0; j < networkDimensions[i]; j++){
            net.setSigmaSumForNeuron(i,j,answer[j] - net.getOutFromNeuron(i,j));
        }
    }

    //--4.1-- Считаем итоговую сигму для всех нейронов i-го слоя
    public void calcSigmaToTransferForLayer(int i){
        for (int j=0;j<networkDimensions[i];j++){
            net.calcSigmaToTransferForNeuron(i,j);
        }
    }

    //--5.1-- Считаем изменение весов для всех нейронов i-го слоя
    public void calcDWForLayer(int i){
        for (int j=0;j<networkDimensions[i];j++){
            net.calcDWForHiddenNeuron(i,j);
        }
    }

    //--5.2-- Применяем изменения весов для всей сети (кроме 1го слоя)
    public void applyDW(){
        for (int i=1;i<networkDimensions.length;i++){
            for(int j=0;j<networkDimensions[i];j++){
                net.applyDWForHiddenNeuron(i,j);
            }
        }
    }

    //--6-- Передаем сигмы j-го нейрона i-го слоя на (i-1) слой
    public void transferSigmas(int i, int j){
        for (int k=0;k<networkDimensions[i-1];k++){
            net.setInputSigmaForNeuron(i-1,k,net.getSigmaToTransferFromNeuron(i,j),j);
        }
    }

    //--6.1-- Передаем сигмы каждого нейрона i-го слоя на (i-1) слой
    public void transferSigmasFromLayer (int i){
        if (i<=1){

        }else {
            for (int j = 0; j < networkDimensions[i]; j++) {
                transferSigmas(i, j);
            }
        }
    }

    //--7-- Выполняем Backpropagator
    public void RunBackpropagation (double[] input, double[] cOutput){
        Propagator propagator = new Propagator();
        propagator.startPropagation(net,input);
        calcSigmaSumForOutputLayer(cOutput);
        calcSigmaToTransferForLayer(networkDimensions.length-1);
        transferSigmasFromLayer(networkDimensions.length-1);
        calcDWForLayer(networkDimensions.length-1);
        for (int i=networkDimensions.length-2;i>0;i--){
            calcEWVectorForLayer(i);
            calcSigmaSumForLayer(i);
            calcSigmaToTransferForLayer(i);
            transferSigmasFromLayer(i);
            calcDWForLayer(i);
        }
        applyDW();
    }

    //--8-- Выполняем Backpropagator для массива сэмплов до достижения желаемой величины ошибки
    public int runBPA(double[][] inM, double[][] outM, double accuracy){
        networkError =2;
        double networkErrorTemp=2;
        int counter=0;
        Propagator propagator = new Propagator();

        while (networkError >accuracy){networkErrorTemp=0;
            learningIterations++;
            for (int i=0;i<inM.length;i++) {
                counter++;
                RunBackpropagation(inM[i],outM[i]);
                propagator.startPropagation(net,inM[i]);
                for (int k = 0; k < networkDimensions[networkDimensions.length-1]; k++) {
                    networkErrorTemp +=
                            Math.pow(outM[i][k]
                                    - net.getOutFromNeuron(networkDimensions[networkDimensions.length-1],k), 2);
                }
                //System.out.println("Итерация: "+counter+" Вход ["+neuronNet[0][0].getOut()+"]["+neuronNet[0][1].getOut()+"], Выход[2][0]: "+neuronNet[2][0].getOut()+" ,Ошибка: "+ networkError);
                System.out.println("Итерация: "+learningIterations+" ,Ошибка: "+ networkError);

            }
            networkError =networkErrorTemp;

            //super.setChanged();
        }
        return counter;
    }

    public int runBPA(LinkedList<Sample> samples, double targetPercentage){
        networkError =2;
        double networkErrorTemp=2;
        int counter=0;
        int counter2=0;
        int rightanswerscounter=0;
        double percentage=0;
        Propagator propagator = new Propagator();
        while (percentage <targetPercentage){
            networkErrorTemp=0;
            learningIterations++;
            counter=0;
            rightanswerscounter=0;
            Collections.shuffle(samples);
            for (int i=0;i<samples.size();i++) {
                counter++;

                for (int t=0;t<1;t++) {
                    RunBackpropagation(samples.get(i).getSampleIn(), samples.get(i).getSampleOut());
                }

                for (int k = 0; k < networkDimensions[networkDimensions.length-1]; k++) {
                    networkErrorTemp +=
                            Math.pow(samples.get(i).getSampleOutI(k)
                                    - net.getOutFromNeuron(networkDimensions[networkDimensions.length-1],k), 2);
                }

            }
            for (int i=0;i<samples.size();i++){
                if (propagator.startPropagationWithTestSampleAllIn(net, samples.get(i).getSampleIn(),samples.get(i).getSampleOut())){
                    rightanswerscounter++;}
            }
            networkError =networkErrorTemp;
            percentage = ((double)rightanswerscounter/samples.size());
            System.out.println("   Эпоха: "+learningIterations
                    +",  Ошибка: "+ networkError
                    +"  Правильных ответов: "+percentage*100+"%");


            if (percentage>0.97){break;}

        }
        return counter;
    }

    /*
    double[] input;
    double[] cOutput;

    public void setInput(double[] i) {
        input = i;
    }

    public double[] getInput() {
        return input;
    }

    public double getInputI(int i) {
        return input[i];
    }

    public void setcOutput(double[] o) {
        cOutput = o;
    }

    public double[] getcOutput(){
        return cOutput;
    }

    public double getcOutputI(int i){
        return cOutput[i];
    }

    public void Run (double[] i, double[] o){
        input = i;
        cOutput = o;

    }
    */
}
