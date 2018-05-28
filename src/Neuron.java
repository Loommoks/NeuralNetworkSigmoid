import com.sun.javaws.exceptions.InvalidArgumentException;

public class Neuron {

    private int layerlevel;
    private int neuronNumber;
    private double[] inputsWeight;
    private double[] inputsValue;
    private double out;
    double sum;

    double[] inputsSigma;
    double sigmaSum;
    double sigmaToTrasfer;
    double[] vector;
    double[] dW;
    double[] lastDW;

    double alpha=0.2; //скорость обучения
    double beta=0.1; //инерция


    //-----------Геттеры и сеттеры-------------------//

    public void setLayerLevel(int l){
        layerlevel = l;
    }

    public int getLayerlevel(){
        return layerlevel;
    }

    public void setNeuronNumber(int l){
        neuronNumber = l;
    }

    public int getNeuronNumber(){
        return neuronNumber;
    }

    public void setInputWeightDimension(int l){
        inputsWeight = new double[l];
    }

    public int getInputWeightDimension(){ return inputsWeight.length; }

    public void setInputWeight(double w, int inputNumber){ inputsWeight[inputNumber] = w;}

    public double getInputWeight(int inputNumber){return inputsWeight[inputNumber];}

    public double[] getInputWeight(){return inputsWeight;}

    public void setInputsValue(double v, int num){inputsValue[num]=v;}

    public void setInputsSigma(double s, int num){inputsSigma[num]=s;}

    public double getInputsSigma (int j){return inputsSigma[j];}

    public void setSigmaSum (double s){sigmaSum =s;}

    public double getSigmaSum (){return sigmaSum;}

    public double getSigmaToTrasfer (){return sigmaToTrasfer;}

    public void setSigmaToTrasfer (double s){sigmaToTrasfer=s;}

    public double getInputValue(int num){return inputsValue[num];}

    public double getOut(){
        return out;
    }

    public void setOut(double o){out = o;}

    public double[] getEWVector(){
        return vector;
    }

    public double getEWVector(int j){ return vector[j];}

    public int getEWVectorLenght (){return vector.length;}

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

    //Инициализация нейрона
    public void initializeNeuron (int inputsMassiveLenght){
        layerlevel = 0;
        neuronNumber = 0;
        inputsWeight = new double[inputsMassiveLenght];
        inputsValue = new double[inputsMassiveLenght];
        out = 0;
    }

    //Инициализация нейрона для backpropagation
    public void initializeNeuronDarkSide (int l){
        inputsSigma = new double[l];
        sigmaSum=0;
        sigmaToTrasfer=0;
        vector = new double[l];
        dW = new double[inputsWeight.length];
        lastDW = new double[inputsWeight.length];
    }

    //Инициализация нейрона выходного слоя для backpropagation
    public void initializeNeuronOutDarkSide (){
        inputsSigma = new double[1];
        sigmaSum=0;
        sigmaToTrasfer=0;
        vector = new double[1];
        dW = new double[inputsWeight.length];
        lastDW = new double[inputsWeight.length];
    }

    //Инициализация входного нейрона для прямого распространения
    public void initializeZeroLevelNeuron (){
        layerlevel = 0;
        neuronNumber = 0;
        inputsWeight = new double[1];
        inputsWeight[0] = 1;
        inputsValue = new double[1];
        out = 0;
        inputsSigma = new double[1];
        sigmaSum=0;
        sigmaToTrasfer=0;
        vector = new double[1];
        dW = new double[1];
    }

    //Инициализация нейрона смещения
    //!!!!!!!!!!Доделать
    public void initializeBiasNeuron (){
        layerlevel = 0;
        neuronNumber = 0;
        inputsWeight = new double[1];
        inputsWeight[0] = 1;
        inputsValue = new double[1];
        out = 0;
    }

    //Устанавливаем рандомные веса
    public void setRandomWeight (){
        for (int i=0; i<inputsWeight.length;i++){
            inputsWeight[i] = Math.random()/2+0.5;
        }
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

    //--5-- Считаем изменение весов
    public void calcDW(){
        for (int j=0; j<dW.length;j++){
            dW[j]=alpha*sigmaToTrasfer*inputsValue[j];
        }
    }

    //--5.1-- Применяем изменение весов
    public void applyDW(){
        for (int j=0;j<inputsWeight.length;j++){
            inputsWeight[j]+=dW[j]+beta*lastDW[j];
            lastDW[j]=dW[j]+beta*lastDW[j];
        }
    }

}
