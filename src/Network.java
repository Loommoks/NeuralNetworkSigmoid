public class Network {
    Neuron[][] neuronNet;
    double[][] sigmaTemp;
    double[][] sigmaFull;

    public void initializeNetwork (int[] l){
        neuronNet = new Neuron[l.length][];
        sigmaTemp = new double[l.length-1][];
        sigmaFull = new double[l.length-1][];

        for (int il =0; il<neuronNet.length; il++){
            neuronNet[il] = new Neuron[l[il]];
            if (il>0) {
                sigmaTemp[il-1] = new double[l[il]];
                sigmaFull[il-1] = new double[l[il]];
            }
            for (int jninl=0; jninl<neuronNet[il].length;jninl++){
                    if (il > 0) {
                        neuronNet[il][jninl] = new Neuron();
                        neuronNet[il][jninl].initializeNeuron(neuronNet[il - 1].length);
                        neuronNet[il][jninl].setRandomWeight();
                    } else {
                        neuronNet[il][jninl] = new InputNeuron();
                        neuronNet[il][jninl].initializeZeroLevelNeuron();
                    }
                    neuronNet[il][jninl].setNeuronNumber(jninl);
                    neuronNet[il][jninl].setLayerLevel(il);
            }
        }
        initializeDarkSide();
    }

    public void initializeDarkSide(){
        for (int i=1;i<neuronNet.length;i++){
            if(i<=neuronNet.length-2) {
                for (int j = 0; j < neuronNet[i].length; j++) {
                    neuronNet[i][j].initializeNeuronDarkSide(neuronNet[i+1].length);
                }
            }else {
                if (i==neuronNet.length-1){
                    for (int j = 0; j < neuronNet[i].length; j++) {
                        neuronNet[i][j].initializeNeuronOutDarkSide();
                    }
                }
            }
        }
    }

    public void showNetworkData(){
        System.out.println("Входные данные:["+neuronNet[0][0].getInputValue(0)+"]["+neuronNet[0][1].getInputValue(0)+"]");
        for (int il =0; il<neuronNet.length; il++){

            for (int jninl=0; jninl<neuronNet[il].length;jninl++){
                System.out.print("Нейрон[" +neuronNet[il][jninl].getLayerlevel() +"][" +neuronNet[il][jninl].getNeuronNumber() +"]  ");
                System.out.print("Выход:" +neuronNet[il][jninl].getOut()+"  ");
                double[] wMassive= neuronNet[il][jninl].getInputWeight();
                for (int k=0;k<wMassive.length;k++){
                    System.out.print("Вес["+k+"]: " +wMassive[k]+"  ");
                }
                System.out.println("");
            }
        }
    }


    public double startNetworking(double in[]){
        //По логике даем сигнал на входные нейроны
        //Далее в цикле проходим по всем нейронам каждого слоя и они передают значения на выход
        //System.out.println("Входные данные:["+in[0]+"]["+in[1]+"]");
        for (int i=0; i<neuronNet.length;i++){
            if (i==0) {
                for (int j = 0; j < neuronNet[i].length; j++) {
                    neuronNet[i][j].setInputsValue(in[j],0);
                    neuronNet[i][j].calculateSummator();
                    neuronNet[i][j].calculateActivationFunctionOut();
                    transferSingnal(i,j);
                }
            }
            else {
                for (int j = 0; j < neuronNet[i].length; j++) {
                    neuronNet[i][j].calculateSummator();
                    neuronNet[i][j].calculateActivationFunctionOut();
                    transferSingnal(i,j);
                }
            }
        }

        return 0;

    }

    public void transferSingnal(int x, int y){
        if (x>=neuronNet.length){System.out.print("Network layer exceeded");}
        else {
            if (x == (neuronNet.length-1)){/*System.out.println("Выход нейрона["+x+"]["+y+"]: "+neuronNet[x][y].getOut());*/}
            else {
                for (int i = 0; i < neuronNet[x+1].length; i++) {
                    neuronNet[x+1][i].setInputsValue(neuronNet[x][y].getOut(),y);
                }
            }
        }

    }

    //--1-- Считаем вектор весов для нейрона j
    public double[] calcEWVector (int i, int j){
        if (i>neuronNet.length-2){
            return null;
        }else {
            double[] v = new double[neuronNet[i + 1].length];
            for (int k = 0; k < v.length; k++) {
                v[k] = neuronNet[i + 1][k].getInputWeight(j);
            }
            return v;
        }
    }

    //--1.1-- Считаем вектор весов для всех нейронов i-го слоя
    public void calcEWVectorForLayerI (int i){
        for (int j=0;j<neuronNet[i].length;j++){
            neuronNet[i][j].setEWVector(calcEWVector(i,j));
        }
    }

    //--3.1-- Считаем взвешанную сумму ошибок для всех нейронов i-го слоя
    public void calcSigmaSumForLayerI (int i){
        if(i>neuronNet.length-2) {

        }else {
            for (int j = 0; j < neuronNet[i].length; j++) {
                neuronNet[i][j].calcSigmaSum();
            }
        }
    }

    //--3.2-- Счтьаем взвешанную сумму ошибок для всех нейронов выходного слоя
    public void calcSigmaSumForOutputLayer(double[] answer){
        int i =neuronNet.length-1;
        for(int j=0; j<neuronNet[i].length;j++){
            neuronNet[i][j].setSigmaSum(answer[j]-neuronNet[i][j].getOut());
        }
    }

    //--4.1-- Считаем итоговую сигму для всех нейронов i-го слоя
    public void calcSigmaToTransferForLayerI(int i){
        for (int j=0;j<neuronNet[i].length;j++){
            neuronNet[i][j].calcSigmaToTransfer();
        }
    }

    //--5.1-- Считаем изменение весов для всех нейронов i-го слоя
    public void calcDWForLayerI(int i){

        for (int j=0;j<neuronNet[i].length;j++){
            neuronNet[i][j].calcDW();
        }
    }

    //--5.2-- Применяем изменения весов для всей сети (кроме 1го слоя)
    public void applyDW(){
        for (int i=1;i<neuronNet.length;i++){
            for(int j=0;j<neuronNet[i].length;j++){
                neuronNet[i][j].applyDW();
            }
        }
    }

    //--6-- Передаем сигмы j-го нейрона i-го слоя на (i-1) слой
    public void transferSigmas(int i, int j){
        for (int k=0;k<neuronNet[i-1].length;k++){
            neuronNet[i-1][k].setInputsSigma(neuronNet[i][j].getSigmaToTrasfer(),j);
        }
    }

    //--6.1-- Передаем сигмы каждого нейрона i-го слоя на (i-1) слой
    public void transferSigmasFromILayer (int i){
        if (i<=1){

        }else {
            for (int j = 0; j < neuronNet[i].length; j++) {
                transferSigmas(i, j);
            }
        }
    }

    //--7-- Выполняем Backpropagation
    public void RunBackpropagation (double[] input, double[] cOutput){
        startNetworking(input);
        calcSigmaSumForOutputLayer(cOutput);
        calcSigmaToTransferForLayerI(neuronNet.length-1);
        transferSigmasFromILayer(neuronNet.length-1);
        calcDWForLayerI(neuronNet.length-1);
        for (int i=neuronNet.length-2;i>0;i--){
            calcEWVectorForLayerI(i);
            calcSigmaSumForLayerI(i);
            calcSigmaToTransferForLayerI(i);
            transferSigmasFromILayer(i);
            calcDWForLayerI(i);
        }
        applyDW();
    }

    //--8-- Выполняем Backpropagation для массива сэмплов до достижения желаемой величины ошибки
    public void runBPA(double[][] inM, double[][] outM, double accuracy){
        double tE=2;
        double tempTE=2;
        int counter=0;
        int gcounter=0;
        while (tE>accuracy){tempTE=0;
            for (int i=0;i<inM.length;i++) {
                counter++;
                gcounter++;
                if(gcounter==10000){showNetworkData();gcounter=0;}
                RunBackpropagation(inM[i],outM[i]);
                //RunBackpropagation(inM[i],outM[i]);
                //RunBackpropagation(inM[i],outM[i]);
                startNetworking(inM[i]);
                for (int k = 0; k < neuronNet[neuronNet.length - 1].length; k++) {
                    tempTE += Math.pow(outM[i][k] - neuronNet[neuronNet.length - 1][k].getOut(), 2);
                }
                System.out.println("Итерация: "+counter+" Вход ["+neuronNet[0][0].getOut()+"]["+neuronNet[0][1].getOut()+"], Выход[2][0]: "+neuronNet[2][0].getOut()+" ,Ошибка: "+tE);
            }
            tE=tempTE;
        }
    }
}
