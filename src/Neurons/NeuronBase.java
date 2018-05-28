package Neurons;

public class NeuronBase {

    protected int layerLevel;
    protected int neuronNumber;
    protected double[] inputsWeight;
    protected double[] inputsValue;
    protected double out;
    private double sum;

    protected double[] inputsSigma;
    protected double sigmaSum;
    protected double sigmaToTrasfer;
    protected double[] vector;
    protected double[] dW;



    //-----------Геттеры и сеттеры-------------------//

    public int getLayerLevel(){ return layerLevel; }

    public int getNeuronNumber(){ return neuronNumber; }

    public double getInputWeight(int inputNumber){return inputsWeight[inputNumber];}

    public double[] getInputWeight(){return inputsWeight;}

    public void setInputsValue(double v, int num){inputsValue[num]=v;}

    public void setInputsSigma(double s, int num){inputsSigma[num]=s;}

    public void setSigmaSum (double s){sigmaSum =s;}

    public double getSigmaToTrasfer (){return sigmaToTrasfer;}

    public double getInputValue(int num){return inputsValue[num];}

    public double getOut(){
        return out;
    }

    public void setOut(double o){out = o;}

    public void setEWVector(double[] v){vector = v;}

    public double getSum(){return sum;}

    //------------------Методы--------------------------------//

    //Подсчет результата функции активации (сигмоида 1/(1+е^(-х)) )
    public void calculateActivationFunctionOut(){
        double b;
        b = 1/(1+(Math.pow(Math.E,-sum)));
        out = b;
    }

    //Подсчет взешанной суммы
    public void calculateSummator(){
        double inputSum = 0;
        for (int i =0; i<inputsWeight.length;i++){
            inputSum = inputSum + inputsWeight[i]*inputsValue[i];
        }
        sum = inputSum;
    }




    //Функция активации новый метод
    public double aF(double in){
        double a = 1/(1+(Math.pow(Math.E,-in)));
        return a;
    }

    //--3-- Считаем взвешанную сумму ошибок входящих в нейрон
    public void calcSigmaSum (){
        sigmaSum=0;
        for (int k=0;k<vector.length;k++){
            sigmaSum+= vector[k]*inputsSigma[k];
        }
    }

    //--4-- Считаем итоговую сигму для j-го нейрона
    public void calcSigmaToTransfer (){
        sigmaToTrasfer=sigmaSum*aF(sum)*(1-aF(sum));
    }


}
