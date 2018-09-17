package Neurons;

public class HiddenNeuron extends NeuronBase{

    private double[] lastDW;
    private static double ALPHA =0.00003; //скорость обучения
    private static double BETA =0.0; //инерция
    //private double gamma=1000;// коэффициент ускорение обучения
    private double tetta=1; // сжимание веса для выхода из потенциальной ямы, 1 - без изменений
    private int layersInNetwork;

    //---Get/Set

    public double[] getLastDW(){return lastDW;}
    public static double getAlpha(){return ALPHA;}
    public static double getBeta(){return BETA;}

    public static void setALPHA(double newAlpha){
        HiddenNeuron.ALPHA=newAlpha;
        System.out.println("Изменена скорость обучения: "+ALPHA);
    }

    public static void setBETA(double newBeta){
        HiddenNeuron.BETA=newBeta;
        System.out.println("Изменена инерция обучения: "+BETA);
    }

    public void setLastDW(double[] in){lastDW=in;}

    //Конструктор нейрона скрытого слоя

    public HiddenNeuron(int layer, int neuronNumber, int previousLevelLength, int layersInNetwork){
        this.layerLevel = layer;
        this.neuronNumber = neuronNumber;
        this.layersInNetwork = layersInNetwork;
        initializeNeuron(previousLevelLength);
        setRandomWeight();
    }

    //---Metods

    //Инициализация нейрона
    private void initializeNeuron (int inputsMassiveLength){
        inputsWeight = new double[inputsMassiveLength];
        inputsValue = new double[inputsMassiveLength];
        out = 0;
    }
    //Устанавливаем рандомные веса
    private void setRandomWeight (){
        for (int i=0; i<inputsWeight.length;i++){
            inputsWeight[i] = Math.random()-0.5;
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
            //dW[j]=tetta*ALPHA*sigmaToTrasfer*inputsValue[j];
            dW[j]= ALPHA *sigmaToTrasfer*inputsValue[j];
            //System.out.println("Layer "+layerLevel+" dW:"+dW[j]);
        }
    }

    //--5.1-- Применяем изменение весов
    public void applyDW(){
        for (int j=0;j<inputsWeight.length;j++){
            //inputsWeight[j]+=dW[j];
            inputsWeight[j]=tetta*inputsWeight[j]+(dW[j]+ BETA *lastDW[j]);
            lastDW[j]=dW[j]+lastDW[j];
        }
    }
}
