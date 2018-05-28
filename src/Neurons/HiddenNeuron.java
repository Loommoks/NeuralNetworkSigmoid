package Neurons;

public class HiddenNeuron extends NeuronBase{

    private double[] lastDW;
    private double alpha=0.2; //скорость обучения
    private double beta=0.1; //инерция

    public HiddenNeuron(int layer, int neuronNumber, int previousLevelLength){
        this.layerLevel = layer;
        this.neuronNumber = neuronNumber;
        initializeNeuron(previousLevelLength);
        setRandomWeight();
    }

    //Инициализация нейрона
    private void initializeNeuron (int inputsMassiveLenght){
        layerLevel = 0;
        neuronNumber = 0;
        inputsWeight = new double[inputsMassiveLenght];
        inputsValue = new double[inputsMassiveLenght];
        out = 0;
    }
    //Устанавливаем рандомные веса
    private void setRandomWeight (){
        for (int i=0; i<inputsWeight.length;i++){
            inputsWeight[i] = Math.random()/2+0.5;
        }
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
