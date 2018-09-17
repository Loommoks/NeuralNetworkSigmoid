public class Backpropagator {
    protected Network net;
    protected int[] networkDimensions;

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
